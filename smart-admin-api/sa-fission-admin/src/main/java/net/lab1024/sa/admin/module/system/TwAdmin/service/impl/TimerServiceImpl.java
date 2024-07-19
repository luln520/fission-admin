package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import cn.hutool.core.date.DateUtil;
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
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.*;
import java.util.*;

@Service
@Slf4j
public class TimerServiceImpl {

    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private TwMockUserCoinService twMockUserCoinService;

    @Autowired
    private TwBillService twBillService;

    @Autowired
    private TwNoticeService twNoticeService;

    @Autowired
    private TwCompanyService twCompanyService;


    @Autowired
    private TwRechargeService twRechargeService;

    @Autowired
    private  TwMyzcService twMyzcService;

    @Autowired
    private TwReportService twReportService;

//    @Autowired
//    private TwKjorderService twKjorderService;
//
//    @Autowired
//    private TwKjprofitService twKjprofitService;

    @Autowired
    private TwHyorderService twHyorderService;

    @Autowired
    private TwHysettingService twHysettingService;

    @Autowired
    private TwUserService twUserService;
//
//    @Autowired
//    private TwKuangjiService twKuangjiService;

//
//    @Autowired
//    private TwKjprofitDao twKjprofitDao;
//
//    @Autowired
//    private TwUserKuangjiService twUserKuangjiService;
//
//    @Autowired
//    private TwCurrencyService twCurrencyService;
//
//    @Autowired
//    private TwCurrenyListService twCurrenyListService;

    private static final Map<String, String> map = new HashMap<>();

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

    public void addlog(int uid, String username,BigDecimal money,int companyId){
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",uid);
        TwUser twUser = twUserService.getOne(queryWrapper);


        //创建财务日志
        TwBill twBill = new TwBill();
        twBill.setUid(uid);
        twBill.setUsername(username);
        twBill.setUserCode(twUser.getUserCode());
        twBill.setNum(money);
        twBill.setCompanyId(companyId);
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
        twNotice.setCompanyId(twUser.getCompanyId());
        twNotice.setTitle("秒合约交易");
        twNotice.setTitleEn("second contract trading");
        twNotice.setContent("秒合约已平仓，请注意查收");
        twNotice.setContentEn("The second contract has been closed, please check it carefully");
        twNotice.setAddtime(new Date());
        twNotice.setStatus(1);
        twNoticeService.save(twNotice);
    }


//    public void autokjsy(){
//        QueryWrapper<TwKjorder> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("status",1);
//        List<TwKjorder> list = twKjorderService.list(queryWrapper);
//        for(TwKjorder twKjorder:list){
//            try{
//                String coinname="";
//                Integer kid = twKjorder.getKid();
//                Integer uid = twKjorder.getUid();
//                String username = twKjorder.getUsername();
//                BigDecimal outnum = twKjorder.getOutnum();
//
//                QueryWrapper<TwUser> queryWrapper4 = new QueryWrapper<>();
//                queryWrapper4.eq("id",uid);
//                TwUser twUser = twUserService.getOne(queryWrapper4);
//
//                String nowdate = DateUtil.date2Str(new Date(), "yyyy-MM-dd");
//                QueryWrapper<TwKjprofit> queryWrapper2 = new QueryWrapper<>();
//                queryWrapper2.eq("uid",uid);
//                queryWrapper2.eq("kid",kid);
//                queryWrapper2.eq("day",nowdate);
//                TwKjprofit twKjprofit = twKjprofitService.getOne(queryWrapper2);
//                if(twKjprofit == null){
//                    TwKjprofit twKjprofit1 = new TwKjprofit();
//                    twKjprofit1.setUid(uid);
//                    twKjprofit1.setOrderNo(twKjorder.getOrderNo());
//                    twKjprofit1.setUsername(username);
//                    twKjprofit1.setUserCode(twKjorder.getUserCode());
//                    if(StringUtils.isNotEmpty(twUser.getPath())){
//                        twKjprofit1.setPath(twUser.getPath());
//                    }
//                    twKjprofit1.setDepartment(twUser.getDepatmentId());
//                    twKjprofit1.setKid(twKjorder.getKid());
//                    twKjprofit1.setKtitle(twKjorder.getKjtitle());
//                    twKjprofit1.setNum(outnum);
//                    twKjprofit1.setCompanyId(twUser.getCompanyId());
//                    twKjprofit1.setCoin(coinname);
//                    twKjprofit1.setAddtime(new Date());
//                    twKjprofit1.setDay(DateUtil.str2DateDay(nowdate));
//                    twKjprofitService.save(twKjprofit1);
//
//                    Integer synum = twKjorder.getSynum();
//                    twKjorder.setSynum(synum + 1);
//
//                    twKjorderService.updateById(twKjorder);
//
////                twUserCoinService.incre(uid,outnum,twUserCoin.getUsdt());
//
//                    //写资金日志
//                    TwBill twBill = new TwBill();
//                    twBill.setUid(uid);
//                    twBill.setUsername(username);
//                    twBill.setUserCode(twUser.getUserCode());
//                    twBill.setPath(twUser.getPath());
//                    twBill.setDepartment(twUser.getDepatmentId());
//                    twBill.setNum(outnum);
//                    twBill.setCoinname("usdt");
//                    twBill.setCompanyId(twUser.getCompanyId());
//                    twBill.setAfternum(twUserCoinService.afternum(uid));
//                    twBill.setType(8);
//                    twBill.setAddtime(new Date());
//                    twBill.setSt(1);
//                    twBill.setRemark("矿机收益释放");
//                    twBillService.save(twBill);
//
//                }
//            }catch (Exception e){
//                continue;
//            }
//        }
//    }
//
//    public void endkjsy(){
//        Integer nowtime = (int) (System.currentTimeMillis()/1000);
//        QueryWrapper<TwKjorder> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("status",1);
//        queryWrapper.le("intendtime", nowtime);
//        List<TwKjorder> list = twKjorderService.list(queryWrapper);
//        if(list.size() > 0){
//            for(TwKjorder twKjorder:list){
//
//                QueryWrapper<TwUser> queryWrapper4 = new QueryWrapper<>();
//                queryWrapper4.eq("id",twKjorder.getUid());
//                TwUser twUser = twUserService.getOne(queryWrapper4);
//
//                QueryWrapper<TwUserCoin> queryWrapper3 = new QueryWrapper<>();
//                queryWrapper3.eq("userid",twKjorder.getUid());
//                TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper3);
//
//                Integer uid = twKjorder.getUid();
//                BigDecimal buynum1 = twKjorder.getBuynum();
//                BigDecimal buynum = new BigDecimal(0);
//                QueryWrapper<TwKjprofit> queryWrapper1 = new QueryWrapper<>();
//                queryWrapper1.select("IFNULL(SUM(num), 0) as num")
//                        .eq("kid", twKjorder.getKid())
//                        .eq("uid", twKjorder.getUid());
//                List<Map<String, Object>> result = twKjprofitDao.selectMaps(queryWrapper1);
//                if (result.isEmpty()) {
//                    buynum  = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
//                }
//
//                Object totalNumObject = result.get(0).get("num");
//                if (totalNumObject instanceof BigDecimal) {
//                    buynum = ((BigDecimal) totalNumObject).setScale(2, RoundingMode.HALF_UP);
//                } else if (totalNumObject instanceof Long) {
//                    buynum = BigDecimal.valueOf((Long) totalNumObject).setScale(2, RoundingMode.HALF_UP);
//                } else if (totalNumObject instanceof Integer) {
//                    buynum = BigDecimal.valueOf((Integer) totalNumObject).setScale(2, RoundingMode.HALF_UP);
//                } else {
//                    // 处理其他可能的类型
//                    buynum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
//                }
//                BigDecimal add = buynum.add(buynum1);
//                //增加资产
//                twUserCoinService.incre(uid,add,twUserCoin.getUsdt());
//
//                //写资金日志
//                TwBill twBill = new TwBill();
//                twBill.setUid(uid);
//                twBill.setUsername(twKjorder.getUsername());
//                twBill.setUserCode(twUser.getUserCode());
//                twBill.setCompanyId(twUser.getCompanyId());
//                twBill.setPath(twUser.getPath());
//                twBill.setDepartment(twUser.getDepatmentId());
//                twBill.setNum(buynum);
//                twBill.setCoinname("usdt");
//                twBill.setAfternum(twUserCoinService.afternum(uid));
//                twBill.setType(8);
//                twBill.setAddtime(new Date());
//                twBill.setSt(1);
//                twBill.setRemark("矿机到期收益释放");
//                twBillService.save(twBill);
//
//
//                twKjorder.setStatus(3);
//                twKjorderService.updateById(twKjorder);
//            }
//        }
//    }

    public  void hycarryout() {
        Instant now = Instant.now();

        // 将当前时间戳减去12个小时
        Instant twelveHoursAgo = now.minusSeconds(12 * 60 * 60);
        int nowtime = (int) twelveHoursAgo.getEpochSecond();
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        queryWrapper.eq("order_type",1);
        queryWrapper.le("intselltime", nowtime);
        List<TwHyorder> list = twHyorderService.list(queryWrapper);

        for (TwHyorder twHyorder:list){
            Integer companyId = twHyorder.getCompanyId();
            QueryWrapper<TwHysetting> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("company_id",twHyorder.getCompanyId());
            TwHysetting twHysetting = twHysettingService.getOne(queryWrapper1);
            String hyYlid = twHysetting.getHyYlid();
            String hyKsid = twHysetting.getHyKsid();
            String[] winarr = hyYlid.split("\\|");
            String[] lossarr = hyKsid.split("\\|");

            String mapOrder = map.get(twHyorder.getOrderNo());
            if(StringUtils.isEmpty(mapOrder)) {
                map.put(twHyorder.getOrderNo(), twHyorder.getOrderNo());
                String coinname = twHyorder.getCoinname();
                String symbol = coinname.toLowerCase().replace("/", "");
                String url = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=" + symbol;
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
                queryWrapper3.eq("userid", uid);
                TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper3);

                //买涨
                if (hyzd == 1) {
                    if (kongyk != 0) {   //已控
                        if (kongyk == 1) {  //盈利
                            if (buyprice.compareTo(newprice) < 0) {
                                sellprice = newprice;
                            } else if (newprice.compareTo(buyprice) == 0) {
                                sellprice = newprice.add(randnum);
                            } else if (newprice.compareTo(buyprice) < 0) {
                                sellprice = buyprice.add(randnum);
                            }
                            //增加资产
                            twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());

                            //修改订单状态
                            twHyorder.setStatus(2);
                            twHyorder.setIsWin(1);
                            twHyorder.setSellprice(sellprice);
                            twHyorder.setPloss(ylnum);
                            twHyorderService.updateById(twHyorder);

                            //写财务日志
                            addlog(uuid, username, money,companyId);

                            log.info("买涨已控盈利1=================================");
                        }

                        if (kongyk == 2) { //亏损
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
                            addlog(uuid, username, num,companyId);

                            log.info("买涨已控亏损2=================================");
                        }
                    }

                    if (kongyk == 0) {   //未控
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
                            twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());

                            //修改订单状态
                            twHyorder.setStatus(2);
                            twHyorder.setIsWin(1);
                            twHyorder.setSellprice(sellprice);
                            twHyorder.setPloss(ylnum);
                            twHyorderService.updateById(twHyorder);

                            //写财务日志
                            addlog(uuid, username, money,companyId);

                            log.info("买涨指定盈利3=================================");
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
                            addlog(uuid, username, num,companyId);

                            log.info("买涨指定亏损4=================================");
                        }

                        if (!isWinArray && !isLoseArray) {
                            //如果未指定盈利和亏损，则按单控的计算

                            if (kongyk == 1) {  //盈利
                                if (buyprice.compareTo(newprice) < 0) {
                                    sellprice = newprice;
                                } else if (newprice.compareTo(buyprice) == 0) {
                                    sellprice = newprice.add(randnum);
                                } else if (newprice.compareTo(buyprice) < 0) {
                                    sellprice = buyprice.add(randnum);
                                }
                                //增加资产
                                twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());

                                //修改订单状态
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(1);
                                twHyorder.setSellprice(sellprice);
                                twHyorder.setPloss(ylnum);
                                twHyorderService.updateById(twHyorder);

                                //写财务日志
                                addlog(uuid, username, money,companyId);

                                log.info("买涨指定盈利5=================================");

                            }

                            if (kongyk == 2) { //亏损
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
                                addlog(uuid, username, num,companyId);

                                log.info("买涨指定亏损6=================================");
                            }

                            if (kongyk == 0) {
                                if (buyprice.compareTo(newprice) < 0) {   //盈利
                                    twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());
                                    twHyorder.setStatus(2);
                                    twHyorder.setIsWin(1);
                                    //写财务日志
                                    addlog(uuid, username, money,companyId);

                                    twHyorder.setSellprice(newprice);
                                    twHyorder.setPloss(ylnum);
                                    twHyorderService.updateById(twHyorder);
                                    log.info("买涨指定盈利7=================================");

                                } else if (newprice.compareTo(buyprice) == 0) {
                                    twHyorder.setStatus(2);
                                    twHyorder.setIsWin(2);
                                    //写财务日志
                                    addlog(uuid, username, num,companyId);

                                    twHyorder.setSellprice(newprice);
                                    twHyorder.setPloss(num);
                                    twHyorderService.updateById(twHyorder);
                                    log.info("买涨指定亏损8=================================");
                                } else if (newprice.compareTo(buyprice) < 0) {   //亏损
                                    twHyorder.setStatus(2);
                                    twHyorder.setIsWin(2);
                                    //写财务日志
                                    addlog(uuid, username, num,companyId);

                                    twHyorder.setSellprice(newprice);
                                    twHyorder.setPloss(num);
                                    twHyorderService.updateById(twHyorder);
                                    log.info("买涨指定亏损9=================================");
                                }

                            }
                        }
                    }

                }
                //买跌
                if (hyzd == 2) {

                    if (kongyk != 0) {
                        if (kongyk == 1) { //盈利
                            if (buyprice.compareTo(newprice) < 0) {
                                sellprice = buyprice.subtract(randnum);
                            } else if (newprice.compareTo(buyprice) == 0) {
                                sellprice = buyprice.subtract(randnum);
                            } else if (newprice.compareTo(buyprice) < 0) {
                                sellprice = newprice;
                            }

                            //增加资产
                            twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());

                            //修改订单状态
                            twHyorder.setStatus(2);
                            twHyorder.setIsWin(1);
                            twHyorder.setSellprice(sellprice);
                            twHyorder.setPloss(ylnum);
                            twHyorderService.updateById(twHyorder);

                            //写财务日志
                            addlog(uuid, username, money,companyId);
                        }

                        if (kongyk == 2) { //亏损
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
                            addlog(uuid, username, num,companyId);
                            log.info("合约指定亏损成功======================================");
                        }
                    }

                    if (kongyk == 0) {
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
                            twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());

                            //修改订单状态
                            twHyorder.setStatus(2);
                            twHyorder.setIsWin(1);
                            twHyorder.setSellprice(sellprice);
                            twHyorder.setPloss(ylnum);
                            twHyorderService.updateById(twHyorder);

                            //写财务日志
                            addlog(uuid, username, money,companyId);
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
                            addlog(uuid, username, num,companyId);
                        }

                        if (!isWinArray && !isLoseArray) { //如果未指定盈利和亏损，则按单控的计算
                            if (kongyk == 1) { //盈利
                                if (buyprice.compareTo(newprice) < 0) {
                                    sellprice = buyprice.subtract(randnum);
                                } else if (newprice.compareTo(buyprice) == 0) {
                                    sellprice = buyprice.subtract(randnum);
                                } else if (newprice.compareTo(buyprice) < 0) {
                                    sellprice = newprice;
                                }

                                //增加资产
                                twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());

                                //修改订单状态
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(1);
                                twHyorder.setSellprice(sellprice);
                                twHyorder.setPloss(ylnum);
                                twHyorderService.updateById(twHyorder);

                                //写财务日志
                                addlog(uuid, username, money,companyId);
                            }

                            if (kongyk == 2) { //亏损
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
                                addlog(uuid, username, num,companyId);
                                log.info("合约指定亏损成功======================================");
                            }


                            if (kongyk == 0) {
                                if (buyprice.compareTo(newprice) < 0) {   //亏损
                                    twHyorder.setStatus(2);
                                    twHyorder.setIsWin(2);
                                    //写财务日志
                                    addlog(uuid, username, num,companyId);

                                    twHyorder.setSellprice(newprice);
                                    twHyorder.setPloss(num);
                                    twHyorderService.updateById(twHyorder);

                                } else if (newprice.compareTo(buyprice) == 0) {
                                    twHyorder.setStatus(2);
                                    twHyorder.setIsWin(2);
                                    //写财务日志
                                    addlog(uuid, username, num,companyId);

                                    twHyorder.setSellprice(newprice);
                                    twHyorder.setPloss(num);
                                    twHyorderService.updateById(twHyorder);

                                } else if (newprice.compareTo(buyprice) < 0) {   //盈利
                                    twUserCoinService.incre(uuid, money, twUserCoin.getUsdt());
                                    twHyorder.setStatus(2);
                                    twHyorder.setIsWin(1);
                                    //写财务日志
                                    addlog(uuid, username, money,companyId);

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

    public  void hycarryplanout() {
        Instant now = Instant.now();

        // 将当前时间戳减去12个小时
        Instant twelveHoursAgo = now.minusSeconds(12 * 60 * 60);
        int nowtime = (int) twelveHoursAgo.getEpochSecond();
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",0);
        queryWrapper.eq("order_type",1);
        queryWrapper.le("intplantime", nowtime);
        List<TwHyorder> list = twHyorderService.list(queryWrapper);

        for (TwHyorder twHyorder:list){
            twHyorder.setStatus(1);
            twHyorderService.updateById(twHyorder);
        }
    }
    public  void mockhycarryplanout() {
        Instant now = Instant.now();

        // 将当前时间戳减去12个小时
        Instant twelveHoursAgo = now.minusSeconds(12 * 60 * 60);
        int nowtime = (int) twelveHoursAgo.getEpochSecond();
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",0);
        queryWrapper.eq("order_type",2);
        queryWrapper.le("intplantime", nowtime);
        List<TwHyorder> list = twHyorderService.list(queryWrapper);

        for (TwHyorder twHyorder:list){
            twHyorder.setStatus(1);
            twHyorderService.updateById(twHyorder);
        }
    }


    public  void mockhycarryout() {
        Instant now = Instant.now();

        // 将当前时间戳减去12个小时
        Instant twelveHoursAgo = now.minusSeconds(12 * 60 * 60);
        int nowtime = (int) twelveHoursAgo.getEpochSecond();
        QueryWrapper<TwHyorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        queryWrapper.eq("order_type",2);
        queryWrapper.le("intselltime", nowtime);
        List<TwHyorder> list = twHyorderService.list(queryWrapper);


        for (TwHyorder twHyorder:list){

            Integer companyId = twHyorder.getCompanyId();
            QueryWrapper<TwHysetting> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("company_id",twHyorder.getCompanyId());
            TwHysetting twHysetting = twHysettingService.getOne(queryWrapper1);
            String hyYlid = twHysetting.getHyYlid();
            String hyKsid = twHysetting.getHyKsid();
            String[] winarr = hyYlid.split("\\|");
            String[] lossarr = hyKsid.split("\\|");

            String mapOrder = map.get(twHyorder.getOrderNo());

            if(StringUtils.isEmpty(mapOrder)) {
                map.put(twHyorder.getOrderNo(), twHyorder.getOrderNo());
                String coinname = twHyorder.getCoinname();
                String symbol = coinname.toLowerCase().replace("/", "");
                String url = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=" + symbol;
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

                QueryWrapper<TwMockUserCoin> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("userid", uid);
                TwMockUserCoin twMockUserCoin = twMockUserCoinService.getOne(queryWrapper3);


                //买涨
                if (hyzd == 1) {
                    if (kongyk != 0) {   //已控
                        if (kongyk == 1) {  //盈利
                            if (buyprice.compareTo(newprice) < 0) {
                                sellprice = newprice;
                            } else if (newprice.compareTo(buyprice) == 0) {
                                sellprice = newprice.add(randnum);
                            } else if (newprice.compareTo(buyprice) < 0) {
                                sellprice = buyprice.add(randnum);
                            }
                            //增加资产
                            twMockUserCoinService.incre(uuid, money, twMockUserCoin.getUsdt());

                            //修改订单状态
                            twHyorder.setStatus(2);
                            twHyorder.setIsWin(1);
                            twHyorder.setSellprice(sellprice);
                            twHyorder.setPloss(ylnum);
                            twHyorderService.updateById(twHyorder);

                            //写财务日志
                            addlog(uuid, username, money,companyId);

                            log.info("买涨已控盈利1=================================");
                        }

                        if (kongyk == 2) { //亏损
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
                            addlog(uuid, username, num,companyId);

                            log.info("买涨已控亏损2=================================");
                        }
                    }

                    if (kongyk == 0) {   //未控
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
                            twMockUserCoinService.incre(uuid, money, twMockUserCoin.getUsdt());

                            //修改订单状态
                            twHyorder.setStatus(2);
                            twHyorder.setIsWin(1);
                            twHyorder.setSellprice(sellprice);
                            twHyorder.setPloss(ylnum);
                            twHyorderService.updateById(twHyorder);

                            //写财务日志
                            addlog(uuid, username, money,companyId);

                            log.info("买涨指定盈利3=================================");
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
                            addlog(uuid, username, num,companyId);

                            log.info("买涨指定亏损4=================================");
                        }

                        if (!isWinArray && !isLoseArray) {
                            //如果未指定盈利和亏损，则按单控的计算

                            if (kongyk == 1) {  //盈利
                                if (buyprice.compareTo(newprice) < 0) {
                                    sellprice = newprice;
                                } else if (newprice.compareTo(buyprice) == 0) {
                                    sellprice = newprice.add(randnum);
                                } else if (newprice.compareTo(buyprice) < 0) {
                                    sellprice = buyprice.add(randnum);
                                }
                                //增加资产
                                twMockUserCoinService.incre(uuid, money, twMockUserCoin.getUsdt());

                                //修改订单状态
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(1);
                                twHyorder.setSellprice(sellprice);
                                twHyorder.setPloss(ylnum);
                                twHyorderService.updateById(twHyorder);

                                //写财务日志
                                addlog(uuid, username, money,companyId);

                                log.info("买涨指定盈利5=================================");

                            }

                            if (kongyk == 2) { //亏损
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
                                addlog(uuid, username, num,companyId);

                                log.info("买涨指定亏损6=================================");
                            }

                            if (kongyk == 0) {
                                if (buyprice.compareTo(newprice) < 0) {   //盈利
                                    twMockUserCoinService.incre(uuid, money, twMockUserCoin.getUsdt());
                                    twHyorder.setStatus(2);
                                    twHyorder.setIsWin(1);
                                    //写财务日志
                                    addlog(uuid, username, money,companyId);

                                    twHyorder.setSellprice(newprice);
                                    twHyorder.setPloss(ylnum);
                                    twHyorderService.updateById(twHyorder);
                                    log.info("买涨指定盈利7=================================");

                                } else if (newprice.compareTo(buyprice) == 0) {
                                    twHyorder.setStatus(2);
                                    twHyorder.setIsWin(2);
                                    //写财务日志
                                    addlog(uuid, username, num,companyId);

                                    twHyorder.setSellprice(newprice);
                                    twHyorder.setPloss(num);
                                    twHyorderService.updateById(twHyorder);
                                    log.info("买涨指定亏损8=================================");
                                } else if (newprice.compareTo(buyprice) < 0) {   //亏损
                                    twHyorder.setStatus(2);
                                    twHyorder.setIsWin(2);
                                    //写财务日志
                                    addlog(uuid, username, num,companyId);

                                    twHyorder.setSellprice(newprice);
                                    twHyorder.setPloss(num);
                                    twHyorderService.updateById(twHyorder);
                                    log.info("买涨指定亏损9=================================");
                                }

                            }
                        }
                    }

                }
                //买跌
                if (hyzd == 2) {

                    if (kongyk != 0) {
                        if (kongyk == 1) { //盈利
                            if (buyprice.compareTo(newprice) < 0) {
                                sellprice = buyprice.subtract(randnum);
                            } else if (newprice.compareTo(buyprice) == 0) {
                                sellprice = buyprice.subtract(randnum);
                            } else if (newprice.compareTo(buyprice) < 0) {
                                sellprice = newprice;
                            }

                            //增加资产
                            twMockUserCoinService.incre(uuid, money, twMockUserCoin.getUsdt());

                            //修改订单状态
                            twHyorder.setStatus(2);
                            twHyorder.setIsWin(1);
                            twHyorder.setSellprice(sellprice);
                            twHyorder.setPloss(ylnum);
                            twHyorderService.updateById(twHyorder);

                            //写财务日志
                            addlog(uuid, username, money,companyId);
                        }

                        if (kongyk == 2) { //亏损
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
                            addlog(uuid, username, num,companyId);
                            log.info("合约指定亏损成功======================================");
                        }
                    }

                    if (kongyk == 0) {
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
                            twMockUserCoinService.incre(uuid, money, twMockUserCoin.getUsdt());

                            //修改订单状态
                            twHyorder.setStatus(2);
                            twHyorder.setIsWin(1);
                            twHyorder.setSellprice(sellprice);
                            twHyorder.setPloss(ylnum);
                            twHyorderService.updateById(twHyorder);

                            //写财务日志
                            addlog(uuid, username, money,companyId);
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
                            addlog(uuid, username, num,companyId);
                        }

                        if (!isWinArray && !isLoseArray) { //如果未指定盈利和亏损，则按单控的计算
                            if (kongyk == 1) { //盈利
                                if (buyprice.compareTo(newprice) < 0) {
                                    sellprice = buyprice.subtract(randnum);
                                } else if (newprice.compareTo(buyprice) == 0) {
                                    sellprice = buyprice.subtract(randnum);
                                } else if (newprice.compareTo(buyprice) < 0) {
                                    sellprice = newprice;
                                }

                                //增加资产
                                twMockUserCoinService.incre(uuid, money, twMockUserCoin.getUsdt());

                                //修改订单状态
                                twHyorder.setStatus(2);
                                twHyorder.setIsWin(1);
                                twHyorder.setSellprice(sellprice);
                                twHyorder.setPloss(ylnum);
                                twHyorderService.updateById(twHyorder);

                                //写财务日志
                                addlog(uuid, username, money,companyId);
                            }

                            if (kongyk == 2) { //亏损
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
                                addlog(uuid, username, num,companyId);
                                log.info("合约指定亏损成功======================================");
                            }


                            if (kongyk == 0) {
                                if (buyprice.compareTo(newprice) < 0) {   //亏损
                                    twHyorder.setStatus(2);
                                    twHyorder.setIsWin(2);
                                    //写财务日志
                                    addlog(uuid, username, num,companyId);

                                    twHyorder.setSellprice(newprice);
                                    twHyorder.setPloss(num);
                                    twHyorderService.updateById(twHyorder);

                                } else if (newprice.compareTo(buyprice) == 0) {
                                    twHyorder.setStatus(2);
                                    twHyorder.setIsWin(2);
                                    //写财务日志
                                    addlog(uuid, username, num,companyId);

                                    twHyorder.setSellprice(newprice);
                                    twHyorder.setPloss(num);
                                    twHyorderService.updateById(twHyorder);

                                } else if (newprice.compareTo(buyprice) < 0) {   //盈利
                                    twMockUserCoinService.incre(uuid, money, twMockUserCoin.getUsdt());
                                    twHyorder.setStatus(2);
                                    twHyorder.setIsWin(1);
                                    //写财务日志
                                    addlog(uuid, username, money,companyId);

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


    public void report(){


//        // 获取当前时间
//        Date now = new Date();
//        String nowDate = DateUtil.format(now, "yyyy-MM-dd");



        // 设置时区为美东时间
        ZoneId easternTimeZone = ZoneId.of("America/New_York");


        // 获取当前日期
        LocalDate currentDate = LocalDate.now(easternTimeZone);

        // 早上八点
        LocalTime startOfDay1 = LocalTime.of(12, 0, 0);

        // 第二天早上八点
        LocalTime endOfDay1 = startOfDay1.minusHours(24L);

        // 一天的开始时间
        LocalDateTime startOfDay =  LocalDateTime.of(currentDate, startOfDay1);

        // 一天的结束时间
        LocalDateTime endOfDay = LocalDateTime.of(currentDate.plusDays(1), endOfDay1);

        String nowDate = DateUtil.format(endOfDay, "yyyy-MM-dd");

        String startTime = DateUtil.format(startOfDay, "yyyy-MM-dd HH:mm:ss");

        String endTime = DateUtil.format(endOfDay, "yyyy-MM-dd HH:mm:ss");



        List<TwCompany> list = twCompanyService.list();

        for(TwCompany twCompany:list){
            Integer companyId = twCompany.getId();


            // 将时间转换为时间戳（秒级别）
            long startTimestamp = startOfDay.toEpochSecond(ZoneOffset.UTC);
            long endTimestamp = endOfDay.toEpochSecond(ZoneOffset.UTC);

            //今日注册人数
            int todayUser = 0;
            todayUser = twUserService.countTodayUsers(startTimestamp,endTimestamp,companyId);
            // 总注册人数 M("user")->count()
            int allUser = 0;//userDao.countAllUsers();
            allUser = twUserService.countAllUsers(companyId);


            //用户日总余额
            BigDecimal userCoinDay = new BigDecimal(100);
            userCoinDay = twUserCoinService.dayUserCoin(startTime, endTime,companyId);
            //用户总余额
            BigDecimal userCoinSum = new BigDecimal(100);
            userCoinSum = twUserCoinService.sumUserCoin(companyId);


            // 查询秒合约总下单数 M("hyorder")->where 'status' => 1 ->count();
            int allHyOrders = 0;//hyOrderDao.countUnClosedOrders();
            allHyOrders = twHyorderService.countUnClosedOrders(companyId);

            // 查询秒合约今日下单数
            int hyOrders = 0;//rechargeDao.sumDayRecharge(startTime, endTime);
            hyOrders = twHyorderService.countHyOrdersDay(startTime, endTime,companyId);

            // 查询合约总下单额
            BigDecimal orderSum = new BigDecimal(0);//rechargeDao.sumDayRecharge(startTime, endTime);
            orderSum = twHyorderService.orderSum(companyId);

            // 查询合约今日下单额
            BigDecimal orderDay = new BigDecimal(0);//rechargeDao.sumDayRecharge(startTime, endTime);
            orderDay = twHyorderService.orderDay(startTime, endTime,companyId);

            // 查询合约总盈亏
            BigDecimal winLosshyAllOrders = new BigDecimal(0);//rechargeDao.sumDayRecharge(startTime, endTime);
            winLosshyAllOrders = twHyorderService.winLosshyAllOrders(companyId);

            // 查询合约今日盈亏
            BigDecimal winLosshyDayOrders = new BigDecimal(0);//rechargeDao.sumDayRecharge(startTime, endTime);
            winLosshyDayOrders = twHyorderService.winLosshyDayOrders(startTime, endTime,companyId);

            // 查询今日充值人数  M("user")->where($linewhere)->count();  查询今天的
            int usercz = 0;//userDao.countLineUsers(nowDate);
            usercz = twRechargeService.usersCount(startTime, endTime,companyId);

            // 查询总充值人数  M("user")->where($linewhere)->count();  查询今天的
            int userczTotal = 0;//userDao.countLineUsers(nowDate);
            userczTotal = twRechargeService.usersCountTotal(companyId);

            BigDecimal dayRecharge = new BigDecimal(0);//rechargeDao.sumDayRecharge(startTime, endTime);
            dayRecharge = twRechargeService.sumDayRecharge(startTime, endTime,companyId);
            // 查询充值数量   M("recharge")->where(array('status' => 2))->sum("num");
            BigDecimal allRecharge = new BigDecimal(0);//rechargeDao.sumAllRecharge();
            allRecharge = twRechargeService.sumAllRecharge(companyId);

            // 查询今日提现人数  M("user")->where($linewhere)->count();  查询今天的
            int usertx = 0;//userDao.countLineUsers(nowDate);
            usertx = twMyzcService.usersCount(startTime, endTime,companyId);

            // 查询总提现人数  M("user")->where($linewhere)->count();  查询今天的
            int usertxTotal = 0;//userDao.countLineUsers(nowDate);
            usertxTotal = twMyzcService.usersCountTotal(companyId);

            // 查询提币数量 M("myzc")->where($daywhere)->where(array('status' => 2))->sum("num");
            BigDecimal dayWithdraw = new BigDecimal(0);//myzcDao.sumDayWithdraw(startTime, endTime);
            dayWithdraw = twMyzcService.sumDayWithdraw(startTime, endTime,companyId);
            // 查询提币数量 M("myzc")->where(array('status' => 2))->sum("num");
            BigDecimal allWithdraw = new BigDecimal(0);//myzcDao.sumAllWithdraw();
            allWithdraw = twMyzcService.sumAllWithdraw(companyId);
//            // 查询今日该客量  M("user")->where($linewhere)->count();  查询今天的
            int allLineUsers = 0;//userDao.countLineUsers(nowDate);
            allLineUsers = twUserService.countLineUsers(startTime, endTime,companyId);

            //总客损


            QueryWrapper<TwReport> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("day_date", nowDate);
            queryWrapper3.eq("company_id", companyId);
            TwReport one = twReportService.getOne(queryWrapper3);
            if(one == null){
                TwReport twReport = new TwReport();
                twReport.setCompanyId(companyId);
                twReport.setRegistrantTotal(allUser);
                twReport.setDayDate(nowDate);
                twReport.setActive(allLineUsers);
                twReport.setDayAmount(userCoinDay);
                twReport.setTotalAmount(userCoinSum);
                twReport.setRegistrant(todayUser);
                twReport.setOrders(hyOrders);
                twReport.setOrderTotal(allHyOrders);
                twReport.setOrderNum(orderDay);
                twReport.setOrderSum(orderSum);
                twReport.setProfitNum(winLosshyDayOrders);
                twReport.setProfitSum(winLosshyAllOrders);
                twReport.setRecharge(usercz);
                twReport.setRechargeTotal(userczTotal);
                twReport.setRechargeNum(dayRecharge);
                twReport.setRechargeSum(allRecharge);
                twReport.setPayoutTotal(usertxTotal);
                twReport.setPayoutNum(dayWithdraw);
                twReport.setPayoutSum(allWithdraw);
                twReport.setDayCustomerLoss(dayRecharge.subtract(dayWithdraw));
                twReport.setCustomerLoss(allRecharge.subtract(allWithdraw));
                twReport.setCreateTime(new Date());
                twReportService.saveOrUpdate(twReport);
            }else{
                one.setCompanyId(companyId);
                one.setDayDate(nowDate);
                one.setActive(allLineUsers);
                one.setDayAmount(userCoinDay);
                one.setTotalAmount(userCoinSum);
                one.setRegistrantTotal(allUser);
                one.setRegistrant(todayUser);
                one.setOrders(hyOrders);
                one.setOrderTotal(allHyOrders);
                one.setOrderNum(orderDay);
                one.setOrderSum(orderSum);
                one.setProfitNum(winLosshyDayOrders);
                one.setProfitSum(winLosshyAllOrders);
                one.setRecharge(usercz);
                one.setRechargeTotal(userczTotal);
                one.setRechargeNum(dayRecharge);
                one.setRechargeSum(allRecharge);
                one.setPayout(usertx);
                one.setPayoutTotal(usertxTotal);
                one.setPayoutNum(dayWithdraw);
                one.setPayoutSum(allWithdraw);
                one.setDayCustomerLoss(dayRecharge.subtract(dayWithdraw));
                one.setCustomerLoss(allRecharge.subtract(allWithdraw));
                one.setUpdateTime(new Date());
                twReportService.updateById(one);
            }

        }

    }

//
//    public void kjUser(){
//        // 获取当前时间
//        Date now = new Date();
//        String nowDate = cn.hutool.core.date.DateUtil.format(now, "yyyy-MM-dd");
//        // 获取今日开始和结束时间
//        String startTime = nowDate + " 00:00:00";
//        String endTime = nowDate + " 23:59:59";
//
//
//        // 获取今天的日期
//        LocalDate today = LocalDate.now();
//
//        // 获取今天的开始时间和结束时间
//        LocalDateTime startOfDay = today.atStartOfDay();
//        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
//
//        // 将时间转换为时间戳（秒级别）
//        long startTimestamp = startOfDay.toEpochSecond(ZoneOffset.UTC);
//        long endTimestamp = endOfDay.toEpochSecond(ZoneOffset.UTC);
//
//        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.ge("addtime", startTimestamp);
//        queryWrapper.le("addtime", endTimestamp);
//        List<TwUser> list = twUserService.list(queryWrapper);
//        for(TwUser twUser:list){
//            QueryWrapper<TwKuangji> queryWrapper5 = new QueryWrapper<>();
//            queryWrapper5.eq("company_id",twUser.getCompanyId());
//            List<TwKuangji> list2 = twKuangjiService.list(queryWrapper5);
//            for(TwKuangji twKuangji:list2){
//                Integer userid = twUser.getId();
//                Integer kjid = twKuangji.getId();
//                QueryWrapper<TwUserKuangji> queryWrapper2= new QueryWrapper<>();
//                queryWrapper2.eq("kj_id", kjid);
//                queryWrapper2.eq("user_id", userid);
//                queryWrapper2.eq("company_id",twUser.getCompanyId());
//                TwUserKuangji one2 = twUserKuangjiService.getOne(queryWrapper2);
//                if(one2 == null){
//                    TwUserKuangji twUserKuangji = new TwUserKuangji();
//                    twUserKuangji.setMin(twKuangji.getPricemin());
//                    twUserKuangji.setMax(twKuangji.getPricemax());
//                    twUserKuangji.setNum(1);
//                    twUserKuangji.setCompanyId(twUser.getCompanyId());
//                    twUserKuangji.setKjId(twKuangji.getId());
//                    twUserKuangji.setKjName(twKuangji.getTitle());
//                    twUserKuangji.setUserId(userid);
//                    twUserKuangji.setCreateTime(new Date());
//                    twUserKuangjiService.save(twUserKuangji);
//                    log.info("用户单控新增成功userid{}",userid);
//                }
//            }
//        }
//
//    }
//
//    public void curreny(){
//        QueryWrapper<TwCurrency> queryWrapper5 = new QueryWrapper<>();
//        List<TwCurrency> list = twCurrencyService.list(queryWrapper5);
//        for(TwCurrency twCurrency:list){
//            String currency = twCurrency.getCurrency();
//            QueryWrapper<TwCurrenyList> queryWrapper1 = new QueryWrapper<>();
//            queryWrapper1.eq("name_en", currency);
//            TwCurrenyList one = twCurrenyListService.getOne(queryWrapper1);
//            if(one != null){
//                BigDecimal currenyRate = one.getCurrenyRate();
//                twCurrency.setRate(currenyRate);
//                twCurrency.setUpdateTime(new Date());
//                twCurrencyService.updateById(twCurrency);
//            }
//        }
//    }
//
//
//    public void currenyList() {
//        log.info("货币汇率进入=============================================");
//        String str = "https://open.er-api.com/v6/latest/USD";
//        Map<String, Object> map = CommonUtil.doGet(str, "");
//        JSONObject res = JSONObject.parseObject(map.get("res").toString());
//        String rates = JSONObject.parseObject(res.get("rates").toString()).toString();
//        String replace = rates.replace("{", "");
//        String replace1 = replace.replace("}", "");
//        String[] split = replace1.split(",");
//        for(int  i = 0 ; i <split.length ; i++){
//            TwCurrenyList twCurrenyList = new TwCurrenyList();
//            String srate = split[i];
//            String[] split1 = srate.split(":");
//            String name = split1[0].replace("\"", "");
//            String price = split1[1];
//            QueryWrapper<TwCurrenyList> queryWrapper1 = new QueryWrapper<>();
//            queryWrapper1.eq("name_en", name); // 添加查询条件
//            TwCurrenyList one = twCurrenyListService.getOne(queryWrapper1);
//            if(one == null){
//                twCurrenyList.setNameEn(name);
//                twCurrenyList.setCurrenyRate(new BigDecimal(price));
//                twCurrenyList.setCreateTime(new Date());
//                twCurrenyListService.save(twCurrenyList);
//            }else{
//                one.setCurrenyRate(new BigDecimal(price));
//                one.setUpdateTime(new Date());
//                twCurrenyListService.updateById(one);
//            }
//        }
//    }

    public static void main(String[] args) {
                String str = "https://open.er-api.com/v6/latest/USD";
        Map<String, Object> map = CommonUtil.doGet(str, "");
        JSONObject res = JSONObject.parseObject(map.get("res").toString());
        String rates = JSONObject.parseObject(res.get("rates").toString()).toString();
        System.out.println(rates);
    }
}
