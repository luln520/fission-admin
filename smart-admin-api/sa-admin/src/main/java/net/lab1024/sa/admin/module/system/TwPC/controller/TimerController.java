package net.lab1024.sa.admin.module.system.TwPC.controller;

import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.service.impl.TimerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 定时器
 */

//缺一个矿机是否过期的任务

@Component
@Slf4j
public class TimerController {
    @Autowired
    private TimerServiceImpl timerService;

    /**
     * 工具方法：执行api （api调用）
     * 方法名：getCurl
     * 参数：url（api的）
     * 大致逻辑：
     *         1.设置 禁用 SSL 证书验证
     *         2.设置请求头 agent：Mozilla/5.0 (Linux; U; Android 4.4.1; zh-cn) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.5 Mobile Safari/533.1
     *         3.发送请求 并直接返回 请求完成的数据
     * */

//暂时不用这个功能
    /**
     * 对USDT地址执行查询
     * 参数：address（钱包地址）
     * 大致逻辑：
     *         1.构建请求参数 然后get请求
     *         2.返回的数据 遍历提取并结算需要的字段   基础url需要拼接参数：https://apilist.tronscan.org/api/token_trc20/transfers?  获取转账列表
     *         3.返回list
     *
     * 参考代码：
     *      //对USDT地址执行查询
     *     public function getTransferInList($address)
     *     {
     *         $result = [];
     *         $end = time() * 1000;
     *         $start = (time() - 1800) * 1000;//监控时间  20分钟
     *         $params = [
     *             'limit' => 300,
     *             'start' => 0,
     *             'direction' => 'in',
     *             'relatedAddress' => $address,
     *             'start_timestamp' => $start,
     *             'end_timestamp' => $end,
     *         ];
     *         $api = "https://apilist.tronscan.org/api/token_trc20/transfers?" . http_build_query($params);
     *         $resp = $this->get_curl($api);
     *         $data = json_decode($resp, true);
     *         foreach ($data['token_transfers'] as $transfer) {
     *             if ($transfer['to_address'] == $address && $transfer['finalResult'] == 'SUCCESS') {
     *                 $result[] = [
     *                     'time' => $transfer['block_ts'] / 1000,
     *                     'money' => $transfer['quant'] / 1000000,
     *                     'trade_id' => $transfer['transaction_id'],
     *                     'buyer' => $transfer['from_address'],
     *                     'mybuyer' => $transfer['to_address'],
     *                     'trc20id' => $transfer['transaction_id'],
     *                     'tokenAbbr' => $transfer['tokenInfo']['tokenAbbr'],//必须币种是USDT
     *                     'finalResult' => $transfer['finalResult'],//最终结果必须是成功的
     *                 ];
     *             }
     *         }
     *         return $result;
     *     }
     * */



    /**
     * 独资矿机自动收益，一天执行一次 凌晨 24:00
     * 表：kjorder，user_coin，kjprofit，bill，notice
     * 大致逻辑：
     *         1.查询 kjorder  where status=1 and type =1 遍历处理
     *         2.   查询  user_coin where userid=？（kjorder里面的uid 字段）
     *         3.   查询 kjprofit where uid=？ and  kid=(kjorder里面的id 字段) and day=?(当前时间 年月日)
     *         4.判断是否存在 逻辑3查询出来的结果
     *         5.判断 outtype==1：调取api：基础url-> https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=(拼接 当前kjorder的outcoin转小写 然后拼接上 usdt )
     *                outtype==2: 取 当前kjorder的outnum字段的值
     *         6.拼接参数写入矿机收益日志 kjprofit 表
     *         7.增加用户资产  user_coin where userid=？
     *         8.写资金日志 bill表
     *         9.修改矿机收益次数 减少1  update set synum=?   kjorder where id=?
     *         10.减少以后  判断剩余次数  <1 时候：修改update kjorder  set status=3 where id =?
     *
     * 参考代码：
     *     //独资矿机自动收益，一天执行一次 凌晨 24:00
     *     public function autokjsy()
     *     {
     *         $kjlist = M("kjorder")->where(array('status' => 1, 'type' => 1))->select();
     *         foreach ($kjlist as $key => $vo) {
     *             $id = $vo['id'];
     *             $uid = $vo['uid'];
     *             $username = $vo['username'];
     *             $minfo = M("user_coin")->where(array('userid' => $uid))->find();
     *             $nowdate = date("Y-m-d", time());
     *             $profitinfo = M("kjprofit")->where(array('uid' => $uid, 'kid' => $id, 'day' => $nowdate))->find();
     *             if (empty($profitinfo)) {
     *                 //查找矿机收益的类型以及查找收益是否需要冻结及冻结天数
     *                 $outtype = $vo['outtype'];
     *                 if ($outtype == 1) {//按产值需要查找产出币种的最新行情
     *                     $coinname = strtolower(trim($vo['outcoin']));
     *                     $outnum = $vo['outusdt'];
     *                     $symbol = $coinname . 'usdt';
     *                     $coinapi = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=" . $symbol;
     *                     $newprice = $this->getnewprice($coinapi);
     *                     $tcoinnum = sprintf("%.6f", ($outnum / $newprice)); //实际产生的币量，保留6位小数
     *                 } elseif ($outtype == 2) {
     *                     $coinname = strtolower(trim($vo['outcoin']));
     *                     $tcoinnum = $vo['outnum'];
     *                 }
     *                 $djout = $vo['djout'];//1冻结2不冻结
     *                 $djday = $vo['djnum'];//冻结天数
     *                 //写入矿机收益日志
     *                 $kjprofit_d['uid'] = $uid;
     *                 $kjprofit_d['username'] = $username;
     *                 $kjprofit_d['kid'] = $id;
     *                 $kjprofit_d['ktitle'] = $vo['kjtitle'];
     *                 $kjprofit_d['num'] = $tcoinnum;
     *                 $kjprofit_d['coin'] = $coinname;
     *                 $kjprofit_d['addtime'] = date("Y-m-d H:i:s", time());
     *                 $kjprofit_d['day'] = date("Y-m-d", time());
     *                 M("kjprofit")->add($kjprofit_d);
     *                 M("user_coin")->where(array('userid' => $uid))->setInc($coinname, $tcoinnum);
     *                 //写资金日志
     *                 $billdata['uid'] = $uid;
     *                 $billdata['username'] = $username;
     *                 $billdata['num'] = $tcoinnum;
     *                 $billdata['coinname'] = $coinname;
     *                 $billdata['afternum'] = $minfo[$coinname] + $tcoinnum;
     *                 $billdata['type'] = 8;
     *                 $billdata['addtime'] = date("Y-m-d H:i:s", time());
     *                 $billdata['st'] = 1;
     *                 $billdata['remark'] = L('矿机收益释放');
     *                 M("bill")->add($billdata);
     *                 //修改矿机收益次数
     *                 M("kjorder")->where(array('id' => $id))->setDec("synum", 1);
     *                 $reinfo = M("kjorder")->where(array('id' => $id))->find();
     *                 if ($reinfo['synum'] < 1) {
     *                     M("kjorder")->where(array('id' => $id))->save(array('status' => 3));
     *                 }
     *             }
     *         }
     *     }
     * */
    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0 */1 * * * ?")
    public void autokjsy() {
        timerService.autokjsy();
    }


    /**
     * 矿机到期时间结算
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void endkjsy() {
        timerService.endkjsy();
    }

    /**
     * 自动按风控比例设置订单的盈亏比例  5秒执行一次
     * 表：hyorder，hysetting
     * 大致逻辑：
     *         1.查询 hyorder没有指定输赢的数量   hyorder count(id) where status=1 and kongy=0
     *         2.查询  hysetting where id=1
     *         3.判断 hy_fkgl 字段hy_fkgl>0  不满足就不执行下面的
     *         4.计算输赢数量（取整）   输数量：没有指定输盈的数量* hy_fkgl的值/100    赢数量：没有指定输盈的数量-输数量
     *         5.数据库截取对应的数量   hyorder where status=1 and kongy=0 order by num asc limit （输赢数量）
     *         6.分别遍历 取出来的输赢数据   判断时间然后 输：update hyorder set kongyk=2 where id=?
     *                                                  赢：update hyorder set kongyk=1 where id=?
     *
     * 参考代码：
     *     //自动按风控比例设置订单的盈亏比例 5秒执行一次
     *     public function setwl()
     *     {
     *         $where['status'] = 1;
     *         $where['kongyk'] = 0;
     *         $count = M("hyorder")->where($where)->count();
     *         $setting = M("hysetting")->where(array('id' => 1))->find();
     *         if ($setting['hy_fkgl'] > 0) {
     *             $ylcount = intval($count * $setting['hy_fkgl'] / 100);
     *             $kscount = $count - $ylcount;
     *             if ($ylcount > 0) {
     *                 $yllist = M("hyorder")->where($where)->order("num asc")->limit($ylcount)->select();
     *                 foreach ($yllist as $k => $v) {
     *                     $yid = $v['id'];
     *                     if (time() >= $v['intselltime']) {
     *                         M("hyorder")->where(array('id' => $yid))->save(array('kongyk' => 1));
     *                     }
     *                 }
     *             }
     *             if ($kscount > 0) {
     *                 $kslist = M("hyorder")->where($where)->order("num asc")->limit($kscount)->select();
     *                 foreach ($kslist as $k => $v) {
     *                     $kid = $v['id'];
     *                     if (time() >= $v['intselltime']) {
     *                         M("hyorder")->where(array('id' => $kid))->save(array('kongyk' => 2));
     *                     }
     *                 }
     *             }
     *         }
     *     }
     * */


    /**
     * 自动结算合约订单  5秒执行一次（重点：看代码）
     * 表：hyorder，hysetting,user_coin
     * 大致逻辑：
     *         1.查询合约订单 查询合约设置
     *         2.拿到设置好的指定盈利ID组和指定亏损ID组
     *         3.先处理设置好的ID组 然后 处理 如果指未定盈利和亏损则按单控的计算
     *
     * 参考代码：
     *
     *      //自动结算合约订单  5秒执行一次(需要)
     *     public function hycarryout()
     *     {
     *         {
     *             $nowtime = time();//当前时间 秒
     *             $where['status'] = 1;
     *             $where['intselltime'] = array('elt', $nowtime);//小于等于
     *             $list = M("hyorder")->where($where)->select();// hyorder where status=1 and  intselltime<=nowtime
     *             //获取合约参数
     *             $setting = M("hysetting")->where(array('id' => 1))->find();//hysetting  where id =1
     *             //指定盈利ID组
     *             $winarr = explode('|', $setting['hy_ylid']);//分割 hy_ylid字段 分隔符：|
     *             //指定亏损ID组
     *             $lossarr = explode('|', $setting['hy_ksid']);//分割 hy_ksid字段 分隔符：|
     *             foreach ($list as $key => $vo) //遍历
     *                 $coinname = $vo['coinname'];
     *             $coinarr = explode("/", $coinname);
     *             $symbol = strtolower($coinarr[0]) . strtolower($coinarr[1]);//拼接  $symbol
     *             $coinapi = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=" . $symbol; //调取api 基础地址：istory/kline?period=1day&size=1&symbol=？
     *             $newprice = $this->getnewprice($coinapi);
     *             $randnum = "0." . rand(1000, 9999);//随机数 0.1-0.9999
     *             $buyprice = $vo['buyprice'];
     *             $otype = $vo['hyzd']; //合约方向
     *             $dkong = $vo['kongyk']; //单控设置
     *             $uid = $vo['uid'];//会员ID
     *             $id = $vo['id'];//记录ID
     *             $num = $vo['num'];
     *             $hybl = $vo['hybl'];
     *             $ylnum = $num * ($hybl / 100);
     *             $money = $num + $ylnum;//盈利金额
     *             //买涨
     *             if ($otype == 1) {
     *                 if (in_array($uid, $winarr)) {//如果有指定盈利ID，则按盈利结算
     *                     if ($newprice > $buyprice) {
     *                         $sellprice = $newprice;
     *                     } elseif ($newprice == $buyprice) {
     *                         $sellprice = $newprice + $randnum;
     *                     } elseif ($newprice < $buyprice) {
     *                         $sellprice = $buyprice + $randnum;
     *                     }
     *                     //增加资产
     *                     M("user_coin")->where(array('userid' => $uid))->setInc("usdt", $money); //增加资产:  sql: update user_coin set usdt=? where userid=?
     *                     //修改订单状态
     *                     $sd['status'] = 2;
     *                     $sd['is_win'] = 1;
     *                     $sd['sellprice'] = $sellprice;
     *                     $sd['ploss'] = $ylnum;
     *                     M("hyorder")->where(array('id' => $id))->save($sd);//修改订单状态   update set status=2 is_win=1 sellprice=？ ploss=？ where id=?
     *                     //写财务日志（调取工具方法）
     *                     $this->addlog($uid, $vo['username'], $money);
     *                 } elseif (in_array($uid, $lossarr)) {//如果有指定亏损ID，则按亏损结算
     *                     //买涨,指定亏损,结算价格要低于买入价格
     *                     if ($newprice > $buyprice) {
     *                         $sellprice = $buyprice - $randnum;
     *                     } elseif ($newprice == $buyprice) {
     *                         $sellprice = $buyprice - $randnum;
     *                     } elseif ($newprice < $buyprice) {
     *                         $sellprice = $newprice;
     *                     }
     *                     //修改订单状态
     *                     $sd['status'] = 2;
     *                     $sd['is_win'] = 2;
     *                     $sd['sellprice'] = $sellprice;
     *                     $sd['ploss'] = $num;
     *                     M("hyorder")->where(array('id' => $id))->save($sd);//修改订单状态   update set status=2 is_win=2 sellprice=？ ploss=？ where id=?
     *                 } else {//如果未指定盈利和亏损，则按单控的计算
     *                     if ($dkong == 1) {//盈利
     *
     *                         if ($newprice > $buyprice) {
     *                             $sellprice = $newprice;
     *                         } elseif ($newprice == $buyprice) {
     *                             $sellprice = $newprice + $randnum;
     *                         } elseif ($newprice < $buyprice) {
     *                             $sellprice = $buyprice + $randnum;
     *                         }
     *                         //增加资产
     *                         M("user_coin")->where(array('userid' => $uid))->setInc("usdt", $money);//增加资产:  sql: update user_coin set usdt=? where userid=?
     *                         //修改订单状态
     *                         $sd['status'] = 2;
     *                         $sd['is_win'] = 1;
     *                         $sd['sellprice'] = $sellprice;
     *                         $sd['ploss'] = $ylnum;
     *                         M("hyorder")->where(array('id' => $id))->save($sd);//修改订单状态   update set status=2 is_win=1 sellprice=？ ploss=？ where id=?
     *                         //写财务日志（调取工具方法）
     *                         $this->addlog($uid, $vo['username'], $money);
     *                     } elseif ($dkong == 2) {//亏损
     *                         if ($newprice > $buyprice) {
     *                             $sellprice = $buyprice - $randnum;
     *                         } elseif ($newprice == $buyprice) {
     *                             $sellprice = $buyprice - $randnum;
     *                         } elseif ($newprice < $buyprice) {
     *                             $sellprice = $newprice;
     *                         }
     *                         //修改订单状态
     *                         $sd['status'] = 2;
     *                         $sd['is_win'] = 2;
     *                         $sd['sellprice'] = $sellprice;
     *                         $sd['ploss'] = $num;
     *                         M("hyorder")->where(array('id' => $id))->save($sd);//修改订单状态   update set status=2 is_win=2 sellprice=？ ploss=？ where id=?
     *                     }
     *                 }
     *                 //买跌
     *             } elseif ($otype == 2) {
     *                 if (in_array($uid, $winarr)) {//如果有指定盈利ID，则按盈利结算
     *                     if ($newprice > $buyprice) {
     *                         $sellprice = $buyprice - $randnum;
     *                     } elseif ($newprice == $buyprice) {
     *                         $sellprice = $buyprice - $randnum;
     *                     } elseif ($newprice < $buyprice) {
     *                         $sellprice = $newprice;
     *                     }
     *                     //增加资产
     *                     M("user_coin")->where(array('userid' => $uid))->setInc("usdt", $money);
     *                     //修改订单状态
     *                     $sd['status'] = 2;
     *                     $sd['is_win'] = 1;
     *                     $sd['sellprice'] = $sellprice;
     *                     $sd['ploss'] = $ylnum;
     *                     M("hyorder")->where(array('id' => $id))->save($sd);
     *                     //写财务日志
     *                     $this->addlog($uid, $vo['username'], $money);
     *                 } elseif (in_array($uid, $lossarr)) {//如果有指定亏损ID，则按亏损结算
     *                     if ($newprice > $buyprice) {
     *                         $sellprice = $newprice;
     *                     } elseif ($newprice == $buyprice) {
     *                         $sellprice = $buyprice + $randnum;
     *                     } elseif ($newprice < $buyprice) {
     *                         $sellprice = $buyprice + $randnum;
     *                     }
     *                     //修改订单状态
     *                     $sd['status'] = 2;
     *                     $sd['is_win'] = 2;
     *                     $sd['sellprice'] = $sellprice;
     *                     $sd['ploss'] = $num;
     *                     M("hyorder")->where(array('id' => $id))->save($sd);
     *                 } else {//如果未指定盈利和亏损，则按单控的计算
     *                     if ($dkong == 1) {//盈利
     *                         if ($newprice > $buyprice) {
     *                             $sellprice = $buyprice - $randnum;
     *                         } elseif ($newprice == $buyprice) {
     *                             $sellprice = $buyprice - $randnum;
     *                         } elseif ($newprice < $buyprice) {
     *                             $sellprice = $newprice;
     *                         }
     *                         //增加资产
     *                         M("user_coin")->where(array('userid' => $uid))->setInc("usdt", $money);
     *                         //修改订单状态
     *                         $sd['status'] = 2;
     *                         $sd['is_win'] = 1;
     *                         $sd['sellprice'] = $sellprice;
     *                         $sd['ploss'] = $ylnum;
     *                         M("hyorder")->where(array('id' => $id))->save($sd);
     *                         //写财务日志
     *                         $this->addlog($uid, $vo['username'], $money);
     *                     } elseif ($dkong == 2) {//亏损
     *                         if ($newprice > $buyprice) {
     *                             $sellprice = $newprice;
     *                         } elseif ($newprice == $buyprice) {
     *                             $sellprice = $buyprice + $randnum;
     *                         } elseif ($newprice < $buyprice) {
     *                             $sellprice = $buyprice + $randnum;
     *                         }
     *                         //修改订单状态
     *                         $sd['status'] = 2;
     *                         $sd['is_win'] = 2;
     *                         $sd['sellprice'] = $sellprice;
     *                         $sd['ploss'] = $num;
     *                         M("hyorder")->where(array('id' => $id))->save($sd);
     *                     }
     *                 }
     *             }
     *         }
     *     }
     *
     * */

    @Scheduled(cron = "*/30 * * * * ?")
    public void hycarryout()  {
        timerService.hycarryout();
    }
}

