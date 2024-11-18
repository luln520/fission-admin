package net.lab1024.sa.admin.module.system.login.service;

import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import net.lab1024.sa.admin.module.system.department.domain.vo.DepartmentVO;
import net.lab1024.sa.admin.module.system.department.service.DepartmentService;
import net.lab1024.sa.admin.module.system.employee.dao.EmployeeDao;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeePermissionService;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.login.domain.LoginEmployeeDetail;
import net.lab1024.sa.admin.module.system.login.domain.LoginForm;
import net.lab1024.sa.admin.module.system.menu.domain.vo.MenuVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.constant.StringConst;
import net.lab1024.sa.common.common.domain.RequestUser;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.enumeration.UserTypeEnum;
import net.lab1024.sa.common.common.util.SmartBeanUtil;
import net.lab1024.sa.common.common.util.SmartEnumUtil;
import net.lab1024.sa.common.module.support.captcha.CaptchaService;
import net.lab1024.sa.common.module.support.captcha.domain.CaptchaVO;
import net.lab1024.sa.common.module.support.config.ConfigKeyEnum;
import net.lab1024.sa.common.module.support.config.ConfigService;
import net.lab1024.sa.common.module.support.loginlog.LoginLogResultEnum;
import net.lab1024.sa.common.module.support.loginlog.LoginLogService;
import net.lab1024.sa.common.module.support.loginlog.domain.LoginLogEntity;
import net.lab1024.sa.common.module.support.loginlog.domain.LoginLogVO;
import net.lab1024.sa.common.module.support.token.LoginDeviceEnum;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * 员工 登录服务
 *
 * @Author 1024创新实验室: 开云
 * @Date 2021-12-01 22:56:34
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ），2012-2022
 */
@Slf4j
@Service
public class LoginService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private EmployeePermissionService employeePermissionService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private TwCompanyService twCompanyService;

    @Autowired
    private EmployeeDao employeeDao;

    private static final Map<String, String> loginMap = new HashMap<>();

    /**
     * 登录信息二级缓存
     */
    private ConcurrentMap<Long, LoginEmployeeDetail> loginUserDetailCache = new ConcurrentLinkedHashMap.Builder<Long, LoginEmployeeDetail>().maximumWeightedCapacity(1000).build();

    /**
     * 获取验证码
     *
     * @return
     */
    public ResponseDTO<CaptchaVO> getCaptcha() {
        return ResponseDTO.ok(captchaService.generateCaptcha());
    }

    /**
     * 员工登陆
     *
     * @param loginForm
     * @return 返回用户登录信息
     */
    public ResponseDTO<LoginEmployeeDetail> login(LoginForm loginForm, String ip, String userAgent) {
        LoginDeviceEnum loginDeviceEnum = SmartEnumUtil.getEnumByValue(loginForm.getLoginDevice(), LoginDeviceEnum.class);
        if (loginDeviceEnum == null) {
            return ResponseDTO.userErrorParam("登录设备暂不支持！");
        }
        // 校验 图形验证码
//        ResponseDTO<String> checkCaptcha = captchaService.checkCaptcha(loginForm);
//        if (!checkCaptcha.getOk()) {
//            return ResponseDTO.error(checkCaptcha);
//        }

        /**
         * 验证账号和账号状态
         */
        EmployeeEntity employeeEntity = employeeService.getByLoginName(loginForm.getLoginName(),loginForm.getCompanyId());
        if (null == employeeEntity) {
            return ResponseDTO.userErrorParam("登录名不存在！");
        }

        if (employeeEntity.getDisabledFlag()) {
            saveLoginLog(employeeEntity, ip, userAgent, "账号已禁用", LoginLogResultEnum.LOGIN_FAIL,employeeEntity.getCompanyId());
            return ResponseDTO.userErrorParam("您的账号已被禁用,请联系工作人员！");
        }
        /**
         * 验证密码：
         * 1、万能密码
         * 2、真实密码
         */
        String superPassword = EmployeeService.getEncryptPwd(configService.getConfigValue(ConfigKeyEnum.SUPER_PASSWORD));
        String requestPassword = EmployeeService.getEncryptPwd(loginForm.getPassword());
        if (!(superPassword.equals(requestPassword) || employeeEntity.getLoginPwd().equals(requestPassword))) {
            saveLoginLog(employeeEntity, ip, userAgent, "密码错误", LoginLogResultEnum.LOGIN_FAIL,employeeEntity.getCompanyId());
            return ResponseDTO.userErrorParam("登录名或密码错误！");
        }

        // 生成 登录token，保存token
        Boolean superPasswordFlag = superPassword.equals(requestPassword);
        String token = tokenService.generateToken(employeeEntity.getEmployeeId(), employeeEntity.getActualName(),employeeEntity.getCompanyId(), UserTypeEnum.ADMIN_EMPLOYEE, loginDeviceEnum, superPasswordFlag);

        //获取员工登录信息
        LoginEmployeeDetail loginEmployeeDetail = loadLoginInfo(employeeEntity);
        loginEmployeeDetail.setToken(token);
        loginEmployeeDetail.setCompanyId(employeeEntity.getCompanyId());
        loginEmployeeDetail.setEmail(employeeEntity.getEmail());
        // 放入缓存
        loginUserDetailCache.put(employeeEntity.getEmployeeId(), loginEmployeeDetail);

        //保存登录记录
        saveLoginLog(employeeEntity, ip, userAgent, superPasswordFlag ? "万能密码登录" : loginDeviceEnum.getDesc(), LoginLogResultEnum.LOGIN_SUCCESS,employeeEntity.getCompanyId());

        return ResponseDTO.ok(loginEmployeeDetail);
    }


    /**
     * 获取登录的用户信息
     *
     * @return
     */
    private LoginEmployeeDetail loadLoginInfo(EmployeeEntity employeeEntity) {
        LoginEmployeeDetail loginEmployeeDetail = SmartBeanUtil.copy(employeeEntity, LoginEmployeeDetail.class);
        loginEmployeeDetail.setUserType(UserTypeEnum.ADMIN_EMPLOYEE);

        //部门信息
        DepartmentVO department = departmentService.getDepartmentById(employeeEntity.getDepartmentId());
        loginEmployeeDetail.setDepartmentName(null == department ? StringConst.EMPTY : department.getName());

        /**
         * 获取前端菜单和后端权限
         * 1、从数据库获取所有的权限
         * 2、拼凑成菜单和后端权限
         */
        List<MenuVO> menuAndPointsList = employeePermissionService.getEmployeeMenuAndPointsList(employeeEntity.getEmployeeId(), employeeEntity.getAdministratorFlag());
        //前端菜单
        loginEmployeeDetail.setMenuList(menuAndPointsList);
        //后端权限
        loginEmployeeDetail.setAuthorities(employeePermissionService.buildAuthorities(menuAndPointsList));

        //上次登录信息
        LoginLogVO loginLogVO = loginLogService.queryLastByUserId(employeeEntity.getEmployeeId(), UserTypeEnum.ADMIN_EMPLOYEE);
        if (loginLogVO != null) {
            loginEmployeeDetail.setLastLoginIp(loginLogVO.getLoginIp());
            loginEmployeeDetail.setLastLoginTime(loginLogVO.getCreateTime());
            loginEmployeeDetail.setLastLoginUserAgent(loginLogVO.getUserAgent());
        }

        return loginEmployeeDetail;
    }

    /**
     * 保存登录日志
     *
     * @param employeeEntity
     * @param ip
     * @param userAgent
     */
    private void saveLoginLog(EmployeeEntity employeeEntity, String ip, String userAgent, String remark, LoginLogResultEnum result,Integer companyId) {
        LoginLogEntity loginEntity = LoginLogEntity.builder()
                .userId(employeeEntity.getEmployeeId())
                .userType(UserTypeEnum.ADMIN_EMPLOYEE.getValue())
                .userName(employeeEntity.getActualName())
                .userAgent(userAgent)
                .companyId(companyId)
                .loginIp(ip)
                .remark(remark)
                .loginResult(result.getValue())
                .createTime(LocalDateTime.now())
                .build();
        loginLogService.log(loginEntity);
    }


    /**
     * 移除用户信息缓存
     *
     * @param requestUserId
     */
    public void removeLoginUserDetailCache(Long requestUserId) {
        loginUserDetailCache.remove(requestUserId);
    }

    /**
     * 根据登陆token 获取员请求工信息
     *
     * @param
     * @return
     */
    public LoginEmployeeDetail getLoginUserDetail(String token, HttpServletRequest request) {
        Long requestUserId = tokenService.getUserIdAndValidateToken(token);
        if (requestUserId == null) {
            return null;
        }
        // 查询用户信息
        LoginEmployeeDetail loginEmployeeDetail = loginUserDetailCache.get(requestUserId);
        if (loginEmployeeDetail == null) {
            // 员工基本信息
            EmployeeEntity employeeEntity = employeeService.getById(requestUserId);
            if (employeeEntity == null) {
                return null;
            }

            loginEmployeeDetail = this.loadLoginInfo(employeeEntity);
            loginEmployeeDetail.setToken(token);
            loginUserDetailCache.put(requestUserId, loginEmployeeDetail);
        }

        //更新请求ip和user agent
        loginEmployeeDetail.setUserAgent(ServletUtil.getHeaderIgnoreCase(request, RequestHeaderConst.USER_AGENT));
        loginEmployeeDetail.setIp(ServletUtil.getClientIP(request));

        return loginEmployeeDetail;
    }


    /**
     * 退出登陆，清除token缓存
     *
     * @return
     */
    public ResponseDTO<String> logout(String token, RequestUser requestUser) {
        loginUserDetailCache.remove(requestUser.getUserId());
        tokenService.removeToken(token);
        //保存登出日志
        saveLogoutLog(requestUser, requestUser.getIp(), requestUser.getUserAgent());
        return ResponseDTO.ok();
    }

    /**
     * 保存登出日志
     */
    private void saveLogoutLog(RequestUser requestUser, String ip, String userAgent) {
        LoginLogEntity loginEntity = LoginLogEntity.builder()
                .userId(requestUser.getUserId())
                .userType(requestUser.getUserType().getValue())
                .userName(requestUser.getUserName())
                .userAgent(userAgent)
                .loginIp(ip)
                .loginResult(LoginLogResultEnum.LOGIN_OUT.getValue())
                .createTime(LocalDateTime.now())
                .build();
        loginLogService.log(loginEntity);
    }

    public ResponseDTO code(String email,int companyId) throws IOException {
        String code = this.codeRandom();
        QueryWrapper<TwCompany> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", companyId);
        TwCompany one = twCompanyService.getOne(queryWrapper);
        String companyMail = one.getCompanyMail();
        String companyMailPwd = one.getCompanyMailPwd();
        this.email(email,code,companyMail,companyMailPwd);
        loginMap.put(email, code);
        log.info("用户邮箱手机号{]:"+email+"邮箱验证码{}:"+code);

        return ResponseDTO.ok("验证码已发送");
    }
    public ResponseDTO emailVial(String email,String regcode) {
        String storedCaptcha = loginMap.get(email);

        //验证码
        if(storedCaptcha == null) {
            return ResponseDTO.userErrorParam("验证码错误或过期！");
        }

        if(storedCaptcha != null){
            if (!storedCaptcha.equals(regcode)) {
                return ResponseDTO.userErrorParam("验证码错误或过期！");
            }
        }
        loginMap.remove(email);

        return ResponseDTO.ok("验证码校验成功" );
    }

    private  String codeRandom() {
        String characterSet = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(characterSet.length());
            char randomChar = characterSet.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }

    public  void email(String email,String code,String companyMail,String companyMailPwd){
        try{
            //1.创建一封邮件的实例对象
            Properties props = new Properties();
            //选择ssl方式
            gmailtls(props);

            final String username = companyMail;// gmail 邮箱
            final String password = companyMailPwd;// Google应用专用密码
            // 当做多商户的时候需要使用getInstance, 如果只是一个邮箱发送的话就用getDefaultInstance
            // Session.getDefaultInstance 会将username,password保存在session会话中
            // Session.getInstance 不进行保存
            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            MimeMessage msg = new MimeMessage(session);
            //2.设置发件人地址
            msg.setFrom(new InternetAddress(email));
            /**
             * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
             * MimeMessage.RecipientType.TO:发送
             * MimeMessage.RecipientType.CC：抄送
             * MimeMessage.RecipientType.BCC：密送
             */
            msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            //4.设置邮件主题
            msg.setSubject("Email verification code："+code, "UTF-8");

            // 6. 创建文本"节点"
            MimeBodyPart text = new MimeBodyPart();
            // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
            text.setContent("<p>Your login verification code: "+code+"</p>",
                    "text/html;charset=UTF-8");

            // 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
            MimeMultipart mm_text_image = new MimeMultipart();
            mm_text_image.addBodyPart(text);
            mm_text_image.setSubType("related");    // 关联关系
//            mm_text_image.setSubType("related");    // 关联关系


            // 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
            msg.setContent(mm_text_image);
            //设置邮件的发送时间,默认立即发送
            msg.setSentDate(new Date());
            Transport.send(msg);

        }catch (Exception e){

        }
    }

    private  void gmailtls(Properties props) {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }
}
