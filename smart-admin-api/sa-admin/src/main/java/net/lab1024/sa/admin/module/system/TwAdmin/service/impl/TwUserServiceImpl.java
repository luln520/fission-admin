package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserQianbaoDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * 用户信息表(TwUser)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:46
 */




@Service("twUserService")
public class TwUserServiceImpl extends ServiceImpl<TwUserDao, TwUser> implements TwUserService {

    private static final String PASSWORD_SALT_FORMAT = "smart_%s_admin_$^&*";
    @Autowired
    private TwUserCoinService twUserCoinService;
    @Autowired
    private TwHyorderService twHyorderService;
    @Autowired
    private TwBborderService twBborderService;
    @Autowired
    private TwBillService twBillService;
    @Autowired
    private TwRechargeService twRechargeService;
    @Autowired
    private TwAdminLogService twAdminLogService;
    @Autowired
    private TwNoticeService twNoticeService;
    @Autowired
    private TwOnlineService twOnlineService;
    @Autowired
    private TwKjprofitService twKjprofitService;
    @Autowired
    private TwUserLogService twUserLogService;
    @Autowired
    private TwIssueService twIssueService;
    @Autowired
    private TwKjorderService twKjorderService;
    @Override
    public Integer countAllUsers() {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public Integer countLineUsers(String startTime, String endTime) {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("startTime", startTime);
        queryWrapper.le("endTime", endTime);
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
        Page<TwUser> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
        List<TwUser> list = baseMapper.listpage(objectPage, twUserVo);
        for(TwUser twUser:list){
            String invit1 = twUser.getInvit1();
            QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("invit_1", invit1);
            TwUser one1 = this.getOne(queryWrapper1);
            String username1 = one1.getUsername();
            twUser.setInvit1(username1);
            String invit2 = twUser.getInvit2();
            QueryWrapper<TwUser> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("invit_2", invit2);
            TwUser one2 = this.getOne(queryWrapper2);
            String username2 = one2.getUsername();
            twUser.setInvit2(username2);
            String invit3 = twUser.getInvit3();
            QueryWrapper<TwUser> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("invit_3", invit3);
            TwUser one3 = this.getOne(queryWrapper3);
            String username3 = one3.getUsername();
            twUser.setInvit3(username3);
        }
        objectPage.setRecords(list);
        return objectPage;
    }

    @Override
    public ResponseDTO addOrUpdate(TwUser twUser,HttpServletRequest request) throws IOException {
       if(twUser.getId() == null){
           String username = twUser.getUsername();
           String phone = twUser.getPhone();
           String password = twUser.getPassword();
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
           String encryptPwd = getEncryptPwd(password);
           String invite = generateRandomString();
           String ip = getClientIP(request);
           String locationByIP = getLocationByIP(ip);
           twUser.setPassword(encryptPwd);
           twUser.setInvit(invite);
           twUser.setAddr(locationByIP);
           this.save(twUser);
           return ResponseDTO.ok();
       }

       if(twUser.getId() != null){
           Integer id = twUser.getId();
           QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
           queryWrapper.eq("id", id);
           TwUser one = this.getOne(queryWrapper);
           one.setPassword(twUser.getPassword());
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
             one.setStatus(1);
             return this.updateById(one);
         }
         if(type == 4){         //禁止提币
             QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
             queryWrapper.eq("id", id);
             TwUser one = this.getOne(queryWrapper);
             one.setStatus(2);
             return this.updateById(one);
         }

        return true;
    }

    @Override
    public boolean setMoney(int uid, int type, double money, String bizhong) {
        String remark = "";
        String coinname = bizhong.toLowerCase();
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", uid);
        TwUser one = this.getOne(queryWrapper);

        if(type == 2){
            twUserCoinService.decre(uid,money,coinname);
            remark = "管理员手动减少";


            TwAdminLog twAdminLog = new TwAdminLog();
//                twAdminLog.setAdminId();
//                twAdminLog.setAdminUsername();
            twAdminLog.setAction("管理员手动减少");
//                twAdminLog.setIp();
            Instant instant = Instant.now();
            long epochMilli = instant.toEpochMilli();
            twAdminLog.setCreateTime((int) epochMilli);
            twAdminLog.setRemark("指定用户 "+uid+" 手动减少");
            twAdminLogService.save(twAdminLog);
        }

        if(type == 1){   //增加
            twUserCoinService.incre(uid,money,coinname);
            TwRecharge twRecharge = new TwRecharge();
            twRecharge.setUid(uid);
            twRecharge.setUsername(one.getUsername());
            twRecharge.setCoin(coinname);
            twRecharge.setNum(money);
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
            twAdminLog.setCreateTime((int) epochMilli);
            twAdminLog.setRemark("指定用户 "+uid+" 手动增加");
            twAdminLogService.save(twAdminLog);
        }

        double afternum = twUserCoinService.afternum(uid, coinname);
        TwBill twBill = new TwBill();
        twBill.setUid(uid);
        twBill.setUsername(one.getUsername());
        twBill.setCoinname(coinname);
        twBill.setNum(money);
        twBill.setAfternum(afternum);
        twBill.setType(1);
        twBill.setAddtime(new Date());
        twBill.setSt(1);
        twBill.setRemark(remark);
        twBillService.save(twBill);

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

    public  String getClientIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }


    private  String getLocationByIP(String ipAddress) throws IOException {
        String apiUrl = "http://ip-api.com/json/" + ipAddress;
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 解析 JSON 响应，提取地理位置信息
            String jsonResponse = response.toString();
            // 这里的解析方式仅作为演示，实际中你可能需要使用更复杂的 JSON 解析库
            String country = jsonResponse.split(",")[1].split(":")[1].replaceAll("\"", "");
            String region = jsonResponse.split(",")[4].split(":")[1].replaceAll("\"", "");

            return country + ", " + region;
        } else {
            throw new IOException("Unable to retrieve location information. HTTP Response Code: " + responseCode);
        }
    }
}
