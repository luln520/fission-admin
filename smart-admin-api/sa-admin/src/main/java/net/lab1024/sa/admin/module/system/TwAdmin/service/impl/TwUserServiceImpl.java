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
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.Email.SendEmailLib;
import net.lab1024.sa.common.common.SMS.SendSmsLib;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.enumeration.UserTypeEnum;
import net.lab1024.sa.common.common.util.CommonUtil;
import net.lab1024.sa.common.module.support.config.ConfigKeyEnum;
import net.lab1024.sa.common.module.support.config.ConfigService;
import net.lab1024.sa.common.module.support.loginlog.LoginLogResultEnum;
import net.lab1024.sa.common.module.support.token.TokenService;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    public IPage<TwUser> listUserpage(TwUserVo twUserVo,HttpServletRequest request) {
        List<TwUser>  list1 = new ArrayList<>();
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId1 = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        if(roleEmployeeVO.getKey().equals("admin") || roleEmployeeVO.getKey().equals("backend")){
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

        if(roleEmployeeVO.getKey().equals("agent")){
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

           String encryptPwd = getEncryptPwd(password);
           String ip = CommonUtil.getClientIP(request);
           String locationByIP = CommonUtil.getAddress(ip);

           twUser.setUsername(username);
           twUser.setInvit(invitCode);
           twUser.setInvit1(invit);
           twUser.setPath(path);
           twUser.setDepatmentId(departmentId.intValue());
           twUser.setPassword(encryptPwd);
           twUser.setAreaCode("");
           twUser.setAddip(ip);
           twUser.setAddr(locationByIP);
           twUser.setRealName(locationByIP);
           long timestampInSeconds = Instant.now().getEpochSecond();
           twUser.setAddtime((int) (timestampInSeconds/1000));
           twUser.setStatus(1);
           twUser.setTxstate(1);
           twUser.setRzstatus(0);
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
            twAdminLog.setRemark("指定用户 "+uid+" 手动减少");
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
            twAdminLog.setRemark("指定用户 "+uid+" 手动增加");
            twAdminLogService.save(twAdminLog);

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
        String language = userReq.getLanguage();
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
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
        String token = tokenService.useToken(uid, username, superPasswordFlag);

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

//            String address = CommonUtil.getAddress(ip);
//            String address = CommonUtil.getAddress(ip);

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
                twUser.setAreaCode("");
                twUser.setPath(path);
                twUser.setAddip(ip);
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
                twUserCoin.setUsdt(tyonfig.getTymoney());
                twUserCoinService.save(twUserCoin);

                captchaMap.remove(username);

                List<TwKuangji> list = twKuangjiService.list();
                for(TwKuangji twKuangji:list){
                    TwUserKuangji twUserKuangji = new TwUserKuangji();
                    twUserKuangji.setMin(twKuangji.getPricemin());
                    twUserKuangji.setMax(twKuangji.getPricemax());
                    twUserKuangji.setNum(1);
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
            twNotice.setTitleEn("Certification information submitted successfully");
            twNotice.setContent("实名资料提成功，耐心等待管理员审核");
            twNotice.setContentEn("The real-name information has been submitted successfully, please wait patiently for the administrator to review it.");
            twNotice.setAddtime(new Date());
            twNotice.setStatus(1);
            twNoticeService.save(twNotice);
            return ResponseDTO.okMsg("认证资料提交成功");
        }catch (Exception e){
             return ResponseDTO.userErrorParam("认证资料提交失败");
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
    public ResponseDTO code(String username,String area,int type,String language) throws IOException {
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
            SendEmailLib.email(username,code);
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


}
