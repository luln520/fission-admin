package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.UserReq;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.SMS.SendSmsLib;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.CommonUtil;
import net.lab1024.sa.common.module.support.config.ConfigKeyEnum;
import net.lab1024.sa.common.module.support.config.ConfigService;
import net.lab1024.sa.common.module.support.token.TokenService;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.*;
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

    // 使用一个静态的 Map 存储验证码，以便在整个应用程序中共享
    private static final Map<String, String> captchaMap = new HashMap<>();

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

    @Autowired
    private TwUserKuangjiService twUserKuangjiService;

    @Autowired
    @Lazy
    private TwKuangjiService twKuangjiService;

    @Autowired
    @Lazy
    private TwHysettingService twHysettingService;

    @Autowired
    private TwHyorderDao twHyorderDao;

    @Autowired
    private TwLeverOrderMapper twLeverOrderMapper;

    @Autowired
    private TwKjprofitDao twKjprofitDao;

    @Autowired
    private TwRechargeDao twRechargeDao;

    @Autowired
    private TwMyzcDao twMyzcDao;

    @Autowired
    private TwCompanyService twCompanyService;

    @Override
    public Integer countAllUsers(int companyId) {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_type",1);
        queryWrapper.eq("company_id", companyId);
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public Integer countTodayUsers(long startTime, long endTime,int companyId) {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_type",1);
        queryWrapper.ge("addtime", startTime);
        queryWrapper.le("addtime", endTime);
        queryWrapper.eq("company_id", companyId);
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public Integer countLineUsers(String startTime, String endTime,int companyId) {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_type",1);
        queryWrapper.ge("lgtime", startTime);
        queryWrapper.le("lgtime", endTime);
        queryWrapper.eq("company_id", companyId);
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public IPage<TwUser> listpage(TwUserVo twUserVo) {
            Page<TwUser> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, twUserVo));
            return objectPage;
    }

    @Override
    public IPage<TwUser> listUserpage(TwUserVo twUserVo,HttpServletRequest request) {
        List<TwUser>  list1 = new ArrayList<>();
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId1 = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwUser> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
            List<TwUser> list = baseMapper.listpage(objectPage, twUserVo);
            for(TwUser twUser:list){
                String paths = "";
//                String path = twUser.getPath();
//                String[] numberStrings = path.replace("#", "").split(",");
//                int[] numbers = new int[numberStrings.length];
//                for (int i = 0; i < numberStrings.length; i++) {
//                    numbers[i] = Integer.parseInt(numberStrings[i]);
//                }
//                for (int num : numbers) {
                    EmployeeEntity byId = employeeService.getById(Long.valueOf(twUser.getInvit1()));
                    if(byId == null){
                        QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
                        queryWrapper1.eq("id", twUser.getId());
                        TwUser one = this.getOne(queryWrapper1);
                        if(one != null){
                            String realName = one.getRealName();
                            paths += realName +",";
                        }
                    }else{
                        String actualName = byId.getActualName();
                        paths += actualName +",";
                    }
//                }
                QueryWrapper<TwUserCoin> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("userid", twUser.getId());
                TwUserCoin one = twUserCoinService.getOne(queryWrapper1);
                if(null==one){
                    twUser.setMoney(new BigDecimal(0));
                }else {
                    twUser.setMoney(one.getUsdt());
                }
                twUser.setPath(paths);


                QueryWrapper<TwHysetting> queryWrapper4 = new QueryWrapper<>();
                queryWrapper4.eq("id",1);
                TwHysetting twHysetting = twHysettingService.getOne(queryWrapper4);
                String hyYlid = twHysetting.getHyYlid();
                String hyKsid = twHysetting.getHyKsid();
                String[] winarr = hyYlid.split("\\|");
                String[] lossarr = hyKsid.split("\\|");

                boolean isWinArray = false;
                boolean isLoseArray = false;
                for (String win : winarr) {
                    if (win.equals(twUser.getId().toString())) {
                        isWinArray = true;
                        break; // 如果找到匹配，可以提前退出循环
                    }
                }
                for (String win : lossarr) {
                    if (win.equals(twUser.getId().toString())) {
                        isLoseArray = true;
                        break; // 如果找到匹配，可以提前退出循环
                    }
                }

                if (isWinArray) {
                    twUser.setWinOrLose(1);
                }

                if (isLoseArray) {
                    twUser.setWinOrLose(2);
                }

                if(!isWinArray && !isLoseArray){
                    twUser.setWinOrLose(3);
                }


                List<TwUserKuangji> kjList = new ArrayList<>();
                List<TwKuangji> list2 = twKuangjiService.list();
                for(TwKuangji twKuangji:list2){
                    Integer userid = twUser.getId();
                    Integer kjid = twKuangji.getId();
                    QueryWrapper<TwUserKuangji> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("kj_id", kjid);
                    queryWrapper.eq("user_id", userid);
                    TwUserKuangji one2 = twUserKuangjiService.getOne(queryWrapper);
                    if(one2 == null){
                        TwUserKuangji twUserKuangji = new TwUserKuangji();
                        twUserKuangji.setMin(new BigDecimal(1000));
                        twUserKuangji.setMax(new BigDecimal(5000));
                        twUserKuangji.setNum(1);
                        twUserKuangji.setKjId(twKuangji.getId());
                        twUserKuangji.setKjName(twKuangji.getTitle());
                        twUserKuangji.setUserId(userid);
                        twUserKuangji.setCreateTime(new Date());
                        twUserKuangjiService.save(twUserKuangji);

                        Integer id = twUserKuangji.getId();
                        twUserKuangji.setId(id);
                        kjList.add(twUserKuangji);
                    }else{
                        kjList.add(one2);
                    }
                }
                twUser.setTwUserKuangji(kjList);
                list1.add(twUser);
            }
            objectPage.setRecords(list1);
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId1.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwUser> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
                twUserVo.setDepartmentId(byId1.getDepartmentId());
                List<TwUser> list = baseMapper.listpage(objectPage, twUserVo);
                for(TwUser twUser:list){
                    String paths = "";
//                    String path = twUser.getPath();
//                    String[] numberStrings = path.replace("#", "").split(",");
//                    int[] numbers = new int[numberStrings.length];
//                    for (int i = 0; i < numberStrings.length; i++) {
//                        numbers[i] = Integer.parseInt(numberStrings[i]);
//                    }
//                    for (int num : numbers) {
                        EmployeeEntity byId = employeeService.getById(Long.valueOf(twUser.getInvit1()));
                        if(byId == null){
                            QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
                            queryWrapper1.eq("id", twUser.getId());
                            TwUser one = this.getOne(queryWrapper1);
                            if(one != null){
                                String realName = one.getRealName();
                                paths += realName +",";
                            }
                        }else{
                            String actualName = byId.getActualName();
                            paths += actualName +",";
                        }
//                    }
                    QueryWrapper<TwUserCoin> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("userid", twUser.getId());
                    TwUserCoin one = twUserCoinService.getOne(queryWrapper1);
                    if(null==one){
                        twUser.setMoney(new BigDecimal(0));
                    }else {
                        twUser.setMoney(one.getUsdt());
                    }
                    twUser.setPath(paths);

                    QueryWrapper<TwHysetting> queryWrapper4 = new QueryWrapper<>();
                    queryWrapper4.eq("id",1);
                    TwHysetting twHysetting = twHysettingService.getOne(queryWrapper4);
                    String hyYlid = twHysetting.getHyYlid();
                    String hyKsid = twHysetting.getHyKsid();
                    String[] winarr = hyYlid.split("\\|");
                    String[] lossarr = hyKsid.split("\\|");

                    boolean isWinArray = false;
                    boolean isLoseArray = false;
                    for (String win : winarr) {
                        if (win.equals(twUser.getId().toString())) {
                            isWinArray = true;
                            break; // 如果找到匹配，可以提前退出循环
                        }
                    }
                    for (String win : lossarr) {
                        if (win.equals(twUser.getId().toString())) {
                            isLoseArray = true;
                            break; // 如果找到匹配，可以提前退出循环
                        }
                    }

                    if (isWinArray) {
                        twUser.setWinOrLose(1);
                    }

                    if (isLoseArray) {
                        twUser.setWinOrLose(2);
                    }

                    if(!isWinArray && !isLoseArray){
                        twUser.setWinOrLose(3);
                    }


                    List<TwUserKuangji> kjList = new ArrayList<>();
                    List<TwKuangji> list2 = twKuangjiService.list();
                    for(TwKuangji twKuangji:list2){
                        Integer userid = twUser.getId();
                        Integer kjid = twKuangji.getId();
                        QueryWrapper<TwUserKuangji> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("kj_id", kjid);
                        queryWrapper.eq("user_id", userid);
                        TwUserKuangji one2 = twUserKuangjiService.getOne(queryWrapper);
                        if(one2 == null){
                            TwUserKuangji twUserKuangji = new TwUserKuangji();
                            twUserKuangji.setMin(new BigDecimal(1000));
                            twUserKuangji.setMax(new BigDecimal(5000));
                            twUserKuangji.setNum(1);
                            twUserKuangji.setKjId(twKuangji.getId());
                            twUserKuangji.setKjName(twKuangji.getTitle());
                            twUserKuangji.setUserId(userid);
                            twUserKuangji.setCreateTime(new Date());
                            twUserKuangjiService.save(twUserKuangji);

                            Integer id = twUserKuangji.getId();
                            twUserKuangji.setId(id);
                            kjList.add(twUserKuangji);
                        }else{
                            kjList.add(one2);
                        }
                    }
                    twUser.setTwUserKuangji(kjList);

                    list1.add(twUser);
                }
                objectPage.setRecords(list1);
                return objectPage;
            }else{
                Page<TwUser> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
                twUserVo.setEmployeeId(byId1.getEmployeeId());
                List<TwUser> list = baseMapper.listpage(objectPage, twUserVo);
                for(TwUser twUser:list){
                    String paths = "";
//                    String path = twUser.getPath();
//                    String[] numberStrings = path.replace("#", "").split(",");
//                    int[] numbers = new int[numberStrings.length];
//                    for (int i = 0; i < numberStrings.length; i++) {
//                        numbers[i] = Integer.parseInt(numberStrings[i]);
//                    }
//                    for (int num : numbers) {
                        EmployeeEntity byId = employeeService.getById(Long.valueOf(twUser.getInvit1()));
                        if(byId == null){
                            QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
                            queryWrapper1.eq("id", twUser.getId());
                            TwUser one = this.getOne(queryWrapper1);
                            if(one != null){
                                String realName = one.getRealName();
                                paths += realName +",";
                            }
                        }else{
                            String actualName = byId.getActualName();
                            paths += actualName +",";
                        }
//                    }
                    QueryWrapper<TwUserCoin> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("userid", twUser.getId());
                    TwUserCoin one = twUserCoinService.getOne(queryWrapper1);
                    if(null==one){
                        twUser.setMoney(new BigDecimal(0));
                    }else {
                        twUser.setMoney(one.getUsdt());
                    }
                    twUser.setPath(paths);

                    QueryWrapper<TwHysetting> queryWrapper4 = new QueryWrapper<>();
                    queryWrapper4.eq("id",1);
                    TwHysetting twHysetting = twHysettingService.getOne(queryWrapper4);
                    String hyYlid = twHysetting.getHyYlid();
                    String hyKsid = twHysetting.getHyKsid();
                    String[] winarr = hyYlid.split("\\|");
                    String[] lossarr = hyKsid.split("\\|");

                    boolean isWinArray = false;
                    boolean isLoseArray = false;
                    for (String win : winarr) {
                        if (win.equals(twUser.getId().toString())) {
                            isWinArray = true;
                            break; // 如果找到匹配，可以提前退出循环
                        }
                    }
                    for (String win : lossarr) {
                        if (win.equals(twUser.getId().toString())) {
                            isLoseArray = true;
                            break; // 如果找到匹配，可以提前退出循环
                        }
                    }

                    if (isWinArray) {
                        twUser.setWinOrLose(1);
                    }

                    if (isLoseArray) {
                        twUser.setWinOrLose(2);
                    }

                    if(!isWinArray && !isLoseArray){
                        twUser.setWinOrLose(3);
                    }

                    List<TwUserKuangji> kjList = new ArrayList<>();
                    List<TwKuangji> list2 = twKuangjiService.list();
                    for(TwKuangji twKuangji:list2){
                        Integer userid = twUser.getId();
                        Integer kjid = twKuangji.getId();
                        QueryWrapper<TwUserKuangji> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("kj_id", kjid);
                        queryWrapper.eq("user_id", userid);
                        TwUserKuangji one2 = twUserKuangjiService.getOne(queryWrapper);
                        if(one2 == null){
                            TwUserKuangji twUserKuangji = new TwUserKuangji();
                            twUserKuangji.setMin(new BigDecimal(1000));
                            twUserKuangji.setMax(new BigDecimal(5000));
                            twUserKuangji.setNum(1);
                            twUserKuangji.setKjId(twKuangji.getId());
                            twUserKuangji.setKjName(twKuangji.getTitle());
                            twUserKuangji.setUserId(userid);
                            twUserKuangji.setCreateTime(new Date());
                            twUserKuangjiService.save(twUserKuangji);

                            Integer id = twUserKuangji.getId();
                            twUserKuangji.setId(id);
                            kjList.add(twUserKuangji);
                        }else{
                            kjList.add(one2);
                        }
                    }
                    twUser.setTwUserKuangji(kjList);
                    list1.add(twUser);

                }
                objectPage.setRecords(list1);
                return objectPage;
            }
        }
         return null;
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


           String usercode = "";
           usercode = userRandomString();   //生成用户code
           QueryWrapper<TwUser> queryWrapperUserCode = new QueryWrapper<>();
           queryWrapperUserCode.eq("user_code", usercode);
           TwUser userCode = this.getOne(queryWrapperUserCode);
           if(userCode != null){
               usercode = userRandomString();
           }

           String encryptPwd = getEncryptPwd(password);
           String ip = CommonUtil.getClientIP(request);
           String locationByIP = CommonUtil.getAddress(ip);

           twUser.setUsername(username);
           twUser.setInvit(invitCode);
           twUser.setInvit1(employeeId.toString());
           twUser.setPath(path);
           twUser.setDepatmentId(departmentId.intValue());
           twUser.setPassword(encryptPwd);
           twUser.setAreaCode("");
           twUser.setUserCode(usercode);
           twUser.setAddip(ip);
           twUser.setAddr(locationByIP);
           twUser.setRealName(username);
           long timestampInSeconds = Instant.now().getEpochSecond();
           twUser.setAddtime((int) (timestampInSeconds));
           twUser.setStatus(1);
           twUser.setTxstate(1);
           twUser.setRzstatus(1);
           this.save(twUser);

           Integer uid = twUser.getId();
           TwUserCoin twUserCoin = new TwUserCoin();
           twUserCoin.setUserid(uid);
           twUserCoin.setUsdt(new BigDecimal(0));
           twUserCoinService.save(twUserCoin);

           List<TwKuangji> list = twKuangjiService.list();
           for(TwKuangji twKuangji:list){
               TwUserKuangji twUserKuangji = new TwUserKuangji();
               twUserKuangji.setMin(twKuangji.getPricemin());
               twUserKuangji.setMax(twKuangji.getPricemax());
               twUserKuangji.setNum(1);
               twUserKuangji.setCompanyId(twUser.getCompanyId());
               twUserKuangji.setKjId(twKuangji.getId());
               twUserKuangji.setKjName(twKuangji.getTitle());
               twUserKuangji.setUserId(uid);
               twUserKuangji.setCreateTime(new Date());
               twUserKuangjiService.save(twUserKuangji);
           }

           return ResponseDTO.ok();
       }

       if(twUser.getId() != null){
           QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
           queryWrapper.eq("id", twUser.getId());
           TwUser one = this.getOne(queryWrapper);

           one.setJifen(twUser.getJifen());
           one.setStatus(twUser.getStatus());
           one.setUserType(twUser.getUserType());
           one.setRztype(twUser.getRztype());
           one.setCardfm(twUser.getCardfm());
           one.setCardzm(twUser.getCardzm());
           one.setUsername(twUser.getUsername());
           if(StringUtils.isNotEmpty(twUser.getPassword())){
               String encryptPwd = getEncryptPwd(twUser.getPassword());
               one.setPassword(encryptPwd);
           }
           one.setTxstate(twUser.getTxstate());
           one.setBuyOn(twUser.getBuyOn());
           this.updateById(one);
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
    public boolean setMoney(int uid, int type, BigDecimal money,HttpServletRequest request) {
        String remark = "";
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", uid);
        TwUser one = this.getOne(queryWrapper);

        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);

        QueryWrapper<TwUserCoin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("userid", uid);
        TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper1);

        if(type == 2){
            twUserCoinService.decre(uid,money,twUserCoin.getUsdt());
            remark = "管理员手动减少";


            TwAdminLog twAdminLog = new TwAdminLog();
            twAdminLog.setAdminId((int) byId.getEmployeeId());
            twAdminLog.setAdminUsername(byId.getActualName());
            twAdminLog.setAction("管理员手动减少");
            Instant instant = Instant.now();
            long epochMilli = instant.toEpochMilli();
            twAdminLog.setCreateTime((int) (epochMilli/1000));
            twAdminLog.setDepartment(one.getDepatmentId());
            twAdminLog.setPath(one.getPath());
            twAdminLog.setRemark("指定用户 "+one.getUsername()+" 手动减少");
            twAdminLogService.save(twAdminLog);

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
            twAdminLog.setAdminId((int) byId.getEmployeeId());
            twAdminLog.setAdminUsername(byId.getActualName());
            twAdminLog.setAction("管理员手动增加");
            Instant instant = Instant.now();
            long epochMilli = instant.toEpochMilli();
            twAdminLog.setCreateTime((int) (epochMilli/1000));
            twAdminLog.setDepartment(one.getDepatmentId());
            twAdminLog.setPath(one.getPath());
            twAdminLog.setRemark("指定用户 "+one.getUsername()+" 手动增加");
            twAdminLogService.save(twAdminLog);

        }

        TwBill twBill = new TwBill();
        twBill.setUid(uid);
        twBill.setUsername(one.getUsername());
        twBill.setUserCode(one.getUserCode());
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
                twNotice.setCompanyId(one.getCompanyId());
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
                    twNotice.setCompanyId(twUser.getCompanyId());
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
        String language = userReq.getLanguage();
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("company_id",userReq.getCompanyId());
        TwUser one = this.getOne(queryWrapper);
        if (null == one) {
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("用户不存在！");
            }else{
                return ResponseDTO.userErrorParam("user does not exist！");
            }
        }

        if (one.getStatus() != 1) {
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("你的账号已冻结请联系管理员！");
            }else{
                return ResponseDTO.userErrorParam("Your account has been frozen. Please contact the administrator！");
            }
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
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("登录名或密码错误！");
            }else{
                return ResponseDTO.userErrorParam("Login name or password error！");
            }
        }

        // 生成 登录token，保存token
        Boolean superPasswordFlag = superPassword.equals(requestPassword);
        String token = tokenService.useToken(uid, username,one.getCompanyId(), superPasswordFlag);

        //获取员工登录信息
        one.setToken(token);

        one.setLogins(one.getLogins()+1);
        one.setLogintime(new Date());

        this.updateById(one);

        // 放入缓存
        loginUserDetailCache.put(uid, one);

        TwUserLog twUserLog = new TwUserLog();
        twUserLog.setUserid(uid);
        twUserLog.setType("登录");
        twUserLog.setUsername(one.getUsername());
        twUserLog.setRemark("邮箱登录");
        twUserLog.setCompanyId(one.getCompanyId());
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
            String language = userReq.getLanguage();
            String encryptPwd = getEncryptPwd(password); //MD5密码加密
            String invit = userReq.getInvit();
            int type = userReq.getType();
            String regcode = userReq.getRegcode();   //验证码

            QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            TwUser one = this.getOne(queryWrapper);
            if (null != one) {
                if(language.equals("zh")){
                    return ResponseDTO.userErrorParam("用户名已存在！");
                }else{
                    return ResponseDTO.userErrorParam("The username already exists");
                }
            }

            //验证码
            String storedCaptcha = captchaMap.get(username);

//            if (storedCaptcha == null) {
//                // 验证码正确，移除验证码以防止重复使用
//                if(language.equals("zh")){
//                    return ResponseDTO.userErrorParam("验证码错误！");
//                }else{
//                    return ResponseDTO.userErrorParam("Verification code is wrong");
//                }
//            }

            if(storedCaptcha != null){
                if (!storedCaptcha.equals(regcode)) {
                    // 验证码正确，移除验证码以防止重复使用
                    if(language.equals("zh")){
                        return ResponseDTO.userErrorParam("验证码错误或过期！");
                    }else{
                        return ResponseDTO.userErrorParam("Verification code is wrong or expired");
                    }
                }
            }

            if(StringUtils.isEmpty(password)){
                if(language.equals("zh")){
                    return ResponseDTO.userErrorParam("请输入密码！");
                }else{
                    return ResponseDTO.userErrorParam("Please enter password！");
                }
            }

            if(StringUtils.isEmpty(invit)){
                if(language.equals("zh")){
                    return ResponseDTO.userErrorParam("请输入邀请码！");
                }else{
                    return ResponseDTO.userErrorParam("Please enter the invitation code！");
                }
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
                    if(language.equals("zh")){
                        return ResponseDTO.userErrorParam("推荐人不存在！");
                    }else{
                        return ResponseDTO.userErrorParam("The recommender does not exist！");
                    }
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

            String usercode = "";
            usercode = userRandomString();   //生成用户code
            QueryWrapper<TwUser> queryWrapperUserCode = new QueryWrapper<>();
            queryWrapperUserCode.eq("user_code", usercode);
            TwUser userCode = this.getOne(queryWrapperUserCode);
            if(userCode != null){
                usercode = userRandomString();
            }

//            String address = CommonUtil.getAddress(ip);
//            String address = CommonUtil.getAddress(ip);
            Integer companyId = byInvite.getCompanyId();
            QueryWrapper<TwUser> queryWrapperInvite = new QueryWrapper<>();
            queryWrapperInvite.eq("invit", invitCode);
            TwUser invituserCode = this.getOne(queryWrapperInvite);
            if(invituserCode == null){   //验证码不重复
                TwUser twUser = new TwUser();
                twUser.setUsername(username);
                twUser.setPassword(encryptPwd);
                if(type == 1){
                    twUser.setPhone(username);
                }
                twUser.setInvit(invitCode);
                twUser.setInvit1(invit1);
                twUser.setInvit2(invit2);
                twUser.setInvit3(invit3);
                twUser.setType(type);
                twUser.setUserCode(usercode);
                twUser.setAreaCode("");
                twUser.setPath(path);
                twUser.setAddip(ip);
                twUser.setCompanyId(companyId);
                twUser.setDepatmentId(departmentId.intValue());
//                twUser.setAddr(address);
                long timestampInSeconds = Instant.now().getEpochSecond();
                twUser.setAddtime((int) (timestampInSeconds));
                twUser.setStatus(1);
                twUser.setTxstate(1);
                twUser.setRzstatus(0);
                this.save(twUser);

                Integer uid = twUser.getId();
                TwUserCoin twUserCoin = new TwUserCoin();
                twUserCoin.setUserid(uid);
                twUserCoin.setCompanyId(companyId);
                twUserCoin.setUsdt(tyonfig.getTymoney());
                twUserCoinService.save(twUserCoin);

                captchaMap.remove(username);

                List<TwKuangji> list = twKuangjiService.list();
                for(TwKuangji twKuangji:list){
                    TwUserKuangji twUserKuangji = new TwUserKuangji();
                    twUserKuangji.setMin(twKuangji.getPricemin());
                    twUserKuangji.setMax(twKuangji.getPricemax());
                    twUserKuangji.setNum(1);
                    twUserKuangji.setCompanyId(companyId);
                    twUserKuangji.setKjId(twKuangji.getId());
                    twUserKuangji.setKjName(twKuangji.getTitle());
                    twUserKuangji.setUserId(uid);
                    twUserKuangji.setCreateTime(new Date());
                    twUserKuangjiService.save(twUserKuangji);
                }

                if(language.equals("zh")){
                    return ResponseDTO.ok("注册成功！");
                }else{
                    return ResponseDTO.ok("login was successful！");
                }
            }
        }catch (Exception e){
            return ResponseDTO.userErrorParam("login has failed");



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
            twNotice.setCompanyId(one.getCompanyId());
            twNotice.setAccount(one.getUsername());
            twNotice.setTitle("重置密码");
            twNotice.setTitleEn("reset Password");
            twNotice.setContent("登陆密码重置成功");
            twNotice.setContentEn("Login password reset successfully");
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
            if(twUser.getLanguage().equals("zh")){
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
            }else{
                if(StringUtils.isEmpty(twUser.getPhone())){
                    return ResponseDTO.userErrorParam("The mobile phone number cannot be empty");
                }
                if(StringUtils.isEmpty(twUser.getRealName())){
                    return ResponseDTO.userErrorParam("The real name cannot be empty");
                }
                if(StringUtils.isEmpty(twUser.getCardzm())){
                    return ResponseDTO.userErrorParam("The front of the document cannot be empty");
                }
                if(StringUtils.isEmpty(twUser.getCardfm())){
                    return ResponseDTO.userErrorParam("The back of the document cannot be empty");
                }
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
            twNotice.setCompanyId(twUser.getCompanyId());
            twNotice.setTitle("认证资料提交成功");
            twNotice.setTitleEn("Certification information submitted successfully");
            twNotice.setContent("实名资料提成功，耐心等待管理员审核");
            twNotice.setContentEn("The real-name information has been submitted successfully, please wait patiently for the administrator to review it.");
            twNotice.setAddtime(new Date());
            twNotice.setStatus(1);
            twNoticeService.save(twNotice);
            if(twUser.getLanguage().equals("zh")){
                return ResponseDTO.okMsg("认证资料提交成功");
            }else{
                return ResponseDTO.okMsg("The certification materials were submitted successfully");
            }
        }catch (Exception e){
            if(twUser.getLanguage().equals("zh")){
                return ResponseDTO.okMsg("认证资料提交失败");
            }else{
                return ResponseDTO.okMsg("Failed to submit the authentication materials");
            }
        }
    }

    @Override
    public  IPage<TwUser>  authList(TwUserVo twUserVo) {
        Page<TwUser> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
        objectPage.setRecords(baseMapper.authList(objectPage, twUserVo));
        return objectPage;
    }

    @Override
    public boolean authProcess(int uid, int type, HttpServletRequest request) {

        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);

        if(type == 1){  //审核通过
            QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", uid);
            TwUser one = this.getOne(queryWrapper);
            one.setRzstatus(2);
            long timestampInSeconds = Instant.now().getEpochSecond();
            one.setRztime((int) (timestampInSeconds));
            this.updateById(one);

            TwAdminLog twAdminLog = new TwAdminLog();
            twAdminLog.setAdminId((int) byId.getEmployeeId());
            twAdminLog.setAdminUsername(byId.getActualName());
            twAdminLog.setAction("认证资料审核通过");
            Instant instant = Instant.now();
            long epochMilli = instant.toEpochMilli();
            twAdminLog.setCreateTime((int) (epochMilli/1000));
            twAdminLog.setDepartment(one.getDepatmentId());
            twAdminLog.setPath(one.getPath());
            twAdminLog.setRemark("用户 "+one.getRealName()+" 认证审核通过");
            return twAdminLogService.save(twAdminLog);
        }

        if(type == 2){//审核不通过
            QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", uid);
            TwUser one = this.getOne(queryWrapper);
            one.setRzstatus(3);
            long timestampInSeconds = Instant.now().getEpochSecond();
            one.setRztime((int) (timestampInSeconds));
            this.updateById(one);

            TwAdminLog twAdminLog = new TwAdminLog();
            twAdminLog.setAdminId((int) byId.getEmployeeId());
            twAdminLog.setAdminUsername(byId.getActualName());
            twAdminLog.setAction("认证资料审核驳回");
            Instant instant = Instant.now();
            long epochMilli = instant.toEpochMilli();
            twAdminLog.setCreateTime((int) (epochMilli/1000));
            twAdminLog.setDepartment(one.getDepatmentId());
            twAdminLog.setPath(one.getPath());
            twAdminLog.setRemark("用户 "+one.getRealName()+" 认证审核驳回");
            return twAdminLogService.save(twAdminLog);
        }

            return true;
    }

    @Override
    public ResponseDTO code(String username,String area,int type,String language,int companyId) throws IOException {
        if(type == 1){   //手机
            String code = this.codeRandom();
            String phone = area + username;
            SendSmsLib.phone(phone,code);
            captchaMap.put(username, code);
            if(language.equals("zh")){
                return ResponseDTO.ok("验证码已发送");
            }else{
                return ResponseDTO.ok("Verification code has been sent");
            }
        }

        if(type == 2){    //邮箱
            String code = this.codeRandom();
            QueryWrapper<TwCompany> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", companyId);
            TwCompany one = twCompanyService.getOne(queryWrapper);
            String companyMail = one.getCompanyMail();
            String companyMailPwd = one.getCompanyMailPwd();
            this.email(username,code,companyMail,companyMailPwd);
            captchaMap.put(username, code);
            if(language.equals("zh")){
                return ResponseDTO.ok("验证码已发送");
            }else{
                return ResponseDTO.ok("Verification code has been sent");
            }
        }
        return null;
    }

    @Override
    public ResponseDTO codeResp() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://pp8nge.api.infobip.com/sms/1/inbox/reports?limit=10&applicationId=111907&entityId=4575A374484F5CC8C67609C2F745DB5A")
//                .method("GET", body)
                .addHeader("Authorization", "a74fae415d4519486835cc44dabf84f9-9602cc5b-b798-4fae-8f9a-c6c785080d75")
                .addHeader("Accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return ResponseDTO.ok(response);
    }

    @Override
    public ResponseDTO usertj(int uid) {

        BigDecimal winHyorder = new BigDecimal(0);
        BigDecimal lossHyorder = new BigDecimal(0);
        BigDecimal winLeverOrder = new BigDecimal(0);
        BigDecimal lossLeverOrder = new BigDecimal(0);
        BigDecimal kjOrder = new BigDecimal(0);
        BigDecimal recharge = new BigDecimal(0);
        BigDecimal myzc = new BigDecimal(0);

        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(num), 0) as winHyorder")
                .eq("is_win", 1)
                .eq("uid", uid)
                .eq("status", 2);

        List<Map<String, Object>> winHyorderResult = this.twHyorderDao.selectMaps(queryWrapper);
        if (winHyorderResult.isEmpty()) {
            winHyorder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object winHyorderObject = winHyorderResult.get(0).get("winHyorder");
        if (winHyorderObject instanceof BigDecimal) {
            winHyorder =  ((BigDecimal) winHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (winHyorderObject instanceof Long) {
            winHyorder =  BigDecimal.valueOf((Long) winHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (winHyorderObject instanceof Integer) {
            winHyorder =  BigDecimal.valueOf((Integer) winHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            // 处理其他可能的类型
            winHyorder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }


        QueryWrapper<TwHyorder> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.select("IFNULL(SUM(num), 0) as lossHyorder")
                .eq("is_win", 2)
                .eq("uid", uid)
                .eq("status", 2);

        List<Map<String, Object>> lossHyorderResult = this.twHyorderDao.selectMaps(queryWrapper1);
        if (lossHyorderResult.isEmpty()) {
            lossHyorder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object lossHyorderObject = lossHyorderResult.get(0).get("lossHyorder");
        if (lossHyorderObject instanceof BigDecimal) {
            lossHyorder =  ((BigDecimal) lossHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (lossHyorderObject instanceof Long) {
            lossHyorder =  BigDecimal.valueOf((Long) lossHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (lossHyorderObject instanceof Integer) {
            lossHyorder =  BigDecimal.valueOf((Integer) lossHyorderObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            // 处理其他可能的类型
            lossHyorder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }


        QueryWrapper<TwLeverOrder> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.select("IFNULL(SUM(num), 0) as winLeverOrder")
                .eq("is_win", 1)
                .eq("uid", uid)
                .eq("status", 2);

        List<Map<String, Object>> winLeverOrderResult = this.twLeverOrderMapper.selectMaps(queryWrapper2);
        if (winLeverOrderResult.isEmpty()) {
            winLeverOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object winLeverOrderResultObject = winLeverOrderResult.get(0).get("winLeverOrder");
        if (winLeverOrderResultObject instanceof BigDecimal) {
            winLeverOrder =  ((BigDecimal) winLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (winLeverOrderResultObject instanceof Long) {
            winLeverOrder =  BigDecimal.valueOf((Long) winLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (winLeverOrderResultObject instanceof Integer) {
            winLeverOrder =  BigDecimal.valueOf((Integer) winLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            // 处理其他可能的类型
            winLeverOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }


        QueryWrapper<TwLeverOrder> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.select("IFNULL(SUM(num), 0) as lossLeverOrder")
                .eq("is_win", 2)
                .eq("uid", uid)
                .eq("status", 2);


        List<Map<String, Object>> lossLeverOrderResult = this.twLeverOrderMapper.selectMaps(queryWrapper3);
        if (lossLeverOrderResult.isEmpty()) {
            lossLeverOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object lossLeverOrderResultObject = lossLeverOrderResult.get(0).get("lossLeverOrder");
        if (lossLeverOrderResultObject instanceof BigDecimal) {
            lossLeverOrder =  ((BigDecimal) lossLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (lossLeverOrderResultObject instanceof Long) {
            lossLeverOrder =  BigDecimal.valueOf((Long) lossLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (lossLeverOrderResultObject instanceof Integer) {
            lossLeverOrder =  BigDecimal.valueOf((Integer) lossLeverOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            // 处理其他可能的类型
            lossLeverOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }


        QueryWrapper<TwKjprofit> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.select("IFNULL(SUM(num), 0) as kjOrder")
                .eq("uid", uid);

        List<Map<String, Object>> kjOrderResult = this.twKjprofitDao.selectMaps(queryWrapper4);
        if (kjOrderResult.isEmpty()) {
            kjOrder = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object kjOrderResultObject = kjOrderResult.get(0).get("kjOrder");
        if (kjOrderResultObject instanceof BigDecimal) {
            kjOrder =  ((BigDecimal) kjOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (kjOrderResultObject instanceof Long) {
            kjOrder =  BigDecimal.valueOf((Long) kjOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (kjOrderResultObject instanceof Integer) {
            kjOrder =  BigDecimal.valueOf((Integer) kjOrderResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            // 处理其他可能的类型
            kjOrder =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }


        QueryWrapper<TwRecharge> queryWrapper5 = new QueryWrapper<>();
        queryWrapper5.select("IFNULL(SUM(num), 0) as recharge")
                .eq("uid", uid)
                .eq("status", 2);

        List<Map<String, Object>> rechargeResult = this.twRechargeDao.selectMaps(queryWrapper5);
        if (rechargeResult.isEmpty()) {
            recharge = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object rechargeResultObject = rechargeResult.get(0).get("recharge");
        if (rechargeResultObject instanceof BigDecimal) {
            recharge =  ((BigDecimal) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (rechargeResultObject instanceof Long) {
            recharge =  BigDecimal.valueOf((Long) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (rechargeResultObject instanceof Integer) {
            recharge =  BigDecimal.valueOf((Integer) rechargeResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            // 处理其他可能的类型
            recharge =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        QueryWrapper<TwMyzc> queryWrapper6 = new QueryWrapper<>();
        queryWrapper6.select("IFNULL(SUM(num), 0) as myzc")
                .eq("userid", uid)
                .eq("status", 2);

        List<Map<String, Object>> myzcResult = this.twMyzcDao.selectMaps(queryWrapper6);
        if (myzcResult.isEmpty()) {
            myzc = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object myzcResultObject = myzcResult.get(0).get("myzc");
        if (myzcResultObject instanceof BigDecimal) {
            myzc =  ((BigDecimal) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (myzcResultObject instanceof Long) {
            myzc =  BigDecimal.valueOf((Long) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (myzcResultObject instanceof Integer) {
            myzc =  BigDecimal.valueOf((Integer) myzcResultObject).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            // 处理其他可能的类型
            myzc =  BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        BigDecimal hyorder = winHyorder.subtract(lossHyorder);
        BigDecimal leverOrder = winLeverOrder.subtract(lossLeverOrder);
        BigDecimal totalWinOrder = winHyorder.add(winLeverOrder).add(kjOrder);
        BigDecimal totalLossOrder = lossHyorder.add(lossLeverOrder);


        Map<String, Object> results = new HashMap<>();
        results.put("hyorderWinOrder",hyorder);        //用户合约盈利
        results.put("leverWinOrder",leverOrder);       //用户杠杆盈利

        results.put("totalWinOrder",totalWinOrder);   //用户总盈利
        results.put("totalLossOrder",totalLossOrder); //用户总亏损
        results.put("recharge",recharge);             //用户总充值
        results.put("myzc",myzc);                     //用户总提现

        return ResponseDTO.ok(results);
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

    private  String userRandomString() {
        String characterSet = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 7; i++) {
            int randomIndex = random.nextInt(characterSet.length());
            char randomChar = characterSet.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
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



    //gmail邮箱的TLS方式
    private  void gmailtls(Properties props) {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
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
            msg.setSubject("To reset your password!", "UTF-8");

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


}
