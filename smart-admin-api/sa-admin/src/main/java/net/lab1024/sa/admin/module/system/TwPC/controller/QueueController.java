package net.lab1024.sa.admin.module.system.TwPC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务队列（可能是定时器启动）
 */
@RestController
@RequestMapping("/api/pc/queue")
public class QueueController {

    /**
     *更新市场价格
     * 表：market，trade_log
     * 大致逻辑：
     *         1.查询 market   全部取出来 为 marketList
     *         2.遍历 marketList
     *         3.判断 hou_price为 空 或者 当前小时时间 是 0点
     *         4.获取当前时间  读取到 当天的 00:00:00 的时间戳
     *         5.查询 select price from trade_log where market=？ and addtime< ?  order by id desc limit 1
     *         6.判断 hou_price 是否存在  不存在： select price from trade_log where market=？ order by id desc limit 1 替换 hou_price
     *         7.修改 Market   update Market set hou_price=? where name=?
     *
     * 参考代码：
     *       // 更新市场价格
     *     public function houprice()
     *     {
     *         $list = M("market")->select();（ 查询所有 market）
     *         foreach ($list as $k => $v) {
     *             if (!$v['hou_price'] || (date('H', time()) == '0')) {
     *                 $t = time();
     *                 $start = mktime(0, 0, 0, date('m', $t), date('d', $t), date('Y', $t));(当天的 00:00:00 的时间戳)
     *                 $hou_price = M('trade_log')->where(array(
     *                     'market' => $v['name'],
     *                     'addtime' => array('lt', $start)（小于  <start）
     *                 ))->order('id desc')->getField('price');
     *                 if (!$hou_price) {
     *                     $hou_price = M('trade_log')->where(array('market' => $v['name']))->order('id asc')->getField('price');
     *                 }
     *                 M('Market')->where(array('name' => $v['name']))->setField('hou_price', $hou_price);
     *                 S('home_market', null);
     *             }
     *         }
     *     }
     * */



    /**
     *计算趋势,每天运行一次即可
     * 表：market，trade_log
     * 大致逻辑：
     *         1.查询 market   全部取出来 为 marketList
     *         2.遍历 marketList
     *         3.计算时间（看代码注释）
     *         5.查询 select max(price) trade_log from trade_logaddtime >=? and addtime < ? and market =?
     *         6.判断 price 是否存在  不存在： price=0
     *         7.构建一个数组 两个元素 时间和价格（下面一个逻辑使用）
     *         7.修改 Market   update Market set tendency=? where name=?      tendency就是上面那个数组，需要转json
     *
     * 参考代码：
     *     //计算趋势,每天运行一次即可
     *     public function tendency()
     *     {
     *         $list = M("market")->select();
     *         foreach ($list as $k => $v) {
     *             $tendency_time = 4; //间隔时间4小时
     *             $t = time();(获取当前时间戳)
     *             $tendency_str = $t - (24 * 60 * 60 * 3); //当前时间的3天前
     *             for ($x = 0; $x <= 18; $x++) { //18次,72个小时
     *                 $na = $tendency_str + (60 * 60 * $tendency_time * $x);（计算）
     *                 $nb = $tendency_str + (60 * 60 * $tendency_time * ($x + 1));（计算）
     *                 $b = M("trade_log")->where("addtime >=" . $na . " and addtime <" . $nb . " and market ='" . $v["name"] . "'")->max("price");
     *                 if (!$b) {
     *                     $b = 0;
     *                 }
     *                 $rs[] = array($na, $b);(构建一个数组 两个元素 时间和价格)
     *             }
     *             M("Market")->where(array("name" => $v["name"]))->setField("tendency", json_encode($rs));（需要转化为json字符串）
     *             unset($rs);
     *         }
     *     }
     * */

    /**
     *计算行情 生成交易日志（两个方法）
     * 表：market，trade_json，trade_log
     * 大致逻辑：
     *         1.查询 market   全部取出来 为 marketList
     *         2.遍历 marketList 调取 方法 2
     *         3.方法2 看代码
     *
     * 参考代码：
     *      //计算行情
     *     public function chart()
     *     {
     *         $list = M("market")->select();
     *         foreach ($list as $k => $v) {
     *             $this->setTradeJson($v["name"]);
     *         }
     *     }
     *
     *
     *     //生成交易日志
     *     public function setTradeJson($market)
     *     {
     *         $timearr = array(1, 5, 15, 30, 60, 240, 1440);
     *         foreach ($timearr as $k => $v) {
     *             //如果在该时间类型有数据
     *             $tradeJson = M("trade_json")->where(array("market" => $market, "type" => $v))->order("id desc")->find();
     *             if ($tradeJson) {
     *                 $addtime = $tradeJson["addtime"];
     *             } else {
     *                 $addtime = M("trade_log")->where(array("market" => $market))->order("id asc")->getField("addtime");
     *             }
     *             if ($addtime) {
     *                 $youtrade_log = M("trade_log")->where("addtime >=" . $addtime . "  and market ='" . $market . "'")->sum("num");
     *             }
     *             if ($youtrade_log) {
     *                 if ($v == 1) {
     *                     $start_time = $addtime;
     *                 } else {
     *                     $start_time = mktime(date("H", $addtime), floor(date("i", $addtime) / $v) * $v, 0, date("m", $addtime), date("d", $addtime), date("Y", $addtime));
     *                 }
     *                 for ($x = 0; $x <= 20; $x++) {
     *                     $na = $start_time + (60 * $v * $x);
     *                     $nb = $start_time + (60 * $v * ($x + 1));
     *                     if (time() < $na) {
     *                         break;
     *                     }
     *                     $sum = M("trade_log")->where("addtime >=" . $na . " and addtime <" . $nb . " and market ='" . $market . "'")->sum("num");
     *                     if ($sum) {
     *                         $sta = M("trade_log")->where("addtime >=" . $na . " and addtime <" . $nb . " and market ='" . $market . "'")->order("id asc")->getField("price");
     *                         $max = M("trade_log")->where("addtime >=" . $na . " and addtime <" . $nb . " and market ='" . $market . "'")->max("price");
     *                         $min = M("trade_log")->where("addtime >=" . $na . " and addtime <" . $nb . " and market ='" . $market . "'")->min("price");
     *                         $end = M("trade_log")->where("addtime >=" . $na . " and addtime <" . $nb . " and market ='" . $market . "'")->order("id desc")->getField("price");
     *                         $d = array($na, $sum, $sta, $max, $min, $end);//时间，成交量,成交价,最高价,最低价,收盘价
     *                         if ($v == 1) {
     *                             $newd['new_price'] = $sta;
     *                             $newd['min_price'] = $min;
     *                         }
     *                         if ($v == 1440) {
     *                             $newd['volume'] = $sum;
     *                         }
     *                         // 判断是否有最新成交记录
     *                         $jiansuotime = M("trade_log")->where(array("market" => $market))->order("id desc")->find();
     *                         $times = floor((time() - $jiansuotime['addtime']) % 86400 / 60);
     *                         if ($times >= 1) {
     *                             $jiansuo = M("trade_json")->where(array("market" => $market, "data" => json_encode($d), "addtime" => $na, "type" => $v))->find();
     *                             if ($jiansuo) {
     *                                 $sdfds = array();
     *                                 $sdfds['market'] = $market;
     *                                 $sdfds['price'] = $sta;
     *                                 $sdfds['num'] = 0;
     *                                 $sdfds['mum'] = 0;
     *                                 $sdfds['type'] = 1;
     *                                 $sdfds['addtime'] = time();
     *                                 $sdfds['status'] = 0;
     *                                 $aa = M("trade_log")->add($sdfds);
     *                                 M("trade_json")->execute("commit");
     *                                 sleep(1);
     *                             }
     *                         }
     *                         if (M("trade_json")->where(array("market" => $market, "addtime" => $na, "type" => $v))->find()) {
     *                             M("trade_json")->where(array("market" => $market, "addtime" => $na, "type" => $v))->save(array("data" => json_encode($d)));
     *                             M("market")->where(array('name' => $market))->save($newd);
     *                         } else {
     *                             M("market")->where(array('name' => $market))->save($newd);
     *                             M("trade_json")->add(array("market" => $market, "data" => json_encode($d), "addtime" => $na, "type" => $v));
     *                             M("trade_json")->execute("commit");
     *                             M("trade_json")->where(array("market" => $market, "data" => "", "type" => $v))->delete();
     *                             M("trade_json")->execute("commit");
     *                         }
     *                     } else {
     *                         M("trade_json")->add(array("market" => $market, "data" => "", "addtime" => $na, "type" => $v));
     *                         M("trade_json")->execute("commit");
     *                     }
     *                 }
     *             }
     *         }
     *         return "计算成功!";
     *     }
     * */



}

