package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.models.auth.In;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKjorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKjprofitDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.common.common.util.CommonUtil;
import net.lab1024.sa.common.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
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

    @Autowired
    private TwUserService twUserService;

    @Autowired
    private TwKjorderDao twKjorderDao;

    @Autowired
    private TwKjprofitDao twKjprofitDao;

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
        Map<String, Object> map = CommonUtil.executeGet(url);
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
        twBill.setType(4);
        twBill.setAddtime(new Date());
        twBill.setSt(1);
        twBill.setRemark("合约出售");
        twBillService.save(twBill);

        TwNotice twNotice = new TwNotice();
        twNotice.setUid(uid);
        twNotice.setPath(twUser.getPath());
        twNotice.setDepartment(twUser.getDepatmentId());
        twNotice.setAccount(username);
        twNotice.setTitle("秒合约交易");
        twNotice.setTitleEn("second contract trading");
        twNotice.setContent("秒合约已平仓，请及时加仓");
        twNotice.setContentEn("The second contract has been closed, please add your position in time");
        twNotice.setAddtime(new Date());
        twNotice.setStatus(1);
        twNoticeService.save(twNotice);
    }


    public void autokjsy(){
        QueryWrapper<TwKjorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        List<TwKjorder> list = twKjorderService.list(queryWrapper);
        for(TwKjorder twKjorder:list){
            String coinname="";
            Integer kid = twKjorder.getKid();
            Integer uid = twKjorder.getUid();
            String username = twKjorder.getUsername();
            BigDecimal outnum = twKjorder.getOutnum();
            QueryWrapper<TwUserCoin> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("userid",uid);
            TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper1);

            QueryWrapper<TwUser> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.eq("id",uid);
            TwUser twUser = twUserService.getOne(queryWrapper4);

            String nowdate = DateUtil.date2Str(new Date(), "yyyy-MM-dd");
            QueryWrapper<TwKjprofit> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("uid",uid);
            queryWrapper2.eq("kid",kid);
            queryWrapper2.eq("day",nowdate);
            TwKjprofit twKjprofit = twKjprofitService.getOne(queryWrapper2);
            if(twKjprofit == null){
                TwKjprofit twKjprofit1 = new TwKjprofit();
                twKjprofit1.setUid(uid);
                twKjprofit1.setUsername(username);
                twKjprofit1.setPath(twUser.getPath());
                twKjprofit1.setDepartment(twUser.getDepatmentId());
                twKjprofit1.setKid(twKjorder.getKid());
                twKjprofit1.setKtitle(twKjorder.getKjtitle());
                twKjprofit1.setNum(outnum);
                twKjprofit1.setCoin(coinname);
                twKjprofit1.setAddtime(new Date());
                twKjprofit1.setDay(DateUtil.str2DateDay(nowdate));
                twKjprofitService.save(twKjprofit1);

//                twUserCoinService.incre(uid,outnum,twUserCoin.getUsdt());

                //写资金日志
                TwBill twBill = new TwBill();
                twBill.setUid(uid);
                twBill.setUsername(username);
                twBill.setPath(twUser.getPath());
                twBill.setDepartment(twUser.getDepatmentId());
                twBill.setNum(outnum);
                twBill.setCoinname("usdt");
                twBill.setAfternum(twUserCoinService.afternum(uid));
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

    public void endkjsy(){
        Integer nowtime = (int) (System.currentTimeMillis()/1000);
        QueryWrapper<TwKjorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        queryWrapper.le("intendtime", nowtime);
        List<TwKjorder> list = twKjorderService.list(queryWrapper);
        if(list.size() > 0){
            for(TwKjorder twKjorder:list){

                QueryWrapper<TwUser> queryWrapper4 = new QueryWrapper<>();
                queryWrapper4.eq("id",twKjorder.getUid());
                TwUser twUser = twUserService.getOne(queryWrapper4);

                QueryWrapper<TwUserCoin> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("userid",twKjorder.getUid());
                TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper3);

                Integer uid = twKjorder.getUid();
                BigDecimal buynum1 = twKjorder.getBuynum();
                BigDecimal buynum = new BigDecimal(0);
                QueryWrapper<TwKjprofit> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.select("IFNULL(SUM(num), 0) as num")
                        .eq("kid", twKjorder.getKid())
                        .eq("uid", twKjorder.getUid());
                List<Map<String, Object>> result = twKjprofitDao.selectMaps(queryWrapper1);
                if (result.isEmpty()) {
                    buynum  = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
                }

                Object totalNumObject = result.get(0).get("num");
                if (totalNumObject instanceof BigDecimal) {
                    buynum = ((BigDecimal) totalNumObject).setScale(2, RoundingMode.HALF_UP);
                } else if (totalNumObject instanceof Long) {
                    buynum = BigDecimal.valueOf((Long) totalNumObject).setScale(2, RoundingMode.HALF_UP);
                } else if (totalNumObject instanceof Integer) {
                    buynum = BigDecimal.valueOf((Integer) totalNumObject).setScale(2, RoundingMode.HALF_UP);
                } else {
                    // 处理其他可能的类型
                    buynum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
                }
                BigDecimal add = buynum.add(buynum1);
                //增加资产
                twUserCoinService.incre(uid,add,twUserCoin.getUsdt());

                //写资金日志
                TwBill twBill = new TwBill();
                twBill.setUid(uid);
                twBill.setUsername(twKjorder.getUsername());
                twBill.setPath(twUser.getPath());
                twBill.setDepartment(twUser.getDepatmentId());
                twBill.setNum(buynum);
                twBill.setCoinname("usdt");
                twBill.setAfternum(twUserCoinService.afternum(uid));
                twBill.setType(8);
                twBill.setAddtime(new Date());
                twBill.setSt(1);
                twBill.setRemark("矿机到期收益释放");
                twBillService.save(twBill);


                twKjorder.setStatus(3);
                twKjorderService.updateById(twKjorder);
            }
        }
    }

    public  void hycarryout() {
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

            QueryWrapper<TwUserCoin> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("userid",uid);
            TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper3);

            //买涨
            if(hyzd == 1){
                if(kongyk != 0){   //已控
                    if(kongyk == 1){  //盈利
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = newprice;
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = newprice.add(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = buyprice.add(randnum);
                        }
                        //增加资产
                        twUserCoinService.incre(uuid,money,twUserCoin.getUsdt());

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
                        twHyorder.setPloss(num);
                        twHyorderService.updateById(twHyorder);


                        //写财务日志
                        addlog(uuid,username,num);
                    }
                }

                if(kongyk == 0){   //未控
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
                        twUserCoinService.incre(uuid,money,twUserCoin.getUsdt());

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
                        twHyorder.setPloss(num);
                        twHyorderService.updateById(twHyorder);


                        //写财务日志
                        addlog(uuid,username,num);
                    }

                    if(!isWinArray && !isLoseArray){
                        //如果未指定盈利和亏损，则按单控的计算

                        if(kongyk == 1){  //盈利
                            if (buyprice.compareTo(newprice) < 0) {
                                sellprice = newprice;
                            } else if (newprice.compareTo(buyprice) == 0) {
                                sellprice = newprice.add(randnum);
                            } else if (newprice.compareTo(buyprice) < 0) {
                                sellprice = buyprice.add(randnum);
                            }
                            //增加资产
                            twUserCoinService.incre(uuid,money,twUserCoin.getUsdt());

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
                            twHyorder.setPloss(num);
                            twHyorderService.updateById(twHyorder);


                            //写财务日志
                            addlog(uuid,username,num);
                        }

                        if(kongyk == 0){
                            if (buyprice.compareTo(newprice) < 0) {   //盈利
                                twUserCoinService.incre(uuid,money,twUserCoin.getUsdt());
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(1);
                                //写财务日志
                                addlog(uuid,username,money);

                                twHyorder.setSellprice(newprice);
                                twHyorder.setPloss(ylnum);
                                twHyorderService.updateById(twHyorder);


                            } else if (newprice.compareTo(buyprice) == 0) {
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(2);
                                //写财务日志
                                addlog(uuid,username,num);

                                twHyorder.setSellprice(newprice);
                                twHyorder.setPloss(num);
                                twHyorderService.updateById(twHyorder);

                            } else if (newprice.compareTo(buyprice) < 0) {   //亏损
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(2);
                                //写财务日志
                                addlog(uuid,username,num);

                                twHyorder.setSellprice(newprice);
                                twHyorder.setPloss(num);
                                twHyorderService.updateById(twHyorder);
                            }

                        }
                    }
                }

            }
            //买跌
            if(hyzd == 2){

                if(kongyk != 0 ){
                    if(kongyk == 1){ //盈利
                        if (buyprice.compareTo(newprice) < 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) == 0) {
                            sellprice = buyprice.subtract(randnum);
                        } else if (newprice.compareTo(buyprice) < 0) {
                            sellprice = newprice;
                        }

                        //增加资产
                        twUserCoinService.incre(uuid,money,twUserCoin.getUsdt());

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
                        twHyorder.setPloss(num);
                        twHyorderService.updateById(twHyorder);


                        //写财务日志
                        addlog(uuid,username,num);
                        log.info("合约指定亏损成功======================================");
                    }
                }

                if(kongyk == 0 ){
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
                        twUserCoinService.incre(uuid,money,twUserCoin.getUsdt());

                        //修改订单状态
                        twHyorder.setStatus(2);
                        twHyorder.setIsWin(1);
                        twHyorder.setSellprice(sellprice);
                        twHyorder.setPloss(ylnum);
                        twHyorderService.updateById(twHyorder);

                        //写财务日志
                        addlog(uuid,username,money);
                        System.out.println("指定盈利ID======================================");
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
                        twHyorder.setPloss(num);
                        twHyorderService.updateById(twHyorder);


                        //写财务日志
                        addlog(uuid,username,num);
                        System.out.println("合约指定亏损成功======================================");
                    }

                    if(!isWinArray && !isLoseArray){ //如果未指定盈利和亏损，则按单控的计算
                        System.out.println("未指定盈利和亏损，则按单控的计算======================================");
                        if(kongyk == 1){ //盈利
                            if (buyprice.compareTo(newprice) < 0) {
                                sellprice = buyprice.subtract(randnum);
                            } else if (newprice.compareTo(buyprice) == 0) {
                                sellprice = buyprice.subtract(randnum);
                            } else if (newprice.compareTo(buyprice) < 0) {
                                sellprice = newprice;
                            }

                            //增加资产
                            twUserCoinService.incre(uuid,money,twUserCoin.getUsdt());

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
                            twHyorder.setPloss(num);
                            twHyorderService.updateById(twHyorder);


                            //写财务日志
                            addlog(uuid,username,num);
                            log.info("合约指定亏损成功======================================");
                        }


                        if(kongyk == 0){
                            if (buyprice.compareTo(newprice) < 0) {   //亏损
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(2);
                                //写财务日志
                                addlog(uuid,username,num);

                                twHyorder.setSellprice(newprice);
                                twHyorder.setPloss(num);
                                twHyorderService.updateById(twHyorder);

                            } else if (newprice.compareTo(buyprice) == 0) {
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(2);
                                //写财务日志
                                addlog(uuid,username,num);

                                twHyorder.setSellprice(newprice);
                                twHyorder.setPloss(num);
                                twHyorderService.updateById(twHyorder);

                            } else if (newprice.compareTo(buyprice) < 0) {   //盈利
                                twUserCoinService.incre(uuid,money,twUserCoin.getUsdt());
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(1);
                                //写财务日志
                                addlog(uuid,username,money);

                                twHyorder.setSellprice(newprice);
                                twHyorder.setPloss(ylnum);
                                twHyorderService.updateById(twHyorder);
                            }
                        }
                    }
                }
            }
        }
    }
}
