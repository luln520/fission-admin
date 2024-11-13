package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddressDetail;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMyzc;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRecharge;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAddressService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMyzcService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwRechargeService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCoinService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 钱包
 */
@RestController
@RequestMapping("/api/pc/finance")
@Api(tags = {AdminSwaggerTagConst.PC.PC_FINANCE})
@Slf4j
public class PcFinanceController {

    @Autowired
    private TwMyzcService twMyzcService;

    @Autowired
    private TwRechargeService twRechargeService;
    @Autowired
    private TwUserCoinService twUserCoinService;
    @Autowired
    private TwAddressService twAddressService;
    /**
     * 提币列表
     * 表：myzc
     * 参数：id（用户id）
     * select myzc where userid=? order by id desc limit 15
     */
    @GetMapping("/listPcpage")
    @ApiOperation(value = "提币列表")
    @NoNeedLogin
    public ResponseDTO<List<TwMyzc>> listPcpage(@RequestParam int uid) {
        return ResponseDTO.ok(twMyzcService.listPcpage(uid));
    }


    /**
     * 充币列表
     * 表：recharge
     * 参数：id（用户id）
     * select recharge where uid=? order by id desc limit 15
     */
    @GetMapping("/listRecharge")
    @ApiOperation(value = "充币列表")
    @NoNeedLogin
    public ResponseDTO<List<TwAddressDetail>> listRecharge(@RequestParam int uid) {
        return ResponseDTO.ok(twAddressService.listRecharge(uid));
    }


    /**
     * 申请提币
     * 开启事务
     * 表：user,coin,user_coin,myzc,bill
     * 参数：uid（用户id）cid（币种 id） address( 提币地址) num 提现数量
     * 大致逻辑：
     *      1、查询用户  判断用户是否条件满足（是否实名，是否被禁提现） user where id=?
     *      2、查询币的配置  判断是否满足提现要求 （最小额度，最大额度） coin where id=?
     *      3、查询用户余额 判断余额是否满足    user_coin where userid=？
     *      4、都满足 减少用户余额
     *      5、写入申请提现表   myzc
     *      6、写入操作日志  bill
     *      7、任何一步失败 返回失败 回滚事务
     * 参考代码：
     *      //申请提币
     *     public function tbhandle()
     *     {
     *         $uinfo = M("user")->where(array('id' => $uid))->field("id,rzstatus,username,txstate")->find();
     *         $cinfo = M("coin")->where(array('id' => $cid))->find();
     *         $coinname = $cinfo['name'];
     *         $minfo = M("user_coin")->where(array('userid' => $uid))->find();
     *         if ($uinfo['rzstatus'] != 2) {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('请先完成实名认证')]);
     *         }
     *         if ($uinfo['txstate'] == 2) {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('已禁止提现')]);
     *         }
     *         if ($num < $cinfo['txminnum']) {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('不能低于最小提币值')]);
     *         }
     *         if ($num > $cinfo['txmaxnum']) {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('不能高于最大提币值')]);
     *         }
     *         $sxftype = $cinfo['sxftype'];
     *         if ($sxftype == 1) {
     *             $sxf = $num * $cinfo['txsxf'] / 100;
     *         } elseif ($sxftype == 2) {
     *             $sxf = $cinfo['txsxf_n'];
     *         }
     *         if ($sxf <= 0 || $sxf == '') {
     *             $sxf = 0;
     *         }
     *         $tnum = $num - $sxf;
     *         if ($minfo[$coinname] < $num) {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('账户余额不足')]);
     *         }
     *         $dec_re = M("user_coin")->where(array('userid' => $uid))->setDec($coinname, $num);
     *         $data['userid'] = $uid;
     *         $data['username'] = $uinfo['username'];
     *         $data['coinname'] = $cinfo['name'];
     *         $data['num'] = $num;
     *         $data['fee'] = $sxf;
     *         $data['mum'] = $tnum;
     *         $data['address'] = $address;
     *         $data['sort'] = 1;
     *         $data['addtime'] = date("Y-m-d H:i:s", time());
     *         $data['endtime'] = '';
     *         $data['status'] = 1;
     *         $result = M("myzc")->add($data);
     *         //操作日志
     *         $bill['uid'] = $uid;
     *         $bill['username'] = $uinfo['username'];
     *         $bill['num'] = $num;
     *         $bill['coinname'] = $cinfo['name'];
     *         $bill['afternum'] = $minfo[$coinname] - $num;
     *         $bill['type'] = 2;
     *         $bill['addtime'] = date("Y-m-d H:i:s", time());
     *         $bill['st'] = 2;
     *         $bill['remark'] = "提币申请";
     *         $billre = M("bill")->add($bill);
     *         if ($result && $dec_re && $billre) {
     *             $this->ajaxReturn(['code' => 1, 'info' => L('提交成功')]);
     *         } else {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('提交失败')]);
     *         }
     *     }
     *
     *     uid（用户id）cid（币种 id） address( 提币地址) num 提现数量
     */
    @GetMapping("/tbhandle")
    @ApiOperation(value = "申请提币")
    @NoNeedLogin
    public ResponseDTO tbhandle(@RequestParam int uid,
                                @RequestParam int cid,
                                @RequestParam String address,
                                @RequestParam BigDecimal num,
                                @RequestParam BigDecimal currenyNum,
                                @RequestParam String currenyName,
                                @RequestParam String language) {
        ResponseDTO tbhandle = twMyzcService.tbhandle(uid, cid, address, num, currenyNum,currenyName,language);
        log.info("客户提币返回参数ResponseDTO{}",tbhandle);
        return tbhandle;
    }

    /**
     * 上传转账号凭证
     * 表：user,recharge
     * 参数：id（用户id）coinname（币种 名称） czaddress( 地址) payimg （支付图片）  zznum（充值数量） czline（充值网络）
     * 大致逻辑：
     *      1、查询用户 写入recharge表时需要user信息 user where id=?
     *      2、拼接参数  写入 recharge
     * 参考代码：
     *     //上传转账号凭证 (完成)
     *     public function paycoin()
     *     {
     *         $uid = userid();
     *         $uinfo = M("user")->where(array('id' => $uid))->field("id,username")->find();
     *         $zznum = trim(I('post.zznum'));
     *         $payimg = trim(I('post.payimg'));
     *         $coinname = trim(I('post.coinname'));
     *         $czaddress = trim(I('post.czaddress'));
     *         $czline = trim(I('post.czline'));
     *         $data['uid'] = $uid;
     *         $data['username'] = $uinfo['username'];
     *         $data['coin'] = strtoupper($coinname);
     *         $data['num'] = $zznum;
     *         $data['addtime'] = date("Y-m-d H:i:s", time());
     *         $data['updatetime'] = '';
     *         $data['status'] = 1;
     *         $data['payimg'] = $payimg;
     *         $data['address'] = $czaddress;
     *         $data['czline'] = $czline;
     *         $data['msg'] = '';
     *         $result = M("recharge")->add($data);
     *         if ($result) {
     *             $this->ajaxReturn(['code' => 1, 'info' => L('凭证提交成功')]);
     *         } else {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('凭证提交失败')]);
     *         }
     *     }
     *
     *     参数：id（用户id）coinname（币种 名称） czaddress( 地址) payimg （支付图片）  zznum（充值数量） czline（充值网络）
     */
    @GetMapping("/paycoin")
    @ApiOperation(value = "上传转账号凭证")
    @NoNeedLogin
    public ResponseDTO paycoin(@RequestParam int uid,
                               @RequestParam String coinname,
                               @RequestParam String czaddress,
                               @RequestParam String  payimg,
                               @RequestParam String  language,
                               @RequestParam BigDecimal zznum,
                               @RequestParam BigDecimal currenyNum,
                               @RequestParam String currenyName,
                               @RequestParam String czline,
                               @RequestParam int companyId
                                ) {
        ResponseDTO paycoin = twRechargeService.paycoin(uid, coinname, czaddress, payimg, zznum, currenyNum,currenyName,czline, language, companyId);
        log.info("客户充值返回参数ResponseDTO{}",paycoin);
        return paycoin;
    }


    /**
     * 获取折合资产
     * 表：user_coin
     * 参数：id(用户id)
     * select user_coin where userid=?
     * 大致逻辑：
     *         1.相加计算用户资产
     * 参考代码：
     *       $minfo = M("user_coin")->where(array('userid' => $uid))->find();
     *        $usdt = $minfo['usdt'] + $minfo['usdtd'];
     *        $this->ajaxReturn(['code' => 1, 'allzhehe' => $usdt]);
     *
     */
    @GetMapping("/userCoin")
    @ApiOperation(value = "获取折合资产")
    @NoNeedLogin
    public ResponseDTO<TwUserCoin> userCoin(@RequestParam int uid) {
        return ResponseDTO.ok(twUserCoinService.userCoin(uid));
    }


    /**
     * 获取单个币种资产(usdz) （本地数据库查询计算）
     * 表：user_coin，market
     * 参数：id(用户id)
     * select user_coin where userid=?
     * select market where name='usdz_usdt'
     * 大致逻辑：
     *         1.相加且相乘计算折合
     * 参考代码：
     *         $wallinfo = M("user_coin")->where(array('userid' => $uid))->find();
     *         $where['name'] = "usdz_usdt";
     *         $marketinfo = M("market")->where($where)->field("new_price")->find();
     *         $usdzusdt = $marketinfo['new_price'];
     *         $re['num'] = $wallinfo['usdz'];
     *         $re['numd'] = $wallinfo['usdzd'];
     *         $zhehe = $wallinfo['usdz'] * $usdzusdt + $wallinfo['usdzd'] * $usdzusdt;
     *         $re['zhe'] = $zhehe;
     *         $re['code'] = 1;
     *         $this->ajaxReturn($re);
     *
     */


    /**
     * 获取单个币种资产(usdt) （本地数据库查询计算）
     * 表：user_coin
     * 参数：id(用户id)
     * select user_coin where userid=?
     * 大致逻辑：
     *         1.相加计算
     * 参考代码：
     *      //获取单个币种资产(usdt)
     *     public function getmoneyusdt($uid)
     *     {
     *         $wallinfo = M("user_coin")->where(array('userid' => $uid))->find();
     *         $re['num'] = $wallinfo['usdt'];
     *         $re['numd'] = $wallinfo['usdtd'];
     *         $zhehe = $wallinfo['usdt'] + $wallinfo['usdtd'];
     *         $re['zhe'] = $zhehe;
     *         $re['code'] = 1;
     *         $this->ajaxReturn($re);
     *     }
     *
     */


    /**
     * 获取单币种单价
     * 表：market
     * 参数： coinname（币名称）
     * select  new_price,min_price,max_price,faxingjia,volume from market where name=?
     * 大致逻辑：
     *         1.coinname 为 USDZ 时，查询本地 计算并返回
     *         2.相反 调取api   计算并返回
     * 参考代码：
     *     //获取单币种单价 （完成）
     *     public function getnewprice($coinname)
     *     {
     *         if ($coinname == "USDZ") {(查询本地)
     *             $symbol = "usdz_usdt";
     *             $mlist = M("market")->where(array('name' => $symbol))->field("new_price,min_price,max_price,faxingjia,volume")->find();
     *             $open = $mlist['min_price'];//开盘价
     *             $close = $mlist['new_price'];//现价
     *             $lowhig = $close - $open; //涨跌
     *             $change = round(($lowhig / $open * 100), 2); //涨跌幅
     *         } else {（调取api）
     *             $lowcoin = strtolower($coinname);（转小写）
     *             $symbol = $lowcoin . 'usdt';（后面拼接上 usdt）
     *             $coinapi = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=" . $symbol;(拼接 api url)
     *             $result = $this->get_maket_api($coinapi);（调取api）
     *             $price_arr = $result['data'][0];
     *             $open = $price_arr['open'];//开盘价
     *             $close = $price_arr['close'];//现价
     *             $lowhig = $close - $open; //涨跌
     *             $change = round(($lowhig / $open * 100), 2); //涨跌幅
     *         }
     *         $data['code'] = 1;
     *         $data['newprice'] = $close;
     *         $data['change'] = $change;
     *         $this->ajaxReturn($data);
     *     }
     *
     */


    /**
     * 获取单个币种资产（api返回计算）
     * 表：user_coin
     * 参数：id(用户id) coinname （币名） coinname：ogo,ht,itc,jst,flow,shib,fil,iotx,xrp,trx,ltc,bch,doge,eos,eth,btc
     * select user_coin where userid=?
     * 大致逻辑：
     *         1.相加且相乘计算折合
     * 参考代码：
     *     //获取单个币种资产
     *     public function getmoneyogo($coinname,$uid)
     *     {
     *         $wallinfo = M("user_coin")->where(array('userid' => $uid))->find();
     *         $coinapi = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=".$coinname.'usdt'; (拼接上 usdt)
     *         $result = $this->get_maket_api($coinapi);(调取api)
     *         $price_arr = $result['data'][0];
     *         $usdt_price = $price_arr['close'];
     *         $re['num'] = $wallinfo[$coinname];
     *         $re['numd'] = $wallinfo[$coinname.'d'];（这里是拼接上 d）
     *         $zhehe = $wallinfo[$coinname] * $usdt_price + $wallinfo[($coinname.'d')（这里是拼接上 d）] * $usdt_price;
     *         $re['zhe'] = $zhehe;
     *         $re['code'] = 1;
     *         $this->ajaxReturn($re);
     *     }
     */


}

