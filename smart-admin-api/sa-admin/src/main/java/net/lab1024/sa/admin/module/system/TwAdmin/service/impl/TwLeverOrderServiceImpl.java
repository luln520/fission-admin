package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwLeverOrderMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
* @author 1
* @description 针对表【tw_lever_order(杠杆订单表)】的数据库操作Service实现
* @createDate 2024-01-27 12:46:05
*/
@Service
@Transactional
@Slf4j
public class TwLeverOrderServiceImpl extends ServiceImpl<TwLeverOrderMapper, TwLeverOrder>
    implements TwLeverOrderService {

    @Autowired
    private TwUserService twUserService;

    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private TwBillService twBillService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TwNoticeService twNoticeService;

    @Autowired
    private SerialNumberService serialNumberService;
    @Override
    public IPage<TwLeverOrder> listpage(LeverVo leverVo, HttpServletRequest request) {
        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        RoleEmployeeVO roleEmployeeVO = employeeService.selectRoleByEmployeeId(uidToken);

        if(roleEmployeeVO.getKey().equals("admin") || roleEmployeeVO.getKey().equals("backend")){
            Page<TwLeverOrder> objectPage = new Page<>(leverVo.getPageNum(), leverVo.getPageSize());
            objectPage.setRecords(this.baseMapper.listpage(objectPage, leverVo));
            return objectPage;
        }

        if(roleEmployeeVO.getKey().equals("agent")){
            int supervisorFlag = byId.getSupervisorFlag();
            if(supervisorFlag == 1){
                Page<TwLeverOrder> objectPage = new Page<>(leverVo.getPageNum(), leverVo.getPageSize());
                leverVo.setDepartmentId(byId.getDepartmentId());
                objectPage.setRecords(this.baseMapper.listpage(objectPage, leverVo));
                return objectPage;
            }else{
                Page<TwLeverOrder> objectPage = new Page<>(leverVo.getPageNum(), leverVo.getPageSize());
                leverVo.setEmployeeId(byId.getEmployeeId());
                objectPage.setRecords(this.baseMapper.listpage(objectPage, leverVo));
                return objectPage;
            }
        }
        return null;
    }

    @Override
    public IPage<TwLeverOrder> listPcPage(LeverVo leverVo) {
        Page<TwLeverOrder> objectPage = new Page<>(leverVo.getPageNum(), leverVo.getPageSize());
        objectPage.setRecords(this.baseMapper.listpage(objectPage, leverVo));
        return objectPage;
    }

    @Override
    public ResponseDTO creatorder(int uid, String ccoinname, int win, int loss, int fold, int hyzd,BigDecimal num, BigDecimal ploss, BigDecimal premium, String language) {
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", uid); // 添加查询条件
        TwUser twUser = twUserService.getOne(queryWrapper);

        String invite = "";
        EmployeeEntity byInvite = employeeService.getById(Long.valueOf(twUser.getInvit1()));//获取代理人信息
        if(byInvite == null){
            QueryWrapper<TwUser> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.eq("id", twUser.getInvit1()); // 添加查询条件
            TwUser puser = twUserService.getOne(queryWrapper4);
            invite = puser.getInvit1();
        }
        invite = String.valueOf(byInvite.getEmployeeId());

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

        if(twUserCoin.getUsdt().compareTo(num) < 0){
            if(language.equals("zh")){
                return ResponseDTO.userErrorParam("余额不足！");
            }else{
                return ResponseDTO.userErrorParam("Insufficient balance！");
            }
        }

        String symbol = ccoinname.toLowerCase().replace("/", "");
        String str = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol="+symbol;
        log.info("火币调用api路径：{}"+ str);
        Map<String, Object> map = CommonUtil.executeGet(str);
        log.info("火币调用api 返回参数：{}" +map);
        JSONObject res = JSONObject.parseObject(map.get("res").toString());
        JSONArray data = JSONArray.parseArray(res.get("data").toString());
        JSONObject jsonObject = JSONObject.parseObject(data.get(0).toString());

        BigDecimal close = new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP);

        String orderNo = serialNumberService.generate(SerialNumberIdEnum.ORDER);

        TwLeverOrder twLeverOrder = new TwLeverOrder();
        twLeverOrder.setUid(uid);
        twLeverOrder.setOrderNo(orderNo);
        twLeverOrder.setUsername(twUser.getUsername());
        twLeverOrder.setNum(num);
        twLeverOrder.setHyzd(hyzd);
        twLeverOrder.setBuyOrblance(twUserCoin.getUsdt().subtract(num));
        twLeverOrder.setCoinname(ccoinname);
        twLeverOrder.setStatus(1);
        twLeverOrder.setIsWin(0);
        twLeverOrder.setLoss(loss);
        twLeverOrder.setWin(win);
        twLeverOrder.setFold(fold);
        twLeverOrder.setPath(twUser.getPath());
        twLeverOrder.setDepartment(twUser.getDepatmentId());
        twLeverOrder.setBuytime(new Date());
//        Date selltime = DateUtil.secondsDateAddSeconds(new Date(), ctime * 60);
//        twLeverOrder.setSelltime(selltime);
//        twLeverOrder.setIntselltime((int) (selltime.getTime()/1000));
        twLeverOrder.setBuyprice(close);
        twLeverOrder.setSellprice(new BigDecimal(0));
        twLeverOrder.setPloss(ploss);
        twLeverOrder.setPremium(premium);
        twLeverOrder.setKongyk(0);
        twLeverOrder.setInvit(invite);
        this.save(twLeverOrder);
        //扣除USDT额度
        BigDecimal money = num.subtract(premium);
        twUserCoinService.decre(uid,money,twUserCoin.getUsdt());

        //创建财务日志
        TwBill twBill = new TwBill();
        twBill.setUid(uid);
        twBill.setUsername(twUser.getUsername());
        twBill.setNum(num);
        twBill.setCoinname("usdt");
        twBill.setAfternum(twUserCoinService.afternum(uid));
        twBill.setType(9);
        twBill.setPath(twUser.getPath());
        twBill.setDepartment(twUser.getDepatmentId());
        twBill.setAddtime(new Date());
        twBill.setSt(2);
        twBill.setRemark("购买"+ ccoinname + "杠杆交易");
        twBillService.save(twBill);
        if(language.equals("zh")){
            return ResponseDTO.ok("建仓成功！");
        }else{
            return ResponseDTO.ok("Position opening successful！");
        }
    }

    @Override
    public ResponseDTO closeorder(int uid, int lid,String language) {
        QueryWrapper<TwLeverOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        queryWrapper.eq("id", lid);
        TwLeverOrder twLeverOrder = this.getOne(queryWrapper);
        if(twLeverOrder == null){
            if(language.equals("zh")){
                return ResponseDTO.ok("没有此订单！");
            }else{
                return ResponseDTO.ok("There is no such order！");
            }
        }

        if(twLeverOrder.getStatus()!=1){
            if(language.equals("zh")){
                return ResponseDTO.ok("订单已结算！");
            }else{
                return ResponseDTO.ok("Order settled！");
            }
        }

            String coinname = twLeverOrder.getCoinname();
            String symbol = coinname.toLowerCase().replace("/", "");
            String url = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol="+symbol;
            BigDecimal newprice = getnewprice(url);
            // 创建 Random 对象
            Random random = new Random();
            // 生成在[0.1, 0.9999]范围内的随机数
            BigDecimal randnum = new BigDecimal(Double.toString(0.1 + (0.9999 - 0.1) * random.nextDouble()));

            BigDecimal buyprice = twLeverOrder.getBuyprice();   //建仓单价
            Integer hyzd = twLeverOrder.getHyzd();  //合约方向
            Integer kongyk = twLeverOrder.getKongyk(); //单控设置
            String username = twLeverOrder.getUsername();
//            BigDecimal hybl = twLeverOrder.getHybl();
//            MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
//            BigDecimal ylnum = num.multiply(hybl.divide(new BigDecimal(100), mathContext));
//            BigDecimal money = twLeverOrder.getNum().add(twLeverOrder.getPloss()).subtract(twLeverOrder.getPremium());  //盈利金额
//            BigDecimal ploss = twLeverOrder.getPloss().subtract(twLeverOrder.getPremium());
            QueryWrapper<TwUserCoin> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("userid",uid);
            TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper3);
            BigDecimal sellprice = new BigDecimal(0);
            MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
            //买涨
              if(hyzd == 1){
                    if(kongyk == 1){  //盈利
                        BigDecimal ploss =  twLeverOrder.getNum().multiply(new BigDecimal(twLeverOrder.getWin()).divide(new BigDecimal(100)),mathContext).multiply(new BigDecimal(twLeverOrder.getFold()));  //盈利金额
                        BigDecimal money = ploss.add(twLeverOrder.getNum());

                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = newprice;
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = newprice.add(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = buyprice.add(randnum);
                        }
                        //增加资产
                        twUserCoinService.incre(uid,money,twUserCoin.getUsdt());

                        //修改订单状态
                        twLeverOrder.setStatus(2);
                        twLeverOrder.setIsWin(1);
                        twLeverOrder.setSellprice(sellprice);
                        twLeverOrder.setPloss(ploss);
                        this.updateById(twLeverOrder);

                        //写财务日志
                        addlog(uid,username,ploss);

                    }

                    if(kongyk == 2){ //亏损
                        BigDecimal ploss =  twLeverOrder.getNum().multiply(new BigDecimal(twLeverOrder.getLoss()).divide(new BigDecimal(100)),mathContext).multiply(new BigDecimal(twLeverOrder.getFold())); //亏损金额
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = newprice;
                        }
                        //修改订单状态
                        twLeverOrder.setStatus(2);
                        twLeverOrder.setIsWin(2);
                        twLeverOrder.setSellprice(sellprice);
                        twLeverOrder.setPloss(ploss);
                        this.updateById(twLeverOrder);

                        //减少资产
                        twUserCoinService.decre(uid,ploss,twUserCoin.getUsdt());

                        //写财务日志
                        addlog(uid,username,ploss);
                    }

                  if(kongyk == 0){   //未控
                        if (buyprice.compareTo(newprice) < 0) {   //盈利
                            BigDecimal ploss =  twLeverOrder.getNum().multiply(new BigDecimal(twLeverOrder.getWin()).divide(new BigDecimal(100)),mathContext).multiply(new BigDecimal(twLeverOrder.getFold()));  //盈利金额
                            BigDecimal money = ploss.add(twLeverOrder.getNum());
                            //增加资产
                            twUserCoinService.incre(uid, money, twUserCoin.getUsdt());

                            //修改订单状态
                            twLeverOrder.setStatus(2);
                            twLeverOrder.setIsWin(1);
                            twLeverOrder.setSellprice(sellprice);
                            twLeverOrder.setPloss(ploss);
                            this.updateById(twLeverOrder);

                            //写财务日志
                            addlog(uid, username, ploss);


                        } else if (newprice.compareTo(buyprice) == 0) {
                            BigDecimal ploss =  twLeverOrder.getNum().multiply(new BigDecimal(twLeverOrder.getLoss()).divide(new BigDecimal(100)),mathContext).multiply(new BigDecimal(twLeverOrder.getFold())); //亏损金额
                            //修改订单状态
                            twLeverOrder.setStatus(2);
                            twLeverOrder.setIsWin(2);
                            twLeverOrder.setSellprice(sellprice);
                            twLeverOrder.setPloss(ploss);
                            this.updateById(twLeverOrder);

                            //减少资产
                            twUserCoinService.decre(uid, ploss, twUserCoin.getUsdt());

                            //写财务日志
                            addlog(uid, username, ploss);
                        } else if (newprice.compareTo(buyprice) < 0) {   //亏损
                            BigDecimal ploss =  twLeverOrder.getNum().multiply(new BigDecimal(twLeverOrder.getLoss()).divide(new BigDecimal(100)),mathContext).multiply(new BigDecimal(twLeverOrder.getFold())); //亏损金额
                            //修改订单状态
                            twLeverOrder.setStatus(2);
                            twLeverOrder.setIsWin(2);
                            twLeverOrder.setSellprice(sellprice);
                            twLeverOrder.setPloss(ploss);
                            this.updateById(twLeverOrder);

                            //减少资产
                            twUserCoinService.decre(uid, ploss, twUserCoin.getUsdt());

                            //写财务日志
                            addlog(uid, username, ploss);
                        }
                    }

                }
                //买跌
                if(hyzd == 2){
                        if(kongyk == 1){ //盈利

                            BigDecimal ploss =  twLeverOrder.getNum().multiply(new BigDecimal(twLeverOrder.getWin()).divide(new BigDecimal(100)),mathContext).multiply(new BigDecimal(twLeverOrder.getFold()));  //盈利金额
                            BigDecimal money = ploss.add(twLeverOrder.getNum());

                            if (buyprice.compareTo(newprice) < 0) {
                                sellprice = buyprice.subtract(randnum);
                            } else if (newprice.compareTo(buyprice) == 0) {
                                sellprice = buyprice.subtract(randnum);
                            } else if (newprice.compareTo(buyprice) < 0) {
                                sellprice = newprice;
                            }

                            //增加资产
                            twUserCoinService.incre(uid,money,twUserCoin.getUsdt());

                            //修改订单状态
                            twLeverOrder.setStatus(2);
                            twLeverOrder.setIsWin(1);
                            twLeverOrder.setSellprice(sellprice);
                            twLeverOrder.setPloss(ploss);
                            this.updateById(twLeverOrder);

                            //写财务日志
                            addlog(uid,username,ploss);
                        }

                        if(kongyk == 2){ //亏损
                            BigDecimal ploss =  twLeverOrder.getNum().multiply(new BigDecimal(twLeverOrder.getLoss()).divide(new BigDecimal(100)),mathContext).multiply(new BigDecimal(twLeverOrder.getFold())); //亏损金额
                            if (buyprice.compareTo(newprice) < 0) {
                                sellprice = newprice;
                            } else if (newprice.compareTo(buyprice) == 0) {
                                sellprice = buyprice.add(randnum);
                            } else if (newprice.compareTo(buyprice) < 0) {
                                sellprice = buyprice.add(randnum);
                            }

                            //修改订单状态
                            twLeverOrder.setStatus(2);
                            twLeverOrder.setIsWin(2);
                            twLeverOrder.setSellprice(sellprice);
                            twLeverOrder.setPloss(ploss);
                            this.updateById(twLeverOrder);

                            //减少资产
                            twUserCoinService.decre(uid,ploss,twUserCoin.getUsdt());

                            //写财务日志
                            addlog(uid,username,ploss);
                        }


                    if(kongyk == 0) {                              //未控
                        if (buyprice.compareTo(newprice) < 0) {   //亏损
                            BigDecimal ploss =  twLeverOrder.getNum().multiply(new BigDecimal(twLeverOrder.getLoss()).divide(new BigDecimal(100)),mathContext).multiply(new BigDecimal(twLeverOrder.getFold())); //亏损金额
                            //修改订单状态
                            twLeverOrder.setStatus(2);
                            twLeverOrder.setIsWin(2);
                            twLeverOrder.setSellprice(sellprice);
                            twLeverOrder.setPloss(ploss);
                            this.updateById(twLeverOrder);

                            //减少资产
                            twUserCoinService.decre(uid, ploss, twUserCoin.getUsdt());

                            //写财务日志
                            addlog(uid, username, ploss);

                        } else if (newprice.compareTo(buyprice) == 0) {
                            BigDecimal ploss =  twLeverOrder.getNum().multiply(new BigDecimal(twLeverOrder.getLoss()).divide(new BigDecimal(100)),mathContext).multiply(new BigDecimal(twLeverOrder.getFold())); //亏损金额
                            //修改订单状态
                            twLeverOrder.setStatus(2);
                            twLeverOrder.setIsWin(2);
                            twLeverOrder.setSellprice(sellprice);
                            twLeverOrder.setPloss(ploss);
                            this.updateById(twLeverOrder);

                            //减少资产
                            twUserCoinService.decre(uid, ploss, twUserCoin.getUsdt());

                            //写财务日志
                            addlog(uid, username, ploss);

                        } else if (newprice.compareTo(buyprice) < 0) {   //盈利
                            BigDecimal ploss =  twLeverOrder.getNum().multiply(new BigDecimal(twLeverOrder.getWin()).divide(new BigDecimal(100)),mathContext).multiply(new BigDecimal(twLeverOrder.getFold()));  //盈利金额
                            BigDecimal money = ploss.add(twLeverOrder.getNum());
                            //增加资产
                            twUserCoinService.incre(uid, money, twUserCoin.getUsdt());

                            //修改订单状态
                            twLeverOrder.setStatus(2);
                            twLeverOrder.setIsWin(1);
                            twLeverOrder.setSellprice(sellprice);
                            twLeverOrder.setPloss(ploss);
                            this.updateById(twLeverOrder);

                            //写财务日志
                            addlog(uid, username, ploss);
                        }
                    }
                }
                 return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO editKonglo(int kongyk, int id) {
        QueryWrapper<TwLeverOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        TwLeverOrder twLeverOrder = this.getOne(queryWrapper);
        if(kongyk == 1){  //盈利
            twLeverOrder.setKongyk(1);
            this.updateById(twLeverOrder);
        }

        if(kongyk == 2){ //亏损
            twLeverOrder.setKongyk(2);
            this.updateById(twLeverOrder);
        }
         return ResponseDTO.ok();
    }

    public BigDecimal getnewprice(String url){
        Map<String, Object> map = CommonUtil.executeGet(url);
        JSONObject res = JSONObject.parseObject(map.get("res").toString());
        JSONArray data = JSONArray.parseArray(res.get("data").toString());
        JSONObject jsonObject = JSONObject.parseObject(data.get(0).toString());

        BigDecimal close = new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP);
        return close;
    }

    public void addlog(int uid, String username,BigDecimal money){
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",uid);
        TwUser twUser = twUserService.getOne(queryWrapper);

        //创建财务日志
        TwBill twBill = new TwBill();
        twBill.setUid(uid);
        twBill.setUsername(username);
        twBill.setNum(money);
        twBill.setDepartment(twUser.getDepatmentId());
        twBill.setPath(twUser.getPath());
        twBill.setCoinname("usdt");
        twBill.setAfternum(twUserCoinService.afternum(uid));
        twBill.setType(9);
        twBill.setAddtime(new Date());
        twBill.setSt(1);
        twBill.setRemark("杠杆平仓");
        twBillService.save(twBill);

        TwNotice twNotice = new TwNotice();
        twNotice.setUid(uid);
        twNotice.setPath(twUser.getPath());
        twNotice.setDepartment(twUser.getDepatmentId());
        twNotice.setAccount(username);
        twNotice.setTitle("杠杆交易");
        twNotice.setTitleEn("Leverage trading");
        twNotice.setContent("杠杆已平仓，请注意查收");
        twNotice.setContentEn("The leverage position has been closed, please check it carefully");
        twNotice.setAddtime(new Date());
        twNotice.setStatus(1);
        twNoticeService.save(twNotice);
    }

}




