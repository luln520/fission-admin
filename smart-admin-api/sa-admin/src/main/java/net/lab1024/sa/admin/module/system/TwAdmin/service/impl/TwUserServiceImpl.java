package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserQianbaoDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.UserReq;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.login.domain.LoginEmployeeDetail;
import net.lab1024.sa.admin.module.system.login.domain.LoginForm;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.enumeration.UserTypeEnum;
import net.lab1024.sa.common.common.util.CommonUtil;
import net.lab1024.sa.common.module.support.config.ConfigKeyEnum;
import net.lab1024.sa.common.module.support.config.ConfigService;
import net.lab1024.sa.common.module.support.loginlog.LoginLogResultEnum;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * 用户信息表(TwUser)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:46
 */




@Service("twUserService")
@Transactional
public class TwUserServiceImpl extends ServiceImpl<TwUserDao, TwUser> implements TwUserService {

    private static final String PASSWORD_SALT_FORMAT = "smart_%s_admin_$^&*";

    /**
     * 登录信息二级缓存
     */
    private ConcurrentMap<Integer, TwUser> loginUserDetailCache = new ConcurrentLinkedHashMap.Builder<Integer, TwUser>().maximumWeightedCapacity(1000).build();

    @Autowired
    private TwUserCoinService twUserCoinService;
    @Autowired
    private TwBillService twBillService;
    @Autowired
    private TwRechargeService twRechargeService;
    @Autowired
    private TwAdminLogService twAdminLogService;
    @Autowired
    private TwNoticeService twNoticeService;
    @Autowired
    private TwUserLogService twUserLogService;
    @Autowired
    private TwConfigService twConfigService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private TokenService tokenService;
    @Override
    public Integer countAllUsers() {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public Integer countLineUsers(String startTime, String endTime) {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("addtime", startTime);
        queryWrapper.le("addtime", endTime);
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public IPage<TwUser> listpage(TwUserVo twUserVo) {
            Page<TwUser> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, twUserVo));
            return objectPage;
    }

    @Override
    public IPage<TwUser> listUserpage(TwUserVo twUserVo) {
        List<TwUser>  list1 = new ArrayList<>();
        Page<TwUser> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
        List<TwUser> list = baseMapper.listpage(objectPage, twUserVo);
        for(TwUser twUser:list){
            String invit1 = twUser.getInvit1();
                QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", invit1);
                TwUser user1 = this.getOne(queryWrapper1);
                if(user1 == null){
                    twUser.setInvit1("");
                }else{
                    String username1 = user1.getUsername();
                    twUser.setInvit1(username1);
                }

                String invit2 = twUser.getInvit2();
                QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("id", invit2);
                TwUser user2 = this.getOne(queryWrapper2);
                if(user2 == null){
                    twUser.setInvit2("");
                }else{
                    String username2 = user2.getUsername();
                    twUser.setInvit2(username2);
                }

                String invit3 = twUser.getInvit3();
                QueryWrapper<TwUser> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("id", invit3);
                TwUser user3= this.getOne(queryWrapper3);
                if(user3 == null){
                    twUser.setInvit3("");
                }else{
                    String username3 = user3.getUsername();
                    twUser.setInvit3(username3);
                }

            list1.add(twUser);

        }
        objectPage.setRecords(list1);
        return objectPage;
    }

    @Override
    public ResponseDTO addOrUpdate(TwUser twUser,HttpServletRequest request) throws IOException {
       if(twUser.getId() == null){
           String username = twUser.getUsername();
           String phone = twUser.getPhone();
           String password = twUser.getPassword();
           String invit = twUser.getInvit();
           QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
           queryWrapper.eq("username", username);
           TwUser one = this.getOne(queryWrapper);
           if(one != null){
               return ResponseDTO.userErrorParam("账号重复");
           }

           QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
           queryWrapper1.eq("phone", phone);
           TwUser one1 = this.getOne(queryWrapper1);
           if(one1 != null){
               return ResponseDTO.userErrorParam("手机号重复");
           }

           EmployeeEntity byInvite = employeeService.getByInvite(invit);
           if(byInvite == null){
               return ResponseDTO.userErrorParam("没有此推荐人");
           }
           Long employeeId = byInvite.getEmployeeId();
           Long departmentId = byInvite.getDepartmentId();

           String path = "#"+employeeId +"#,";

           String invitCode = generateRandomString();  //生成验证码

           String encryptPwd = getEncryptPwd(password);
           String ip = CommonUtil.getClientIP(request);
           String locationByIP = CommonUtil.getAddress(ip);

           twUser.setUsername(username);
           twUser.setInvit(invitCode);
           twUser.setInvit1(invit);
           twUser.setPath(path);
           twUser.setDepatmentId(departmentId.intValue());
           twUser.setPassword(encryptPwd);
           twUser.setMoney(new BigDecimal(0));
           twUser.setAreaCode("");
           twUser.setAddip(ip);
           twUser.setAddr(locationByIP);
           twUser.setRealName(locationByIP);
           long timestampInSeconds = Instant.now().getEpochSecond();
           twUser.setAddtime((int) (timestampInSeconds/1000));
           twUser.setStatus(1);
           twUser.setTxstate(1);
           twUser.setRzstatus(2);
           this.save(twUser);

           Integer uid = twUser.getId();
           TwUserCoin twUserCoin = new TwUserCoin();
           twUserCoin.setUserid(uid);
           twUserCoin.setUsdt(new BigDecimal(0));
           twUserCoinService.save(twUserCoin);

           return ResponseDTO.ok();
       }

       if(twUser.getId() != null){
           this.updateById(twUser);
           return ResponseDTO.ok();
       }
           return ResponseDTO.ok();
    }

    @Override
    public boolean setAgent(int isAgent, int id) {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        TwUser one = this.getOne(queryWrapper);
        one.setIsAgent(isAgent);
        return this.updateById(one);
    }

    @Override
    public boolean setBUy(int id, int buyOn) {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        TwUser one = this.getOne(queryWrapper);
        one.setBuyOn(buyOn);
        return this.updateById(one);
    }

    @Override
    public boolean setUser(int id, int type, int uid) {

         if(type == 1){  //冻结
             QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
             queryWrapper.eq("id", id);
             TwUser one = this.getOne(queryWrapper);
             one.setStatus(2);
             return this.updateById(one);
         }
         if(type == 2){  //解冻
             QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
             queryWrapper.eq("id", id);
             TwUser one = this.getOne(queryWrapper);
             one.setStatus(1);
             return this.updateById(one);
         }

         if(type == 3){   //启动提币
             QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
             queryWrapper.eq("id", id);
             TwUser one = this.getOne(queryWrapper);
             one.setTxstate(1);
             return this.updateById(one);
         }
         if(type == 4){         //禁止提币
             QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
             queryWrapper.eq("id", id);
             TwUser one = this.getOne(queryWrapper);
             one.setTxstate(2);
             return this.updateById(one);
         }

        return true;
    }

    @Override
    public boolean setMoney(int uid, int type, BigDecimal money) {
        String remark = "";
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", uid);
        TwUser one = this.getOne(queryWrapper);
        BigDecimal money1 = one.getMoney();


        QueryWrapper<TwUserCoin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("userid", uid);
        TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper1);

        if(type == 2){
            twUserCoinService.decre(uid,money,twUserCoin.getUsdt());
            remark = "管理员手动减少";


            TwAdminLog twAdminLog = new TwAdminLog();
//                twAdminLog.setAdminId();
//                twAdminLog.setAdminUsername();
            twAdminLog.setAction("管理员手动减少");
//                twAdminLog.setIp();
            Instant instant = Instant.now();
            long epochMilli = instant.toEpochMilli();
            twAdminLog.setCreateTime((int) (epochMilli/1000));
            twAdminLog.setDepartment(one.getDepatmentId());
            twAdminLog.setRemark("指定用户 "+uid+" 手动减少");
            twAdminLogService.save(twAdminLog);

            one.setMoney(money1.subtract(money));
        }

        if(type == 1){   //增加
            twUserCoinService.incre(uid,money,twUserCoin.getUsdt());
            TwRecharge twRecharge = new TwRecharge();
            twRecharge.setUid(uid);
            twRecharge.setUsername(one.getUsername());
            twRecharge.setCoin("usdt");
            twRecharge.setNum(money);
            twRecharge.setDepartment(one.getDepatmentId());
            twRecharge.setPath(one.getPath());
            twRecharge.setAddtime(new Date());
            twRecharge.setUpdatetime(new Date());
            twRecharge.setStatus(2);
            twRecharge.setPayimg("");
            twRecharge.setMsg("");
            twRecharge.setAtype(1);
            twRechargeService.save(twRecharge);
            remark = "管理员手动增加";

            TwAdminLog twAdminLog = new TwAdminLog();
//                twAdminLog.setAdminId();
//                twAdminLog.setAdminUsername();
            twAdminLog.setAction("管理员手动增加");
//                twAdminLog.setIp();
            Instant instant = Instant.now();
            long epochMilli = instant.toEpochMilli();
            twAdminLog.setCreateTime((int) (epochMilli/1000));
            twAdminLog.setDepartment(one.getDepatmentId());
            twAdminLog.setRemark("指定用户 "+uid+" 手动增加");
            twAdminLogService.save(twAdminLog);

            one.setMoney(money1.add(money));
        }

        TwBill twBill = new TwBill();
        twBill.setUid(uid);
        twBill.setUsername(one.getUsername());
        twBill.setCoinname("usdt");
        twBill.setNum(money);
        twBill.setPath(one.getPath());
        twBill.setDepartment(one.getDepatmentId());
        twBill.setAfternum(twUserCoinService.afternum(uid));
        twBill.setType(1);
        twBill.setAddtime(new Date());
        twBill.setSt(1);
        twBill.setRemark(remark);
        twBillService.save(twBill);

        this.updateById(one);
        return true;
    }

    @Override
    public boolean userNotice(int uid, int type, String title, String content, String imgs) {

        if(type == 1){   //单发
            QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", uid);
            TwUser one = this.getOne(queryWrapper);
            TwNotice twNotice = new TwNotice();
                twNotice.setUid(uid);
                twNotice.setAccount(one.getUsername());
                twNotice.setTitle(title);
                twNotice.setDepartment(one.getDepatmentId());
                twNotice.setPath(one.getPath());
                twNotice.setContent(content);
                twNotice.setImgs(imgs);
                twNotice.setAddtime(new Date());
                twNotice.setStatus(1);
                twNoticeService.save(twNotice);
            }
            if(type == 2){   //群发
                QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
                List<TwUser> list = this.list(queryWrapper);
                for (TwUser twUser:list){
                    TwNotice twNotice = new TwNotice();
                    twNotice.setUid(uid);
                    twNotice.setDepartment(twUser.getDepatmentId());
                    twNotice.setPath(twUser.getPath());
                    twNotice.setAccount(twUser.getUsername());
                    twNotice.setTitle(title);
                    twNotice.setContent(content);
                    twNotice.setImgs(imgs);
                    twNotice.setAddtime(new Date());
                    twNotice.setStatus(1);
                    twNoticeService.save(twNotice);
                }
            }
              return true;
        }

    /**
     * PC H5 用户登录
     * @param ip
     * @return
     */

    @Override
    public ResponseDTO<TwUser> loginUser(UserReq userReq, String ip) {

        /**
         * 验证账号和账号状态
         */
        String username = userReq.getUsername();
        String password = userReq.getPassword();
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        TwUser one = this.getOne(queryWrapper);
        if (null == one) {
            return ResponseDTO.userErrorParam("用户不存在！");
        }

        if (one.getStatus() != 1) {
            return ResponseDTO.userErrorParam("你的账号已冻结请联系管理员!");
        }

        Integer uid = one.getId();
        /**
         * 验证密码：
         * 1、万能密码
         * 2、真实密码
         */
        String superPassword =CommonUtil.getEncryptPwd(configService.getConfigValue(ConfigKeyEnum.SUPER_PASSWORD));
        String requestPassword = this.getEncryptPwd(password);
        if (!(superPassword.equals(requestPassword) || one.getPassword().equals(requestPassword))) {
            return ResponseDTO.userErrorParam("登录名或密码错误！");
        }

        // 生成 登录token，保存token
        Boolean superPasswordFlag = superPassword.equals(requestPassword);
        String token = tokenService.useToken(uid, username, superPasswordFlag);

        //获取员工登录信息
        one.setToken(token);

        // 放入缓存
        loginUserDetailCache.put(uid, one);

        TwUserLog twUserLog = new TwUserLog();
        twUserLog.setUserid(uid);
        twUserLog.setType("登录");
        twUserLog.setRemark("邮箱登录");
        twUserLog.setDepartment(one.getDepatmentId());
        twUserLog.setPath(one.getPath());
        long timestampInSeconds = Instant.now().getEpochSecond();
        twUserLog.setAddtime((int) (timestampInSeconds));
        twUserLog.setAddip(one.getAddip());
        twUserLog.setAddr(one.getAddr());
        twUserLog.setStatus(1);
        twUserLogService.save(twUserLog);

        return ResponseDTO.ok(one);
    }

    /**
     * 验证码/邮箱还没实现
     * @param userReq
     * @param ip
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO register(UserReq userReq, String ip) {

        try{

            /**
             * 验证账号和账号状态
             */
            String username = userReq.getUsername();
            String password = userReq.getPassword();
            String encryptPwd = getEncryptPwd(password); //MD5密码加密
            String invit = userReq.getInvit();
            int type = userReq.getType();
            QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            TwUser one = this.getOne(queryWrapper);
            if (null != one) {
                return ResponseDTO.userErrorParam("用户名已存在！");
            }

            //验证码

            if(StringUtils.isEmpty(password)){
                return ResponseDTO.userErrorParam("请输入密码！");
            }

            if(StringUtils.isEmpty(invit)){
                return ResponseDTO.userErrorParam("请输入邀请码！");
            }

            QueryWrapper<TwConfig> queryConfig = new QueryWrapper<>();
            queryConfig.eq("id", 1);
            TwConfig tyonfig = twConfigService.getOne(queryConfig);    //获取体验金信息


            String invit1 = "0";
            String invit2 = "0";
            String invit3 = "0";
            String path = "";
            String path1 = "";
            Integer inivtId = 0;
            Long departmentId = 0L;
            Long employeeId = 0L;
            EmployeeEntity byInvite = employeeService.getByInvite(invit);//获取代理人信息

            QueryWrapper<TwUser> queryUser = new QueryWrapper<>();
            queryUser.eq("invit", invit);
            TwUser invitUser = this.getOne(queryUser);  //获取邀请人信息
            if(byInvite == null){
                if(invitUser == null){
                    return ResponseDTO.userErrorParam("推荐人不存在！");
                }else{
                    invit1 = invitUser.getInvit1();
                    departmentId = Long.valueOf(invitUser.getDepatmentId());
                    inivtId = invitUser.getId();
                    path1 = invitUser.getPath();
                    if(StringUtils.isNotEmpty(path1)){  //拼接团队路径
                        path = path1 +"#"+ inivtId+"#,";
                    }else{
                        path = "#"+inivtId.toString()+"#,";
                    }
                }
            }else{
                 employeeId = byInvite.getEmployeeId();
                 invit1 =employeeId.toString();
                 departmentId = byInvite.getDepartmentId();
                 path = "#"+employeeId.toString()+"#,";
            }

            String invitCode = generateRandomString();  //生成验证码

            String address = CommonUtil.getAddress("206.238.199.169");

            QueryWrapper<TwUser> queryWrapperInvite = new QueryWrapper<>();
            queryWrapperInvite.eq("invit", invitCode);
            TwUser invituserCode = this.getOne(queryWrapperInvite);
            if(invituserCode == null){   //验证码不重复
                TwUser twUser = new TwUser();
                twUser.setUsername(username);
                twUser.setPassword(encryptPwd);
                twUser.setMoney(tyonfig.getTymoney());
                twUser.setInvit(invitCode);
                twUser.setInvit1(invit1);
                twUser.setInvit2(invit2);
                twUser.setInvit3(invit3);
                twUser.setType(type);
                twUser.setAreaCode("");
                twUser.setPath(path);
                twUser.setAddip(ip);
                twUser.setDepatmentId(departmentId.intValue());
                twUser.setAddr(address);
                long timestampInSeconds = Instant.now().getEpochSecond();
                twUser.setAddtime((int) (timestampInSeconds));
                twUser.setStatus(1);
                twUser.setTxstate(1);
                twUser.setRzstatus(2);
                this.save(twUser);

                Integer uid = twUser.getId();
                TwUserCoin twUserCoin = new TwUserCoin();
                twUserCoin.setUserid(uid);
                twUserCoin.setUsdt(tyonfig.getTymoney());
                twUserCoinService.save(twUserCoin);

                return ResponseDTO.ok("注册成功");
            }
        }catch (Exception e){
            return ResponseDTO.userErrorParam("注册失败");
        }
        return null;
    }

    @Override
    public ResponseDTO editpassword(UserReq userReq) {
        try{
            /**
             * 验证账号
             */
            String username = userReq.getUsername();
            String password = userReq.getPassword();
            String encryptPwd = getEncryptPwd(password); //MD5密码加密
            QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            TwUser one = this.getOne(queryWrapper);
            if (null == one) {
                return ResponseDTO.userErrorParam("用户名不存在！");
            }

            //校验验证码

            one.setPassword(encryptPwd);
            this.updateById(one);

            TwNotice twNotice = new TwNotice();
            twNotice.setUid(one.getId());
            twNotice.setDepartment(one.getDepatmentId());
            twNotice.setPath(one.getPath());
            twNotice.setAccount(one.getUsername());
            twNotice.setTitle("重置密码");
            twNotice.setContent("登陆密码重置成功");
            twNotice.setAddtime(new Date());
            twNotice.setStatus(1);
            twNoticeService.save(twNotice);
            return ResponseDTO.ok("修改成功");
        }catch (Exception e){
            return ResponseDTO.ok("修改失败");
        }
    }

    @Override
    public ResponseDTO<TwUser> userInfo(String token) {
        Long uidToken = tokenService.getUIDToken(token);
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", uidToken.intValue());
        TwUser one = this.getOne(queryWrapper);
        return ResponseDTO.ok(one);
    }

    @Override
    public ResponseDTO auth(TwUser twUser) {
        try{
            if(StringUtils.isEmpty(twUser.getPhone())){
                return ResponseDTO.userErrorParam("手机号不能为空");
            }
            if(StringUtils.isEmpty(twUser.getRealName())){
                return ResponseDTO.userErrorParam("真实姓名不能为空");
            }
            if(StringUtils.isEmpty(twUser.getCardzm())){
                return ResponseDTO.userErrorParam("证件正面不能为空");
            }
            if(StringUtils.isEmpty(twUser.getCardfm())){
                return ResponseDTO.userErrorParam("证件背面不能为空");
            }


            twUser.setRzstatus(1);
            long timestampInSeconds = Instant.now().getEpochSecond();
            twUser.setRztime((int) (timestampInSeconds));
            this.updateById(twUser);

            TwNotice twNotice = new TwNotice();
            twNotice.setUid(twUser.getId());
            twNotice.setAccount(twUser.getUsername());
            twNotice.setDepartment(twUser.getDepatmentId());
            twNotice.setPath(twUser.getPath());
            twNotice.setTitle("认证资料提交成功");
            twNotice.setContent("实名资料提成功，耐心等待管理员审核");
            twNotice.setAddtime(new Date());
            twNotice.setStatus(1);
            twNoticeService.save(twNotice);
            return ResponseDTO.okMsg("认证资料提交成功");
        }catch (Exception e){
             return ResponseDTO.userErrorParam("认证资料提交失败");
        }

    }

    /**
     * 获取 加密后 的密码
     *
     * @param password
     * @return
     */
    public  String getEncryptPwd(String password) {
        return DigestUtils.md5Hex(String.format(PASSWORD_SALT_FORMAT, password));
    }



    private  String generateRandomString() {
        String characterSet = "qwertyuiopasdfghjklzxcvbnmABCDEFGHIJKLMNPQRSTUVWXYZ";
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(characterSet.length());
            char randomChar = characterSet.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }


}
