package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.models.auth.In;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.common.common.util.CommonUtil;
import net.lab1024.sa.common.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class TimerServiceImpl {

    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private TwBillService twBillService;

    @Autowired
    private TwNoticeService twNoticeService;

    @Autowired
    private TwKjorderService twKjorderService;

    @Autowired
    private TwKjprofitService twKjprofitService;

    @Autowired
    private TwHyorderService twHyorderService;

    @Autowired
    private TwHysettingService twHysettingService;

    /**
     * 工具方法：获取行情数据 （api调用）
     * 方法名：getnewprice
     * 参数：url（api的）
     *参考代码：
     *          //获取行情数据
     *     public function getnewprice($api)
     *     {
     *         $result = httpGet(url);(请求 并得到返回结果)
     *         $price_arr = $result['data'][0];
     *         $close = $price_arr['close'];//现价
     *         return $close;
     *     }
     *
     * */

    public BigDecimal getnewprice(String url){
        Map<String, Object> map = CommonUtil.doGet(url, null);
        JSONObject res = JSONObject.parseObject(map.get("res").toString());
        JSONArray data = JSONArray.parseArray(res.get("data").toString());
        JSONObject jsonObject = JSONObject.parseObject(data.get(0).toString());

        BigDecimal close = new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP);
        return close;
    }

    /**
     * 工具方法：写财务日志
     * 表：user_coin，bill，notice
     * 参数：uid (用户id)  username （用户名） money （钱）
     * 大致逻辑：
     *         1.查询 user_coin where userid=?
     *         2.拼接参数写入  bill表
     *         3.拼接参数写入  notice表
     * 参考代码：
     *     //写财务日志
     *     public function addlog($uid, $username, $money)
     *     {
     *         $minfo = M("user_coin")->where(array('userid' => $uid))->find();
     *         $data['uid'] = $uid;
     *         $data['username'] = $username;
     *         $data['num'] = $money;
     *         $data['coinname'] = "usdt";
     *         $data['afternum'] = $minfo['usdt'] + $money;
     *         $data['type'] = 4;
     *         $data['addtime'] = date("Y-m-d H:i:s", time());
     *         $data['st'] = 1;
     *         $data['remark'] = L('合约出售');
     *         M("bill")->add($data);
     *         $notice['uid'] = $uid;
     *         $notice['account'] = $username;
     *         $notice['title'] = L('秒合约交易');
     *         $notice['content'] = L('秒合约已平仓，请及时加仓');
     *         $notice['addtime'] = date("Y-m-d H:i:s", time());
     *         $notice['status'] = 1;
     *         M("notice")->add($notice);
     *     }
     * */

    public void addlog(int uid, String username,BigDecimal money){
        QueryWrapper<TwUserCoin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",uid);
        TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper);

        //创建财务日志
        TwBill twBill = new TwBill();
        twBill.setUid(uid);
        twBill.setUsername(twUserCoin.getUsername());
        twBill.setNum(money);
        twBill.setCoinname("usdt");
        twBill.setAfternum(twUserCoinService.afternum(uid,"usdt"));
        twBill.setType(4);
        twBill.setAddtime(new Date());
        twBill.setSt(1);
        twBill.setRemark("合约出售");
        twBillService.save(twBill);

        TwNotice twNotice = new TwNotice();
        twNotice.setUid(uid);
        twNotice.setAccount(twUserCoin.getUsername());
        twNotice.setTitle("秒合约交易");
        twNotice.setContent("秒合约已平仓，请及时加仓");
        twNotice.setAddtime(new Date());
        twNotice.setStatus(1);
        twNoticeService.save(twNotice);
    }


    public void autokjsy(){
        QueryWrapper<TwKjorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        queryWrapper.eq("type",1);
        List<TwKjorder> list = twKjorderService.list(queryWrapper);
        for(TwKjorder twKjorder:list){
            String coinname="";
            BigDecimal tcoinnum = new BigDecimal(0);
            Integer id = twKjorder.getId();
            Integer uid = twKjorder.getUid();
            String username = twKjorder.getUsername();
            Integer outtype = twKjorder.getOuttype();
            String outcoin = twKjorder.getOutcoin();
            BigDecimal outnum = twKjorder.getOutnum();

            QueryWrapper<TwUserCoin> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("userid",uid);
            TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper1);

            String nowdate = DateUtil.date2Str(new Date(), "yyyy-MM-dd");
            QueryWrapper<TwKjprofit> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("uid",uid);
            queryWrapper2.eq("kid",id);
            queryWrapper2.eq("day",nowdate);
            TwKjprofit twKjprofit = twKjprofitService.getOne(queryWrapper2);
            if(twKjprofit == null){
                if(outtype == 2){
                    coinname = outcoin;
                    tcoinnum = outnum;
                }
                TwKjprofit twKjprofit1 = new TwKjprofit();
                twKjprofit1.setUid(uid);
                twKjprofit1.setUsername(username);
                twKjprofit1.setKid(id);
                twKjprofit1.setKtitle(twKjorder.getKjtitle());
                twKjprofit1.setNum(tcoinnum);
                twKjprofit1.setCoin(coinname);
                twKjprofit1.setAddtime(new Date());
                twKjprofit1.setDay(DateUtil.str2DateDay(nowdate));
                twKjprofitService.save(twKjprofit1);

                twUserCoinService.incre(uid,tcoinnum,"usdt");

                //写资金日志
                TwBill twBill = new TwBill();
                twBill.setUid(uid);
                twBill.setUsername(username);
                twBill.setNum(tcoinnum);
                twBill.setCoinname("usdt");
                twBill.setAfternum(twUserCoinService.afternum(uid,"usdt"));
                twBill.setType(8);
                twBill.setAddtime(new Date());
                twBill.setSt(1);
                twBill.setRemark("矿机收益释放");
                twBillService.save(twBill);

                Integer synum = twKjorder.getSynum();
                twKjorder.setSynum(synum + 1);

                twKjorderService.updateById(twKjorder);

            }
        }
    }

    public  void hycarryout(){
        long nowtime = System.currentTimeMillis()/1000;
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        queryWrapper.le("intselltime", nowtime);
        List<TwHyorder> list = twHyorderService.list(queryWrapper);

        QueryWrapper<TwHysetting> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id",1);
        TwHysetting twHysetting = twHysettingService.getOne(queryWrapper1);
        String hyYlid = twHysetting.getHyYlid();
        String hyKsid = twHysetting.getHyKsid();
        String[] winarr = hyYlid.split("\\|");
        String[] lossarr = hyKsid.split("\\|");

        for (TwHyorder twHyorder:list){
            String coinname = twHyorder.getCoinname();
            String symbol = coinname.toLowerCase().replace("/", "");
            String url = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol="+symbol;
            BigDecimal newprice = getnewprice(url);
            // 创建 Random 对象
            Random random = new Random();
            // 生成在[0.1, 0.9999]范围内的随机数
            BigDecimal randnum = new BigDecimal(Double.toString(0.1 + (0.9999 - 0.1) * random.nextDouble()));

            BigDecimal buyprice = twHyorder.getBuyprice();
            Integer hyzd = twHyorder.getHyzd();  //合约方向
            Integer kongyk = twHyorder.getKongyk(); //单控设置
            String uid = twHyorder.getUid().toString();
            Integer uuid = twHyorder.getUid();
            String username = twHyorder.getUsername();
            BigDecimal num = twHyorder.getNum();
            BigDecimal hybl = twHyorder.getHybl();
            MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
            BigDecimal ylnum = num.multiply(hybl.divide(new BigDecimal(100), mathContext));
            BigDecimal money = num.add(ylnum);  //盈利金额
            BigDecimal sellprice = twHyorder.getSellprice();
            //买涨
            if(hyzd == 1){
                boolean isWinArray = false;
                boolean isLoseArray = false;
                for (String win : winarr) {
                    if (win.equals(uid)) {
                        isWinArray = true;
                        break; // 如果找到匹配，可以提前退出循环
                    }
                }
                for (String win : lossarr) {
                    if (win.equals(uid)) {
                        isLoseArray = true;
                        break; // 如果找到匹配，可以提前退出循环
                    }
                }

                if (isWinArray) {
                    //如果有指定盈利ID，则按盈利结算
                    if (buyprice.compareTo(newprice) < 0) {
                             sellprice = newprice;
                         } else if (newprice.compareTo(buyprice) == 0) {
                             sellprice = newprice.add(randnum);
                         } else if (newprice.compareTo(buyprice) < 0) {
                             sellprice = buyprice.add(randnum);
                         }
                    //增加资产
                    twUserCoinService.incre(uuid,money,"usdt");

                    //修改订单状态
                    twHyorder.setStatus(2);
                    twHyorder.setIsWin(1);
                    twHyorder.setSellprice(sellprice);
                    twHyorder.setPloss(ylnum);
                    twHyorderService.updateById(twHyorder);

                    //写财务日志
                    addlog(uuid,username,money);
                }

                if (isLoseArray) {
                    //买涨,指定亏损,结算价格要低于买入价格
                    if (buyprice.compareTo(newprice) < 0) {
                              sellprice = buyprice.subtract(randnum);
                    } else if (newprice.compareTo(buyprice) == 0) {
                              sellprice = buyprice.subtract(randnum);
                    } else if (newprice.compareTo(buyprice) < 0) {
                              sellprice = newprice;
                    }

                    //修改订单状态
                    twHyorder.setStatus(2);
                    twHyorder.setIsWin(2);
                    twHyorder.setSellprice(sellprice);
                    twHyorder.setPloss(ylnum);
                    twHyorderService.updateById(twHyorder);

                    //减少资产
                    twUserCoinService.decre(uuid,money,"usdt");

                    //写财务日志
                    addlog(uuid,username,money);
                }

                if(!isWinArray || !isLoseArray){
                    //如果未指定盈利和亏损，则按单控的计算

                    if(kongyk == 1){  //盈利
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = newprice;
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = newprice.add(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = buyprice.add(randnum);
                        }
                        //减少资产
                        twUserCoinService.incre(uuid,money,"usdt");

                        //修改订单状态
                        twHyorder.setStatus(2);
                        twHyorder.setIsWin(1);
                        twHyorder.setSellprice(sellprice);
                        twHyorder.setPloss(ylnum);
                        twHyorderService.updateById(twHyorder);

                        //写财务日志
                        addlog(uuid,username,money);

                    }

                    if(kongyk == 2){ //亏损
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = newprice;
                        }
                        //修改订单状态
                        twHyorder.setStatus(2);
                        twHyorder.setIsWin(2);
                        twHyorder.setSellprice(sellprice);
                        twHyorder.setPloss(ylnum);
                        twHyorderService.updateById(twHyorder);

                        //减少资产
                        twUserCoinService.decre(uuid,money,"usdt");

                        //写财务日志
                        addlog(uuid,username,money);
                    }
                }

            }
            //买跌
            if(hyzd == 2){
                boolean isWinArray = false;
                boolean isLoseArray = false;
                for (String win : winarr) {
                    if (win.equals(uid)) {
                        isWinArray = true;
                        break; // 如果找到匹配，可以提前退出循环
                    }
                }
                for (String win : lossarr) {
                    if (win.equals(uid)) {
                        isLoseArray = true;
                        break; // 如果找到匹配，可以提前退出循环
                    }
                }
                if (isWinArray) { //如果有指定盈利ID，则按盈利结算
                    if (buyprice.compareTo(newprice) < 0) {
                        sellprice = buyprice.subtract(randnum);
                    } else if (newprice.compareTo(buyprice) == 0) {
                        sellprice = buyprice.add(randnum);
                    } else if (newprice.compareTo(buyprice) < 0) {
                        sellprice = newprice;
                    }
                    //增加资产
                    twUserCoinService.incre(uuid,money,"usdt");

                    //修改订单状态
                    twHyorder.setStatus(2);
                    twHyorder.setIsWin(1);
                    twHyorder.setSellprice(sellprice);
                    twHyorder.setPloss(ylnum);
                    twHyorderService.updateById(twHyorder);

                    //写财务日志
                    addlog(uuid,username,money);
                }

                if (isLoseArray) { //如果有指定亏损ID，则按亏损结算
                    if (buyprice.compareTo(newprice) < 0) {
                        sellprice = newprice;
                    } else if (newprice.compareTo(buyprice) == 0) {
                        sellprice = buyprice.add(randnum);
                    } else if (newprice.compareTo(buyprice) < 0) {
                        sellprice = buyprice.add(randnum);
                    }

                    //修改订单状态
                    twHyorder.setStatus(2);
                    twHyorder.setIsWin(2);
                    twHyorder.setSellprice(sellprice);
                    twHyorder.setPloss(ylnum);
                    twHyorderService.updateById(twHyorder);

                    //减少资产
                    twUserCoinService.decre(uuid,money,"usdt");

                    //写财务日志
                    addlog(uuid,username,money);
                }

                if(!isWinArray || !isLoseArray){ //如果未指定盈利和亏损，则按单控的计算
                    if(kongyk == 1){ //盈利
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = newprice;
                        }

                        //增加资产
                        twUserCoinService.incre(uuid,money,"usdt");

                        //修改订单状态
                        twHyorder.setStatus(2);
                        twHyorder.setIsWin(1);
                        twHyorder.setSellprice(sellprice);
                        twHyorder.setPloss(ylnum);
                        twHyorderService.updateById(twHyorder);

                        //写财务日志
                        addlog(uuid,username,money);
                    }

                    if(kongyk == 2){ //亏损
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = newprice;
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = buyprice.add(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = buyprice.add(randnum);
                        }

                        //修改订单状态
                        twHyorder.setStatus(2);
                        twHyorder.setIsWin(2);
                        twHyorder.setSellprice(sellprice);
                        twHyorder.setPloss(ylnum);
                        twHyorderService.updateById(twHyorder);

                        //减少资产
                        twUserCoinService.decre(uuid,money,"usdt");

                        //写财务日志
                        addlog(uuid,username,money);
                    }
                }
            }
        }
    }
}
