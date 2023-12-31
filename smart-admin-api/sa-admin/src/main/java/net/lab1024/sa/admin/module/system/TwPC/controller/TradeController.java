package net.lab1024.sa.admin.module.system.TwPC.controller;

import net.lab1024.sa.common.common.util.CommonUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 行情
 */
@RestController
@RequestMapping("/api/pc/trade")
public class TradeController {
    //api接口基础地址()
    public String basePath = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=";

    /**
     * 获取当前最新价格
     * 表 market
     * 参数：symbol  格式为： xxx/xxx   例如：MBN/USDT
     * 大致逻辑：
     *      1.参数值为 MBN/USDT 时 查询本地，不是 则调取接口
     *      2.为 MBN/USDT 的情况：查询select new_price,min_price,max_price,faxingjia,volume from market where name='mbn_usdt'(参数转小写，且/换为_)
     *          取出来的现价 加上一个随机数（ 0.001 * rand(1, 9) 也就是 0.001到0.009），拼接参数并返回
     *      3.调取接口的情况：参数 去除/ 然后转小写
     *                       拼接请求
     *                       执行后读取请求并拼接参数返回
     * 参考代码：
     *      //获取当前最新价格
     *     public function getcoinprice($symbol = null)
     *     {
     *      （调取 api）
     *             $arr = explode('/', $symbol);(参数：symbol 使用 / 分割，数组命名为：arr)
     *             $coinname = strtolower($arr[0]) . strtolower($arr[1]); （arr 取第一个和第二个 分别转小写后 ，字符串命名为：coinname）
     *             $url = $this->base_path . $coinname;（静态变量   basePath 拼接） java代码：url=basePath+coinname（字符串拼接）
     *             $result = $this->get_maket_api($url);（调取api GET 得到返回值）
     *             $pdata = $result['data'][0];
     *             $open = $pdata['open'];//开盘价
     *             $close = $pdata['close'];//现价
     *             $lowhig = $close - $open; //涨跌
     *             $change = round(($lowhig / $open * 100), 2); //涨跌幅
     *             $data['code'] = 1;
     *             $data['price'] = $close;
     *             $data['change'] = $change;
     *             $data['high'] = $pdata['high'];//最高
     *             $data['low'] = $pdata['low'];//最高
     *             $data['amount'] = $pdata['amount'];//量
     *             $this->ajaxReturn($data);
     *
     *     }
     *
     * */

    //btc，eth，eos，doge，bch，ltc，iota，fil，flow，jst，itc，ht
    public static void main(String[] args) {
        String str = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=btcusdt";
        Map<String, Object> stringObjectMap = CommonUtil.doGet(str, null);
        System.out.println(stringObjectMap);
    }

    /**
     * 获取当前最新价格 （和上面逻辑基本相同 返回参数少点，先保留）
     * 表 market
     * 参数：symbol  格式为： xxx/xxx   例如：MBN/USDT
     * 参考代码：
     *      //获取当前最新价格
     *     public function getnewprice($symbol = null)
     *     {
     *
     *             $arr = explode('/', $symbol);
     *             $coinname = strtolower($arr[0]) . strtolower($arr[1]);
     *             $url = $this->base_path . $coinname;（静态变量   basePath 拼接）
     *             $result = $this->get_maket_api($url);
     *             $pdata = $result['data'][0];
     *             $price = $pdata['close'];
     *             $data['code'] = 1;
     *             $data['price'] = $price;
     *             $this->ajaxReturn($data);
     *         }
     *
     * */


    /**
     * 获取5/10条卖出记录或者 获取5/10条购买记录
     * 表 trade
     * 参数：symbol  格式为： xxx/xxx   例如：MBN/USDT
     *       type 1 为卖出记录 2 为购买记录（本地情况）
     *       direction buy 卖出  sell  购买（调取api情况）
     *       size 长度 5或者 10
     * 大致逻辑：
     *         1.参数值为 MBN/USDT 时 查询本地，不是 则调取接口
     *         2.为 MBN/USDT 的情况：查询select trade where market='mbn_usdt'(参数转小写，且/换为_) order by  id desc  limit  20或者40（参数 size*4）
     *              遍历结果 只取传入type值的对象  返回list
     *         3.调取接口的情况：参数symbol 去除/ 然后转小写 结果为 变量名：coinname
     *                            拼接请求  "https://api.huobi.pro/market/history/trade?symbol=" +$coinname + "&size=20或者40（参数 size*4）"
     *                            执行后读取请求并遍历结果 只取传入direction值的对象  返回list
     * 参考代码：
     *      //获取10条卖出记录  （完成）
     *     public function gettradbuyten($symbol = null)
     *     {
     *
     *             $arr = explode('/', $symbol);
     *             $coinname = strtolower($arr[0]) . strtolower($arr[1]);
     *             $url = "https://api.huobi.pro/market/history/trade?symbol=" . $coinname . "&size=40";
     *             $result = $this->get_maket_api($url);
     *             $data = $result['data'];
     *             $ajdata = array();
     *             foreach ($data as $key => $vo) {
     *                 $direction = $vo['data'][0]['direction'];
     *                 if ($direction == "buy") {
     *                     $ajdata[$key]['amount'] = sprintf("%.4f", $vo['data'][0]['amount']);
     *                     $ajdata[$key]['price'] = sprintf("%.4f", $vo['data'][0]['price']);
     *                     $ajdata[$key]['direction'] = $direction;
     *                     $ajdata[$key]['time'] = date('H:i:s', round($vo['ts'] / 1000));
     *                 }
     *             }
     *             $this->ajaxReturn(['code' => 1, 'data' => $ajdata]);
     *     }
     *
     * */


    /**
     * 获取最新买卖记录
     * 表 trade
     * 参数：coinname    例如：MBN
     * 大致逻辑：
     *         1.参数值为 MBN 时 查询本地，不是 则调取接口
     *         2.为 MBN 的情况：查询select trade where market='mbn_usdt'(参数 coinname 转小写，拼接上 _usdt) order by  id desc  limit  20
     *              遍历结果 划分type就行 1和2 都返回  返回两个list
     *         3.调取接口的情况：参数coinname 转小写并拼接上usdt 结果为 变量名：symbol
     *                            拼接请求  "https://api.huobi.pro/market/history/trade?symbol=" +$symbol + "&size=20"  GET
     *                            执行后读取请求并遍历结果 划分direction就行 sell和buy 都返回  返回两个list
     * 参考代码：
     *      //获取10条卖出记录  （完成）
     *     public function gettradbuyten($symbol = null)
     *     {
     *
     *             $arr = explode('/', $symbol);
     *             $coinname = strtolower($arr[0]) . strtolower($arr[1]);
     *             $url = "https://api.huobi.pro/market/history/trade?symbol=" . $coinname . "&size=40";
     *             $result = $this->get_maket_api($url);
     *             $data = $result['data'];
     *             $ajdata = array();
     *             foreach ($data as $key => $vo) {
     *                 $direction = $vo['data'][0]['direction'];
     *                 if ($direction == "buy") {
     *                     $ajdata[$key]['amount'] = sprintf("%.4f", $vo['data'][0]['amount']);
     *                     $ajdata[$key]['price'] = sprintf("%.4f", $vo['data'][0]['price']);
     *                     $ajdata[$key]['direction'] = $direction;
     *                     $ajdata[$key]['time'] = date('H:i:s', round($vo['ts'] / 1000));
     *                 }
     *             }
     *             $this->ajaxReturn(['code' => 1, 'data' => $ajdata]);
     *
     *     }
     *
     * */


    /**
     * 处理单个行情数理（工具方法，下面接口调用后得到的数据需要调用出来） 方法名命名为：processingOneData
     * 参数：list
     * 大致逻辑：
     *          1.取第一条
     *          2.读取必要参数并返回
     * 参考代码：
     *      //处理单个行情数理
     *     public function processing_onedata($list)
     *     {
     *         $price_arr = $list['data'][0];
     *         $open = $price_arr['open'];//开盘价
     *         $close = $price_arr['close'];//现价
     *         $lowhig = $close - $open; //涨跌
     *         $change = round(($lowhig / $open * 100), 2); //涨跌幅
     *         $pdata['open'] = $close;
     *         $pdata['change'] = $change;
     *         return $pdata;
     *     }
     *
     * */


    /**
     *获取主流货币详情
     * 大致逻辑：
     *         1.三个主流币 接口 GET
     *         2.调取完成封装数据返回
     * 参考代码：
     *      //获取主流货币详情
     *     public function get_market_one()
     *     {
     *         $btcapi = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=btcusdt";
     *         $ethapi = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=ethusdt";
     *         $bchapi = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=bchusdt";
     *         $btcresult = $this->get_maket_api($btcapi); （调取 api ）
     *         $ethresult = $this->get_maket_api($ethapi); （调取 api ）
     *         $bchresult = $this->get_maket_api($bchapi); （调取 api ）
     *         $btcdata = $this->processing_onedata($btcresult); （调取 processingOneData ）
     *         $ethdata = $this->processing_onedata($ethresult); （调取 processingOneData ）
     *         $bchdata = $this->processing_onedata($bchresult); （调取 processingOneData ）
     *         $market['btccoin'] = "BTC/USDT";
     *         $market['btcnewprice'] = $btcdata['open'];
     *         $market['btcchange'] = $btcdata['change'];
     *         $market['ethcoin'] = "ETH/USDT";
     *         $market['ethnewprice'] = $ethdata['open'];
     *         $market['ethchange'] = $ethdata['change'];
     *         $market['bchcoin'] = "BCH/USDT";
     *         $market['bchnewprice'] = $bchdata['open'];
     *         $market['bchchange'] = $bchdata['change'];
     *         $market['code'] = 1;
     *         $this->ajaxReturn($market);
     *     }
     *
     * */

    /**
     * 获取行情单个行情数据（api）
     * 参数：coin （币名称） 有：btc，eth，eos，doge，bch，ltc，iota，fil，flow，jst，itc，ht
     * 大致逻辑：
     *         1.参数 coin 拼接上 usdt
     *         2.参数 coin 转大写 拼接上 "/USDT" （拼接返回值用）
     *         3.请求api 读取并拼接参数返回
     *
     * 逻辑代码：
     *        //获取行情单个行情数据
     *     public function obtain($coin)
     *     {
     *         $symbol = $coin . "usdt";
     *         $cname = strtoupper($coin) . "/USDT";
     *         $api = $this->base_path . $symbol;（静态变量   basePath 拼接）
     *         $data = $this->get_maket_api($api);
     *         if ($data['status'] == 'ok') {
     *             $price_arr = $data['data'][0];
     *             $open = $price_arr['open'];//开盘价
     *             $close = $price_arr['close'];//现价
     *             $high = $price_arr['high'];//最高
     *             $low = $price_arr['low'];//最高
     *             $amount = $price_arr['amount'];//量
     *             $lowhig = $close - $open; //涨跌
     *             $change = round(($lowhig / $open * 100), 2); //涨跌幅
     *             $alldata['code'] = 1;
     *             $alldata['cname'] = $cname;
     *             $alldata['open'] = $close;
     *             $alldata['highlow'] = $high . " / " . $low;
     *             $alldata['amount'] = $amount;
     *             $alldata['change'] = $change;
     *             $this->ajaxReturn($alldata);
     *         } else {
     *             $this->ajaxReturn(['code' => 0, 'info' => "error"]);
     *         }
     *     }
     *
     */

    /**
     * 获取行情单个行情数据（本地）
     * 表：market
     * 大致逻辑：
     *         1.查询 select new_price,min_price,max_price,faxingjia,volume from  market where name='mbn_usdt'
     *         3.拼接参数返回
     *
     * 逻辑代码：
     *     //获取行情单个行情数据 本地（完成）
     *     public function obtain_usdz()
     *     {
     *         $symbol = "mbn_usdt";
     *         $mlist = M("market")->where(array('name' => $symbol))->field("new_price,min_price,max_price,faxingjia,volume")->find();
     *         $open = $mlist['min_price'];//开盘价
     *         $close = $mlist['new_price'];// + $num;//现价
     *         $high = $mlist['max_price'];//最高
     *         $low = $mlist['min_price'];//最高
     *         $amount = $mlist['volume'];//最高
     *         $lowhig = $close - $open; //涨跌
     *         $change = round(($lowhig / $open * 100), 2); //涨跌幅
     *         $alldata['code'] = 1;
     *         $alldata['cname'] = "MBN/USDT";
     *         $alldata['open'] = $close;
     *         $alldata['highlow'] = $high . " / " . $low;
     *         $alldata['amount'] = $amount;
     *         $alldata['change'] = $change;
     *         $this->ajaxReturn($alldata);
     *     }
     *
     */


    /**
     * 获取所有获取市场行情
     * 表：ctmarket
     * 大致逻辑：
     *         1.查询 select ctmarket where status=1
     *         3.遍历 调取接口
     *         3.读取接口 拼接平返回 list
     * 逻辑代码：
     *      //获取市场行情
     *       public function getallsymbol()
     *     {
     *         $list = M("ctmarket")->where(array('status' => 1))->field("coinname,id")->select();
     *         foreach ($list as $k => $v) {
     *             $symbol = $v['coinname'] . "usdt";
     *             $cname = strtoupper($v['coinname']) . "/USDT";
     *             $sid = $v['id'];
     *             $api = $this->base_path . $symbol;（静态变量   basePath 拼接）
     *             $data = $this->get_maket_api($api);（请求 GET）
     *             $price_arr = $data['data'][0];
     *             $open = $price_arr['open'];//开盘价
     *             $close = $price_arr['close'];//现价
     *             $change = $close - $open; //涨跌
     *             $change = round(($change / $open * 100), 2); //涨跌幅
     *             $alldata[$k]['sid'] = $sid;
     *             $alldata[$k]['cname'] = $cname;
     *             $alldata[$k]['open'] = $close;
     *             $alldata[$k]['change'] = $change;
     *         }
     *         $this->ajaxReturn(['code' => 1, 'data' => $alldata]);
     *     }
     *
     */


    /**
     * 处理合约页面交易对数据（工具方法  下面会使用） 方法命名为：hyData
     *  大致逻辑：
     *     1.取第一条
     *     2.读取必要参数并返回
     * 逻辑代码：
     *      //处理合约页面交易对数据
     *     public function hydata($list, $cname)
     *     {
     *         $price_arr = $list['data'][0];
     *         $open = $price_arr['open'];//开盘价
     *         $close = $price_arr['close'];//现价
     *         $lowhig = $close - $open; //涨跌
     *         $change = $lowhig / $open * 100;
     *         $change = abs($change);
     *         $change = number_format($change, 2);
     *         $pdata['open'] = $close;
     *         $pdata['cname'] = $cname;
     *         $pdata['change'] = $change;
     *         return $pdata;
     *     }
     *
     */

    /**
     * 合约页面获取所有交易数据
     * 表：ctmarket，
     *  大致逻辑：
     *     1.查询 select coinname,id from  ctmarket where status=1
     *     2.遍历拼接 url 调取 api
     *     3.拼接返回参数 返回list
     * 逻辑代码：
     *      //合约页面获取所有交易数据
     *     public function getallcoin()
     *     {
     *         $where['status'] = 1;
     *         $list = M("ctmarket")->where($where)->field("coinname,id")->select();
     *         foreach ($list as $k => $v) {
     *
     *                 $symbol = $v['coinname'] . "usdt";
     *                 $cname = strtoupper($v['coinname']) . "/USDT";
     *                 $sid = $v['id'];
     *                 $api = $this->base_path . $symbol; （静态变量   basePath 拼接）
     *                 $data = $this->get_maket_api($api); (调取 api GET)
     *                 $result = $this->hydata($data, $cname); (调取 处理方法 hyData)
     *                 $alldata[$k]['sid'] = $sid;
     *                 $alldata[$k]['coin'] = strtoupper($v['coinname']);
     *                 $alldata[$k]['cname'] = $result['cname'];
     *                 $alldata[$k]['symbol'] = $v['coinname'];
     *                 $alldata[$k]['open'] = $result['open'];
     *                 $alldata[$k]['change'] = $result['change'];
     *
     *         }
     *         $this->ajaxReturn(['code' => 1, 'data' => $alldata]);
     *     }
     *
     */


    /**
     * 获取交易对数据
     * 表 market
     * 参数：coinname
     * 大致逻辑：
     *      1.参数值为 MBN 时 查询本地，不是 则调取接口
     *      2.为 MBN 的情况：查询select new_price,min_price,max_price,faxingjia,volume from market where name='mbn_usdt'
     *          取出来的现价 加上一个随机数（ 0.001 * rand(1, 9) 也就是 0.001到0.009），拼接参数并返回
     *      3.调取接口的情况：参数coinname 转小写拼接拼接上 usdt
     *                       拼接请求
     *                       执行后读取请求并拼接参数返回
     * 参考代码：
     *      //获取当前最新价格
     *      public function getcoin_data()
     *     {
     *         $coinname = trim($_POST['coinname']);
     *
     *             $symbol = strtolower($coinname) . "usdt";
     *             $api = $this->base_path . $symbol;
     *             $data = $this->get_maket_api($api);
     *             $price_arr = $data['data'][0];
     *             $open = $price_arr['open'];//开盘价
     *             $close = $price_arr['close'];//现价
     *             $lowhig = $close - $open; //涨跌
     *             $change = round(($lowhig / $open * 100), 2); //涨跌幅
     *             $high = $price_arr['high'];
     *             $low = $price_arr['low'];
     *             $amount = $price_arr['amount'];
     *             $result['close'] = $close;
     *             $result['change'] = $change;
     *             $result['high'] = $high;
     *             $result['low'] = $low;
     *             $result['amount'] = sprintf("%.6f", $amount);
     *             $result['code'] = 1;
     *             $this->ajaxReturn($result);
     *
     *     }
     *
     * */


}

