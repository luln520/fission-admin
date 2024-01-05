package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwFooterDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwHyorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwHyorderVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.HyorderOneRes;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.CommonUtil;
import net.lab1024.sa.common.common.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
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
public class TwHyorderServiceImpl extends ServiceImpl<TwHyorderDao, TwHyorder> implements TwHyorderService {

    @Autowired
    private TwUserService twUserService;

    @Autowired
    private TwHysettingService twHysettingService;

    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private TwBillService twBillService;

    @Override
    public int countUnClosedOrders() {
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "1"); // 添加查询条件
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public TwHyorder hyorderId(int id) {
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id); // 添加查询条件
        return this.getOne(queryWrapper);
    }

    @Override
    public ResponseDTO getHyorderOne(int id) {

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
        Integer time = twHyorder.getTime() * 60;
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
                Map<String, Object> map = CommonUtil.doGet(str, null);
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
        queryWrapper.eq("status", 1); // 添加查询条件
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
    public IPage<TwHyorder> listpage(TwHyorderVo twHyorderVo) {
        Page<TwHyorder> objectPage = new Page<>(twHyorderVo.getPageNum(), twHyorderVo.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, twHyorderVo));
        return objectPage;
    }

    @Override
    public boolean editKongyK(Integer kongyk, int id) {
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id); // 添加查询条件
        TwHyorder one = this.getOne(queryWrapper);
        one.setKongyk(kongyk);
        return this.updateById(one);
    }

    @Override
    public ResponseDTO creatorder(int uid, int ctime, BigDecimal ctzed, String ccoinname, int ctzfx, BigDecimal cykbl) {
        try{
            QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", uid); // 添加查询条件
            TwUser twUser = twUserService.getOne(queryWrapper);

            QueryWrapper<TwUser> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.eq("invit1", twUser.getInvit1()); // 添加查询条件
            TwUser puser = twUserService.getOne(queryWrapper4);

            //获取会员资产
            QueryWrapper<TwUserCoin> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("userid", uid); // 添加查询条件
            TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper1);

            //获取合约手续费比例
            QueryWrapper<TwHysetting> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("userid", uid); // 添加查询条件
            TwHysetting twHysetting = twHysettingService.getOne(queryWrapper2);

            if(twUser.getRzstatus() != 2){
                ResponseDTO.userErrorParam("请先完成实名认证");
            }

            if(twUser.getBuyOn() == 2){
                ResponseDTO.userErrorParam("您的账户已被禁止交易，请联系客服");
            }

//            if(ctzed.compareTo(twHysetting.getHyMin()) < 0){
//                ResponseDTO.userErrorParam("不能小于最低投资额度");
//            }

            BigDecimal hySxf = twHysetting.getHySxf();
            MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
            BigDecimal divide = ctzed.multiply(hySxf).divide(new BigDecimal(100), mathContext);
            BigDecimal tmoney = ctzed.add(divide);
            if(twUserCoin.getUsdt().compareTo(tmoney) < 0){
                ResponseDTO.userErrorParam("USDT余额不足");
            }

            String symbol = ccoinname.toLowerCase().replace("/", "");
            String str = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol="+symbol;
            Map<String, Object> map = CommonUtil.doGet(str, null);
            JSONObject res = JSONObject.parseObject(map.get("res").toString());
            JSONArray data = JSONArray.parseArray(res.get("data").toString());
            JSONObject jsonObject = JSONObject.parseObject(data.get(0).toString());

            BigDecimal close = new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP);

            TwHyorder twHyorder = new TwHyorder();
            twHyorder.setUid(uid);
            twHyorder.setUsername(twUser.getUsername());
            twHyorder.setNum(ctzed);
            twHyorder.setHybl(cykbl);
            twHyorder.setHyzd(ctzfx);
            twHyorder.setBuyOrblance(twUserCoin.getUsdt().subtract(tmoney));
            twHyorder.setCoinname(ccoinname);
            twHyorder.setStatus(1);
            twHyorder.setIsWin(0);
            twHyorder.setBuytime(new Date());
            Date selltime = DateUtil.secondsDateAddSeconds(new Date(), ctime * 60);
            twHyorder.setSelltime(selltime);
            twHyorder.setIntselltime((int) selltime.getTime()/1000);
            twHyorder.setBuyprice(close);
            twHyorder.setSellprice(new BigDecimal(0));
            twHyorder.setPloss(new BigDecimal(0));
            twHyorder.setTime(ctime);
            twHyorder.setKongyk(0);
            if(StringUtils.isEmpty(puser.getInvit())){
                twHyorder.setInvit("");
            }else{
                twHyorder.setInvit(puser.getInvit());
            }
            this.save(twHyorder);
            //扣除USDT额度
            twUserCoinService.decre(uid,tmoney,twUserCoin.getUsdt());

            //创建财务日志
            TwBill twBill = new TwBill();
            twBill.setUid(uid);
            twBill.setUsername(twUser.getUsername());
            twBill.setNum(ctzed);
            twBill.setCoinname("usdt");
            twBill.setAfternum(twUserCoinService.afternum(uid));
            twBill.setType(3);
            twBill.setAddtime(new Date());
            twBill.setSt(2);
            twBill.setRemark("购买"+ ccoinname + "秒合约");
            twBillService.save(twBill);

            return ResponseDTO.ok("建仓成功");
        }catch (Exception e){
            return ResponseDTO.userErrorParam("建仓失败");
        }
    }
}
