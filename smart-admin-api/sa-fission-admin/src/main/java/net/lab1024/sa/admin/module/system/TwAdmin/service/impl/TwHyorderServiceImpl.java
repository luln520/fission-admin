package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwFooterDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwHyorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwHyorderVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.HyorderOneRes;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.CommonUtil;
import net.lab1024.sa.common.common.util.DateUtil;
import net.lab1024.sa.common.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.common.module.support.serialnumber.service.SerialNumberService;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

/**
 * 合约订单表(TwHyorder)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:23:17
 */
@Service("twHyorderService")
@Transactional
@Slf4j
public class TwHyorderServiceImpl extends ServiceImpl<TwHyorderDao, TwHyorder> implements TwHyorderService {

    @Autowired
    private TwUserService twUserService;

    @Autowired
    private TwHysettingService twHysettingService;

    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private TwBillService twBillService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private TwCompanyService twCompanyService;

    @Autowired
    private TwMockUserCoinService twMockUserCoinService;

    @Override
    public int countUnClosedOrders(int companyId) {
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "2"); // 添加查询条件
        queryWrapper.eq("company_id", companyId); // 添加查询条件
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public int countHyOrdersDay(String startTime, String endTime, int companyId) {


        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "2")// 添加查询条件
            .ge("buytime", startTime)
                .le("buytime", endTime)
           .eq("company_id", companyId); // 添加查询条件
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public BigDecimal orderSum(int companyId) {
        BigDecimal winHyorder = new BigDecimal(0);
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(num), 0) as winHyorder")
                .eq("status", 2)
                .eq("company_id", companyId);

        List<Map<String, Object>> winHyorderResult = this.baseMapper.selectMaps(queryWrapper);
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

        return winHyorder;

    }

    @Override
    public BigDecimal orderDay(String startTime, String endTime, int companyId) {
        BigDecimal winHyorder = new BigDecimal(0);
        BigDecimal lossHyorder = new BigDecimal(0);
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(ploss), 0) as winHyorder")
                .eq("status", 2)
                .ge("buytime", startTime)
                .le("buytime", endTime)
                .eq("company_id", companyId);

        List<Map<String, Object>> winHyorderResult = this.baseMapper.selectMaps(queryWrapper);
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

          return winHyorder;
    }

    @Override
    public BigDecimal winLosshyAllOrders(int companyId) {

        BigDecimal winHyorder = new BigDecimal(0);
        BigDecimal lossHyorder = new BigDecimal(0);
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(ploss), 0) as winHyorder")
                .eq("is_win", 1)
                .eq("status", 2)
                .eq("company_id", companyId);

        List<Map<String, Object>> winHyorderResult = this.baseMapper.selectMaps(queryWrapper);
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
        queryWrapper1.select("IFNULL(SUM(ploss), 0) as lossHyorder")
                .eq("is_win", 2)
                .eq("company_id", companyId)
                .eq("status", 2);

        List<Map<String, Object>> lossHyorderResult = this.baseMapper.selectMaps(queryWrapper1);
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

        BigDecimal hyorder = winHyorder.subtract(lossHyorder);

        return hyorder;

    }

    @Override
    public BigDecimal winLosshyDayOrders(String startTime, String endTime, int companyId) {
        BigDecimal winHyorder = new BigDecimal(0);
        BigDecimal lossHyorder = new BigDecimal(0);
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("IFNULL(SUM(ploss), 0) as winHyorder")
                .eq("is_win", 1)
                .eq("status", 2)
                .ge("buytime", startTime)
                .le("buytime", endTime)
                .eq("company_id", companyId);

        List<Map<String, Object>> winHyorderResult = this.baseMapper.selectMaps(queryWrapper);
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
        queryWrapper1.select("IFNULL(SUM(ploss), 0) as lossHyorder")
                .eq("is_win", 2)
                .ge("buytime", startTime)
                .le("buytime", endTime)
                .eq("company_id", companyId)
                .eq("status", 2);

        List<Map<String, Object>> lossHyorderResult = this.baseMapper.selectMaps(queryWrapper1);
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

        BigDecimal hyorder = winHyorder.subtract(lossHyorder);

        return hyorder;
    }

    @Override
    public TwHyorder hyorderId(int id) {
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id); // 添加查询条件
        return this.getOne(queryWrapper);
    }

    @Override
    public ResponseDTO getHyorderOne(int id){

        String timerType = "";
        HyorderOneRes hyorderOneRes = new HyorderOneRes();
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id); // 添加查询条件
        TwHyorder twHyorder = this.getOne(queryWrapper);
        if(twHyorder.getHyzd() == 1){
            timerType = "买涨";
        }else{
            timerType = "买跌";
        }
        hyorderOneRes.setTimerType(timerType);
        String coinname = twHyorder.getCoinname();
        BigDecimal buyprice = twHyorder.getBuyprice();
        hyorderOneRes.setBuyprice(buyprice);
        String time = twHyorder.getTime();
        hyorderOneRes.setTime(time);
        BigDecimal num = twHyorder.getNum();
        hyorderOneRes.setNum(num);
        Integer hyzd = twHyorder.getHyzd();
        hyorderOneRes.setHyzd(hyzd);
        Integer status = twHyorder.getStatus();
        if( status == 1){
            Date selltime = twHyorder.getSelltime();
            int t = DateUtil.dateToSeconds(selltime);
            if(t <= 0){
                //表示已结算
                hyorderOneRes.setStatusstr("正在结算中");
                hyorderOneRes.setClear(0);
                hyorderOneRes.setCode(2);

                return ResponseDTO.ok(hyorderOneRes);
            }else{
                //获取当前交易对的单价
                String symbol = coinname.toLowerCase().replace("/", "");
                String str = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol="+symbol;
                Map<String, Object> map = CommonUtil.executeGet(str);
                JSONObject res = JSONObject.parseObject(map.get("res").toString());
                JSONArray data = JSONArray.parseArray(res.get("data").toString());
                JSONObject jsonObject = JSONObject.parseObject(data.get(0).toString());

                BigDecimal close = new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP);

                hyorderOneRes.setStatusstr("正在结算中");
                hyorderOneRes.setClear(0);
                hyorderOneRes.setCode(1);
                hyorderOneRes.setTimerNewprice(close);
                return ResponseDTO.ok(hyorderOneRes);
            }
        }else{
            //表示已经结算，则显示盈亏即可
            hyorderOneRes.setClear(0);
            hyorderOneRes.setCode(1);
            hyorderOneRes.setPloss(twHyorder.getPloss());
            return ResponseDTO.ok(hyorderOneRes);
        }
    }

    @Override
    public ResponseDTO<List<TwHyorder>> gethyorder(int uid) {
        List<TwHyorder> list1 = new ArrayList<>();
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid); // 添加查询条件
//        queryWrapper.eq("status", 1); // 添加查询条件
        queryWrapper.orderByDesc("id");
        List<TwHyorder> list = this.list(queryWrapper);
        for(TwHyorder twHyorder:list){
            Date selltime = twHyorder.getSelltime();
            int t = DateUtil.dateToSeconds(selltime);
            twHyorder.setTimeResidue(t);

            list1.add(twHyorder);
        }

        return ResponseDTO.ok(list1);
    }

    @Override
    public ResponseDTO<List<TwHyorder>> cbillList(int uid) {
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid); // 添加查询条件
        queryWrapper.orderByDesc("id");
        return ResponseDTO.ok(this.list(queryWrapper));
    }

    @Override
    public ResponseDTO<List<TwHyorder>> contractTy(int uid, int type) {
        if(type == 1){
            QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uid", uid);
            queryWrapper.eq("status",1);
            queryWrapper.orderByDesc("id");
            return ResponseDTO.ok(this.list(queryWrapper));
        }

        if(type == 2){
            QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uid", uid);
            queryWrapper.ne("status",1);
            queryWrapper.orderByDesc("id");
            return ResponseDTO.ok(this.list(queryWrapper));
        }
        return null;
    }

    @Override
    public ResponseDTO<TwHyorder> cbillinfo(int uid, int id) {
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        queryWrapper.eq("id",id);
        return ResponseDTO.ok(this.getOne(queryWrapper));
    }

    @Override
    public IPage<TwHyorder> listpage(TwHyorderVo twHyorderVo, HttpServletRequest request) {
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        int companyId = byId.getCompanyId();
        TwCompany company = twCompanyService.getById(companyId);
        int inviteType = company.getInviteType();

        if(roleEmployeeVO.getWordKey().equals("admin") || roleEmployeeVO.getWordKey().equals("backend")){
            Page<TwHyorder> objectPage = new Page<>(twHyorderVo.getPageNum(), twHyorderVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, twHyorderVo));
            return objectPage;
        }

        if(roleEmployeeVO.getWordKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwHyorder> objectPage = new Page<>(twHyorderVo.getPageNum(), twHyorderVo.getPageSize());
                twHyorderVo.setDepartmentId(byId.getDepartmentId());
                objectPage.setRecords(baseMapper.listpage(objectPage, twHyorderVo));
                return objectPage;
            }else{
                Page<TwHyorder> objectPage = new Page<>(twHyorderVo.getPageNum(), twHyorderVo.getPageSize());
                if(inviteType == 1){
                    twHyorderVo.setEmployeeId(byId.getEmployeeId());
                }
                objectPage.setRecords(baseMapper.listpage(objectPage, twHyorderVo));
                return objectPage;
            }
        }
        return null;
    }

    @Override
    public boolean editKongyK(Integer kongyk, String ids) {
        String[] split = ids.split(",");
        for(int i = 0 ; i<split.length ; i++){
            QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", split[i]); // 添加查询条件
            TwHyorder one = this.getOne(queryWrapper);
            one.setKongyk(kongyk);
            this.updateById(one);
        }
        return true;
    }

    @Override
    public ResponseDTO creatorder(int uid, String ctime, BigDecimal ctzed, String ccoinname, int ctzfx, BigDecimal cykbl,String language, String plantime,int intplantime) {

            QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", uid); // 添加查询条件
            TwUser twUser = twUserService.getOne(queryWrapper);
            Integer userType = twUser.getUserType();
            if(userType == 1){
                return userOrder(twUser, ctime,  ctzed,  ccoinname,  ctzfx,  cykbl, language,  plantime, intplantime) ;
            }
            if(userType == 2){
               return mockUserOrder(twUser, ctime,  ctzed,  ccoinname,  ctzfx,  cykbl, language, plantime, intplantime) ;
            }

            return null;
    }

    public ResponseDTO userOrder(TwUser twUser ,String ctime, BigDecimal ctzed, String ccoinname, int ctzfx, BigDecimal cykbl,String language, String plantime,int intplantime){

        Integer uid = twUser.getId();
        String orderNo = serialNumberService.generate(SerialNumberIdEnum.ORDER);

        TwCompany company = twCompanyService.getById(twUser.getCompanyId());
        int inviteType = company.getInviteType();
        String invite = "";
        if(inviteType == 1){
            EmployeeEntity byInvite = employeeService.getById(Long.valueOf(twUser.getInvit1()));//获取代理人信息
            if(byInvite == null){
                QueryWrapper<TwUser> queryWrapper4 = new QueryWrapper<>();
                queryWrapper4.eq("id", twUser.getInvit1()); // 添加查询条件
                TwUser puser = twUserService.getOne(queryWrapper4);
                invite = puser.getInvit1();
            }
            invite = String.valueOf(byInvite.getEmployeeId());
        }

        //获取会员资产
        QueryWrapper<TwUserCoin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("userid", uid); // 添加查询条件
        TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper1);

        if(twUser.getRzstatus() != 2){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("请先完成实名认证！");
            }else{
                return ResponseDTO.userErrorParam("Please complete real-name authentication first！");
            }
        }

        if(twUser.getBuyOn() == 2){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("您的账户已被禁止交易，请联系客服！");
            }else{
                return ResponseDTO.userErrorParam("Your account has been banned from trading, please contact customer service！");
            }
        }

//            if(ctzed.compareTo(twHysetting.getHyMin()) < 0){
//                ResponseDTO.userErrorParam("不能小于最低投资额度");
//            }

//            BigDecimal hySxf = twHysetting.getHySxf();

//            Integer companyId = twUser.getCompanyId();
//            QueryWrapper<TwCompany> queryWrapper2 = new QueryWrapper<>();
//            queryWrapper2.eq("id", companyId); // 添加查询条件
        BigDecimal hyFee = company.getHyFee();
        BigDecimal tmoneys= ctzed.add(hyFee);
        if(twUserCoin.getUsdt().compareTo(tmoneys) < 0){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("余额不足！");
            }else{
                return ResponseDTO.userErrorParam("Insufficient balance！");
            }
        }



        String symbol = ccoinname.toLowerCase().replace("/", "");
        String str = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol="+symbol;
        Map<String, Object> map = CommonUtil.executeGet(str);
        JSONObject res = JSONObject.parseObject(map.get("res").toString());
        JSONArray data = JSONArray.parseArray(res.get("data").toString());
        JSONObject jsonObject = JSONObject.parseObject(data.get(0).toString());

        BigDecimal close = new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP);

        int time = 0;
        Date plandate = DateUtil.str2DateTime(plantime);
        Date selltime = plandate;
        // 使用正则表达式分割字符串，非数字字符作为分隔符
        String[] parts = ctime.split("\\D+");

        // 输出第一个部分（即数字部分）
        if (parts.length > 0) {
            time = Integer.parseInt(parts[0]);
        }else{
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("结算周期有误！");
            }else{
                return ResponseDTO.userErrorParam("There is an error in the billing cycle！");
            }
        }

        // 使用正则表达式将所有数字字符替换为空字符串
        String nonDigits = ctime.replaceAll("\\d", "");

        String upperCase = nonDigits.toUpperCase();
        if(upperCase.contains("S")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        }else if(upperCase.contains("M")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        } else if(upperCase.contains("H")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        }else if(upperCase.contains("DAY")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        }
        else if(upperCase.contains("WEEK")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        }
        else if(upperCase.contains("MON")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        }
        else if(upperCase.contains("YEAR")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        }else{
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("结算周期有误！");
            }else{
                return ResponseDTO.userErrorParam("There is an error in the billing cycle！");
            }
        }

        TwHyorder twHyorder = new TwHyorder();
        twHyorder.setUid(uid);
        twHyorder.setOrderNo(orderNo);
        twHyorder.setUsername(twUser.getUsername());
        twHyorder.setNum(ctzed);
        twHyorder.setHybl(cykbl);
        twHyorder.setCompanyId(twUser.getCompanyId());
        twHyorder.setUserCode(twUser.getUserCode());
        twHyorder.setHyzd(ctzfx);
        twHyorder.setBuyOrblance(twUserCoin.getUsdt().subtract(tmoneys));
        twHyorder.setCoinname(ccoinname);
        twHyorder.setStatus(0);
        twHyorder.setIsWin(0);
        twHyorder.setPlantime(DateUtil.str2DateTime(plantime));
        twHyorder.setIntplantime(intplantime);
        twHyorder.setOrderType(1);
        twHyorder.setPath(twUser.getPath());
        twHyorder.setDepartment(twUser.getDepatmentId());
        twHyorder.setBuytime(DateUtil.stract12());
        twHyorder.setSelltime(selltime);
        twHyorder.setIntselltime((int) (selltime.getTime()/1000));
        twHyorder.setBuyprice(close);
        twHyorder.setSellprice(new BigDecimal(0));
        twHyorder.setPloss(new BigDecimal(0));
        twHyorder.setTime(ctime);
        twHyorder.setKongyk(0);
        twHyorder.setInvit(invite);
        this.save(twHyorder);
        //扣除USDT额度
        twUserCoinService.decre(uid,tmoneys,twUserCoin.getUsdt());

        //创建财务日志
        TwBill twBill = new TwBill();
        twBill.setUserCode(twUser.getUserCode());
        twBill.setUid(uid);
        twBill.setUsername(twUser.getUsername());
        twBill.setNum(ctzed);
        twBill.setCompanyId(twUser.getCompanyId());
        twBill.setCoinname("usdt");
        twBill.setAfternum(twUserCoinService.afternum(uid));
        twBill.setType(3);
        twBill.setPath(twUser.getPath());
        twBill.setDepartment(twUser.getDepatmentId());
        twBill.setAddtime(new Date());
        twBill.setSt(2);
        twBill.setRemark("购买"+ ccoinname + "秒合约");
        twBillService.save(twBill);
        if(language.equals("zh")){
            return ResponseDTO.ok(orderNo);
        }else{
            return ResponseDTO.ok(orderNo);
        }
    }
    public ResponseDTO mockUserOrder(TwUser twUser ,String ctime, BigDecimal ctzed, String ccoinname, int ctzfx, BigDecimal cykbl,String language, String plantime,int intplantime){
        Integer uid = twUser.getId();

        String orderNo = serialNumberService.generate(SerialNumberIdEnum.ORDER);

        TwCompany company = twCompanyService.getById(twUser.getCompanyId());
        int inviteType = company.getInviteType();
        String invite = "";
        if(inviteType == 1){
            EmployeeEntity byInvite = employeeService.getById(Long.valueOf(twUser.getInvit1()));//获取代理人信息
            if(byInvite == null){
                QueryWrapper<TwUser> queryWrapper4 = new QueryWrapper<>();
                queryWrapper4.eq("id", twUser.getInvit1()); // 添加查询条件
                TwUser puser = twUserService.getOne(queryWrapper4);
                invite = puser.getInvit1();
            }
            invite = String.valueOf(byInvite.getEmployeeId());
        }

        //获取会员资产
        QueryWrapper<TwMockUserCoin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("userid", uid); // 添加查询条件
        TwMockUserCoin twMockUserCoin = twMockUserCoinService.getOne(queryWrapper1);

//        if(twUser.getRzstatus() != 2){
//            if(language.equals("zh")){
//                return ResponseDTO.userErrorParam("请先完成实名认证！");
//            }else{
//                return ResponseDTO.userErrorParam("Please complete real-name authentication first！");
//            }
//        }

        if(twUser.getBuyOn() == 2){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("您的账户已被禁止交易，请联系客服！");
            }else{
                return ResponseDTO.userErrorParam("Your account has been banned from trading, please contact customer service！");
            }
        }

//            if(ctzed.compareTo(twHysetting.getHyMin()) < 0){
//                ResponseDTO.userErrorParam("不能小于最低投资额度");
//            }

//            BigDecimal hySxf = twHysetting.getHySxf();

//            Integer companyId = twUser.getCompanyId();
//            QueryWrapper<TwCompany> queryWrapper2 = new QueryWrapper<>();
//            queryWrapper2.eq("id", companyId); // 添加查询条件
        BigDecimal hyFee = company.getHyFee();
        BigDecimal tmoneys= ctzed.add(hyFee);
        if(twMockUserCoin.getUsdt().compareTo(tmoneys) < 0){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("余额不足！");
            }else{
                return ResponseDTO.userErrorParam("Insufficient balance！");
            }
        }



        String symbol = ccoinname.toLowerCase().replace("/", "");
        String str = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol="+symbol;
        Map<String, Object> map = CommonUtil.executeGet(str);
        JSONObject res = JSONObject.parseObject(map.get("res").toString());
        JSONArray data = JSONArray.parseArray(res.get("data").toString());
        JSONObject jsonObject = JSONObject.parseObject(data.get(0).toString());

        BigDecimal close = new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP);

        int time = 0;
        Date plandate = DateUtil.str2DateTime(plantime);
        Date selltime = plandate;
        // 使用正则表达式分割字符串，非数字字符作为分隔符
        String[] parts = ctime.split("\\D+");

        // 输出第一个部分（即数字部分）
        if (parts.length > 0) {
            time = Integer.parseInt(parts[0]);
        }else{
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("结算周期有误！");
            }else{
                return ResponseDTO.userErrorParam("There is an error in the billing cycle！");
            }
        }

        // 使用正则表达式将所有数字字符替换为空字符串
        String nonDigits = ctime.replaceAll("\\d", "");

        String upperCase = nonDigits.toUpperCase();
        if(upperCase.contains("S")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        }else if(upperCase.contains("M")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        } else if(upperCase.contains("H")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        }else if(upperCase.contains("DAY")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        }
        else if(upperCase.contains("WEEK")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        }
        else if(upperCase.contains("MON")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        }
        else if(upperCase.contains("YEAR")){
            selltime = DateUtil.dateToDate(plandate,time,upperCase);
        }else{
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("结算周期有误！");
            }else{
                return ResponseDTO.userErrorParam("There is an error in the billing cycle！");
            }
        }

        TwHyorder twHyorder = new TwHyorder();
        twHyorder.setUid(uid);
        twHyorder.setOrderNo(orderNo);
        twHyorder.setUsername(twUser.getUsername());
        twHyorder.setNum(ctzed);
        twHyorder.setHybl(cykbl);
        twHyorder.setCompanyId(twUser.getCompanyId());
        twHyorder.setUserCode(twUser.getUserCode());
        twHyorder.setHyzd(ctzfx);
        twHyorder.setBuyOrblance(twMockUserCoin.getUsdt().subtract(tmoneys));
        twHyorder.setCoinname(ccoinname);
        twHyorder.setStatus(0);
        twHyorder.setIsWin(0);
        twHyorder.setPlantime(DateUtil.str2DateTime(plantime));
        twHyorder.setIntplantime(intplantime);
        twHyorder.setOrderType(2);
        twHyorder.setPath(twUser.getPath());
        twHyorder.setDepartment(twUser.getDepatmentId());
        twHyorder.setBuytime(DateUtil.stract12());
        twHyorder.setSelltime(selltime);
        twHyorder.setIntselltime((int) (selltime.getTime()/1000));
        twHyorder.setBuyprice(close);
        twHyorder.setSellprice(new BigDecimal(0));
        twHyorder.setPloss(new BigDecimal(0));
        twHyorder.setTime(ctime);
        twHyorder.setKongyk(0);
        twHyorder.setInvit(invite);
        this.save(twHyorder);
        //扣除USDT额度
        twMockUserCoinService.decre(uid,tmoneys,twMockUserCoin.getUsdt());

//        //创建财务日志
//        TwBill twBill = new TwBill();
//        twBill.setUserCode(twUser.getUserCode());
//        twBill.setUid(uid);
//        twBill.setUsername(twUser.getUsername());
//        twBill.setNum(ctzed);
//        twBill.setCompanyId(twUser.getCompanyId());
//        twBill.setCoinname("usdt");
//        twBill.setAfternum(twUserCoinService.afternum(uid));
//        twBill.setType(3);
//        twBill.setPath(twUser.getPath());
//        twBill.setDepartment(twUser.getDepatmentId());
//        twBill.setAddtime(new Date());
//        twBill.setSt(2);
//        twBill.setRemark("购买"+ ccoinname + "秒合约");
//        twBillService.save(twBill);
        if(language.equals("zh")){
            return ResponseDTO.ok(orderNo);
        }else{
            return ResponseDTO.ok(orderNo);
        }
    }

    @Override
    public ResponseDTO orderNo(String orderNo) {
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        return ResponseDTO.ok(this.getOne(queryWrapper));
    }

}
