package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHysetting;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHyorderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHysettingService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.HyorderOneRes;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 合约
 */
@RestController
@RequestMapping("/api/pc/contract")
@Api(tags = {AdminSwaggerTagConst.PC.PC_CONTRACT})
public class PcContractController {

    @Autowired
    private TwHyorderService twHyorderService;

    @Autowired
    private TwHysettingService twHysettingService;
    /**
     *合约倒计时
     * 表：hyorder
     * 参数：id （hyorder的id）
     * 大致逻辑：
     *         1.查询 hyorder  where id=?
     *         2.补充需要返回的字段
     *         3.判断是否结算
     *         4.没有结算 status=1
     *                     然后 判断时间是否已经过了  过了直接返回
     *                          没有过                 调取 api 然后 拼接参数返回  url：https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol= 拼接上变量（coinname）
     *         5.已经结算： 直接拼接参数并返回
     *
     * 参考代码：
     *      //合约倒计时
     *     public function get_hyorder_one($id)
     *     {
     *         $orderinfo = M("hyorder")->where(array('id' => $id))->find();
     *         $data['clear'] = 0;
     *         $ajax['id'] = $orderinfo['id'];
     *         if ($orderinfo['hyzd'] == 1) {
     *             $ajax['timer_type'] = "买涨";
     *         } else {
     *             $ajax['timer_type'] ="买跌";
     *         }
     *         $ajax['coinname'] = $orderinfo['coinname'];
     *         $ajax['buyprice'] = $orderinfo['buyprice'];
     *         $ajax['time'] = $orderinfo['time'] * 60;
     *         $ajax['timer_buynum'] = $orderinfo['num'];
     *         $ajax['timer_buyprice'] = $orderinfo['buyprice'];
     *         $ajax['hyzd'] = $orderinfo['hyzd'];
     *         $data['ajax'] = $ajax;
     *         if ($orderinfo['status'] == 1) {
     *             $endtime = $orderinfo['selltime'];
     *             $t = $endtime - time();
     *             if ($t <= 0) {
     *                 //表示已结算
     *                 $data['statusstr'] = "正在结算中";
     *                 $data['code'] = 2;
     *                 $this->ajaxReturn($data);
     *             } else {
     *                 //获取当前交易对的单价
     *                 $coinarr = explode('/', $orderinfo['coinname']);
     *                 $symbol = strtolower($coinarr[0] . $coinarr[1]);
     *                 $coinapi = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=" . $symbol;
     *                 $result = $this->get_maket_api($coinapi);
     *                 $price_arr = $result['data'][0];
     *                 $close = $price_arr['close'];//现价
     *                 $data['code'] = 1;
     *                 $data['time'] = $t;
     *                 $data['timer_newprice'] = $close;
     *                 $this->ajaxReturn($data);
     *             }
     *         } else {
     *             //表示已经结算，则显示盈亏即可
     *             $data['statusstr'] = $orderinfo['ploss'];
     *             $data['code'] = 2;
     *             $data['clear'] = 1;
     *             $this->ajaxReturn($data);
     *         }
     *     }
     */
    @GetMapping("/getHyorderOne")
    @ResponseBody
    @NoNeedLogin
    @ApiOperation(value = "合约倒计时")
    public ResponseDTO<HyorderOneRes> getHyorderOne(@RequestParam int id) throws IOException {
        return twHyorderService.getHyorderOne(id);
    }


    /**
     *获取休验合约记录
     * 表：tyhyorder
     * 参数：uid （用户的 id）
     * 大致逻辑：
     *         1.查询 tyhyorder  where uid=? order by id desc
     *         2.遍历 补充需要返回的字段 然后返回list
     * 参考代码：
     *     //获取休验合约记录
     *     public function gethyorder_ty($uid)
     *     {
     *         $list = M("tyhyorder")->where(array('uid' => $uid))->order("id desc")->select();
     *         $data = array();
     *         foreach ($list as $k => $v) {
     *             $data[$k]['id'] = $v['id'];
     *             $data[$k]['coinanme'] = $v['coinname'];
     *             $data[$k]['num'] = $v['num'];
     *             $data[$k]['buyprice'] = $v['buyprice'];
     *             $data[$k]['buytime'] = $v['buytime'];
     *             $endtime = strtotime($v['selltime']);
     *             $t = $endtime - time();
     *             if ($t <= 0) {
     *                 $t = 0;
     *             }
     *             $data[$k]['t'] = $t;
     *         }
     *         $this->ajaxReturn(['code' => 1, 'data' => $data]);
     *     }
     */


    /**
     *获取合约记录
     * 表：hyorder
     * 参数：uid （用户的 id）
     * 大致逻辑：
     *         1.查询 hyorder  where uid=? where status=1 order by id desc
     *         2.遍历 补充需要返回的字段 然后返回list
     * 参考代码：
     *     //获取合约记录
     *     public function gethyorder($uid)
     *     {
     *         $list = M("hyorder")->where(array('uid' => $uid, "status" => 1))->order("id desc")->select();
     *         $data = array();
     *         foreach ($list as $k => $v) {
     *             $data[$k]['id'] = $v['id'];
     *             $data[$k]['coinanme'] = $v['coinname'];
     *             $data[$k]['num'] = $v['num'];
     *             if ($v['status'] == 1) {
     *                 $data[$k]['statusstr'] = L("待结算");
     *             } elseif ($v['status'] == 2) {
     *                 $data[$k]['statusstr'] = L("已结算");
     *             } elseif ($v['status'] == 3) {
     *                 $data[$k]['statusstr'] = L("无效结算");
     *             }
     *             if ($v['hyzd'] == 1) {
     *                 $data[$k]['hyzdstr'] = L("买涨");
     *             } elseif ($v['hyzd'] == 2) {
     *                 $data[$k]['hyzdstr'] = L("买跌");
     *             }
     *             $data[$k]['buyprice'] = $v['buyprice'];
     *             $data[$k]['buytime'] = $v['buytime'];
     *             $endtime = strtotime($v['selltime']);
     *             $t = $endtime - time();
     *             if ($t <= 0) {
     *                 $t = 0;
     *             }
     *             $data[$k]['t'] = $t;
     *         }
     *         $this->ajaxReturn(['code' => 1, 'data' => $data]);
     *     }
     */
    @GetMapping("/gethyorder")
    @ResponseBody
    @NoNeedLogin
    @ApiOperation(value = "获取合约记录")
    public ResponseDTO<List<TwHyorder>> gethyorder(@RequestParam int uid) {
        return twHyorderService.gethyorder(uid);
    }



    /**
     *合约体验订单
     * 表：tyhyorder
     * 参数：uid （用户的 id）
     * 大致逻辑：
     *         1.查询 hyorder  where uid=? where status=1 order by id desc
     *         2.直接返回list
     * 参考代码：
     *       //合约体验订单
     *     public function contract_ty($uid)
     *     {
     *         $hylist = M("tyhyorder")->where(array('uid' => $uid))->order("id desc")->select();
     *         $this->assign("list", $hylist);
     *     }
     */

    /**
     *合约未平仓、已平仓订单
     * 表：hyorder
     * 参数：uid （用户的 id） type 1 或者 2
     * 大致逻辑：
     *         1.type==1:查询 hyorder  where uid=? where status=1 order by id desc
     *         2.type==2:查询 hyorder  where uid=? where status!=1 order by id desc
     * 参考代码：
     *       //合约体验订单
     *     public function contract_ty($uid)
     *     {
     *         $hylist = M("tyhyorder")->where(array('uid' => $uid))->order("id desc")->select();
     *         $this->assign("list", $hylist);
     *     }
     */
    @GetMapping("/contractTy")
    @ResponseBody
    @NoNeedLogin
    @ApiOperation(value = "合约未平仓、已平仓订单")
    public ResponseDTO<List<TwHyorder>> contractTy(@RequestParam int uid ,@RequestParam int type) {
        return twHyorderService.contractTy(uid,type);
    }

    /**
     *购买合约详情
     * 表：hyorder
     * 参数：uid （用户的 id） id(hyorder的id)
     * 大致逻辑：
     *         1.查询 hyorder  where uid=? and id=?
     *         2.直接返回 对象
     * 参考代码：
     *      //购买合约详情
     *     public function cbillinfo($id, $uid)
     *     {
     *         $info = M("hyorder")->where(array('uid' => $uid, 'id' => $id))->find();
     *         $this->assign('info', $info);
     *     }
     */
    @GetMapping("/cbillinfo")
    @ResponseBody
    @NoNeedLogin
    @ApiOperation(value = "购买合约详情")
    public ResponseDTO<TwHyorder> cbillinfo(@RequestParam int uid ,@RequestParam int id) {
        return twHyorderService.cbillinfo(uid,id);
    }

    /**
     *获取合约设置
     * 表：hysetting
     *  hysetting  where  id=1
     */
    @GetMapping("/hysetInfo")
    @ResponseBody
    @NoNeedLogin
    @ApiOperation(value = "获取合约设置")
    public ResponseDTO<TwHysetting> hysetInfo() {
        return ResponseDTO.ok(twHysettingService.hysettingId());
    }

    /**
     *合约购买记录
     * 表：hyorder
     * 参数：uid （用户的 id）
     * 大致逻辑：
     *         1.查询 hyorder  where uid=? order by id desc
     *         2.直接返回 list
     * 参考代码：
     *      //购买合约详情
     *     public function cbillinfo($id, $uid)
     *     {
     *         $info = M("hyorder")->where(array('uid' => $uid, 'id' => $id))->find();
     *         $this->assign('info', $info);
     *     }
     */
    @GetMapping("/cbillList")
    @ResponseBody
    @NoNeedLogin
    @ApiOperation(value = "合约购买记录")
    public ResponseDTO<List<TwHyorder>> cbillList(@RequestParam int uid) {
        return twHyorderService.cbillList(uid);
    }

    /**
     *体验秒合约建仓
     * 表：user，hysetting，tyhyorder
     * 参数：uid （用户的 id） ctime（结算时间） ctzed （投资额度） ccoinname (交易对) ctzfx(合约涨跌1买涨2买跌) cykbl（盈亏比例）
     * 大致逻辑：
     *         1.查询用户信息： user  where id=?
     *         2.查询合约配置:  hysetting where id =1
     *         3.查询用户上级邀请 user  where id=(逻辑1的结果取invit_1的值)
     *         4.判断用户条件是否满足 调取api 基础url：https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=拼接上变量（symbol）
     *         5.拼接体验订单参数 并存入 tyhyorder
     *         6.扣除体验金 update user set money=？ where id=?
     * 参考代码：
     *       //体验秒合约建仓
     *     public function ty_creatorder($uid, $ctime, $ctzed, $ccoinname, $ctzfx, $cykbl)
     *     {
     *         $uinfo = M("user")->where(array('id' => $uid))->find();
     *         //获取合约手续费比例
     *         $setting = M("hysetting")->where(array('id' => 1))->find();
     *         //查询用户上级邀请
     *         $puid = $uinfo['invit_1'];
     *         $puser = M("user")->where(array('id' => $puid))->find();
     *         if ($uinfo['rzstatus'] != 2) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('请先完成实名认证')]);
     *         }
     *         if ($ctime <= 0) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('请选择结算时间')]);
     *         }
     *         if ($ctzed <= 0) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('请选择投资额度')]);
     *         }
     *         if ($ccoinname == '') {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('缺少重要参数')]);
     *         }
     *         if ($ctzfx <= 0) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('缺少重要参数')]);
     *         }
     *         //获取会员资产
     *         $usdtnum = $uinfo['money'];
     *         $hymin = $setting['hy_min'];
     *         if ($hymin > $ctzed) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('不能小于最低投资额度')]);
     *         }
     *         $sxf = $setting['hy_sxf'];
     *         $tmoney = $ctzed + $ctzed * $sxf / 100;
     *         if ($tmoney > $usdtnum) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('体验金余额不足')]);
     *         }
     *         //获取当前交易对的单价
     *         $coinarr = explode('/', $ccoinname);
     *         $symbol = strtolower($coinarr[0] . $coinarr[1]);
     *         $coinapi = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=" . $symbol;
     *         $result = $this->get_maket_api($coinapi);
     *         $price_arr = $result['data'][0];
     *         $close = $price_arr['close'];//现价
     *         //创建订单
     *         $odata['uid'] = $uid;
     *         $odata['username'] = $uinfo['username'];
     *         $odata['num'] = $ctzed;
     *         $odata['hybl'] = $cykbl;
     *         $odata['hyzd'] = $ctzfx;
     *         $odata['coinname'] = $ccoinname;
     *         $odata['status'] = 1;
     *         $odata['is_win'] = 0;
     *         $odata['buytime'] = date("Y-m-d H:i:s", time());
     *         $odata['selltime'] = date("Y-m-d H:i:s", (time() + $ctime * 60));
     *         $odata['intselltime'] = time() + $ctime * 60;
     *         $odata['buyprice'] = $close;
     *         $odata['sellprice'] = '';
     *         $odata['ploss'] = 0;
     *         $odata['time'] = $ctime;
     *         $odata['kongyk'] = 0;
     *         if ($puser['invit'] == '') {
     *             $puser['invit'] = 0;
     *         }
     *         $odata['invit'] = $puser['invit'];
     *         $order = M("tyhyorder")->add($odata);
     *         //扣除体验金
     *         $decre = M("user")->where(array('id' => $uid))->setDec('money', $tmoney);
     *         if ($order && $decre) {
     *             $this->ajaxReturn(['code' => 1, 'msg' => L('体验订单建仓成功')]);
     *         } else {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('体验订单建仓失败')]);
     *         }
     *     }
     *
     */


    /**
     *秒合约建仓
     * 表：user，hysetting，user_coin，hyorder，bill
     * 参数：uid （用户的 id） ctime（结算时间） ctzed （投资额度） ccoinname (交易对) ctzfx(合约涨跌1买涨2买跌) cykbl（盈亏比例）
     * 大致逻辑：
     *         1.查询用户信息和资产： user  where id=?      user_coin where userid=?
     *         2.查询合约配置:  hysetting where id =1
     *         3.查询用户上级邀请 user  where id=(逻辑1的结果取invit_1的值)
     *         4.判断用户条件是否满足 调取api 基础url：https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=拼接上变量（symbol）
     *         5.拼接订单参数 并存入 hyorder
     *         6.扣除USDT额度 update user_coin set usdt=？ where userid=?
     *         7.创建财务日志 写入 bill表
     * 参考代码：
     *     //秒合约建仓
     *     public function creatorder($uid, $ctime, $ctzed, $ccoinname, $ctzfx, $cykbl)
     *     {
     *         $uinfo = M("user")->where(array('id' => $uid))->find();
     *         //获取会员资产
     *         $minfo = M("user_coin")->where(array('userid' => $uid))->find();
     *         $usdtnum = $minfo['usdt'];
     *         //获取合约手续费比例
     *         $setting = M("hysetting")->where(array('id' => 1))->find();
     *         $puid = $uinfo['invit_1'];
     *         $puser = M("user")->where(array('id' => $puid))->find();
     *         if ($uinfo['rzstatus'] != 2) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('请先完成实名认证')]);
     *         }
     *         $hymin = $setting['hy_min'];
     *         if ($hymin > $ctzed) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('不能小于最低投资额度')]);
     *         }
     *         if ($uinfo['buy_on'] == "2") {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('您的账户已被禁止交易，请联系客服')]);
     *         }
     *         $sxf = $setting['hy_sxf'];
     *         $tmoney = $ctzed + $ctzed * $sxf / 100;
     *         if ($tmoney > $usdtnum) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('USDT余额不足')]);
     *         }
     *         //获取当前交易对的单价
     *         $coinarr = explode('/', $ccoinname);
     *         $symbol = strtolower($coinarr[0] . $coinarr[1]);
     *         $coinapi = "https://api.huobi.pro/market/history/kline?period=1day&size=1&symbol=" . $symbol;
     *         $result = $this->get_maket_api($coinapi);
     *         $price_arr = $result['data'][0];
     *         $close = $price_arr['close'];//现价
     *         //创建订单
     *         $odata['uid'] = $uid;
     *         $odata['username'] = $uinfo['username'];
     *         $odata['num'] = $ctzed;
     *         $odata['hybl'] = $cykbl;
     *         $odata['hyzd'] = $ctzfx;
     *         $odata['buy_orblance'] = $minfo['usdt'] - $tmoney;
     *         $odata['coinname'] = $ccoinname;
     *         $odata['status'] = 1;
     *         $odata['is_win'] = 0;
     *         $odata['buytime'] = date("Y-m-d H:i:s", time());
     *         $odata['selltime'] = date("Y-m-d H:i:s", (time() + $ctime * 60));
     *         $odata['intselltime'] = time() + $ctime * 60;
     *         $odata['buyprice'] = $close;
     *         $odata['sellprice'] = '';
     *         $odata['ploss'] = 0;
     *         $odata['time'] = $ctime;
     *         $odata['kongyk'] = 0;
     *         if ($puser['invit'] == '') {
     *             $puser['invit'] = 0;
     *         }
     *         $odata['invit'] = $puser['invit'];
     *         $order = M("hyorder")->add($odata);
     *         //扣除USDT额度
     *         $decre = M("user_coin")->where(array('userid' => $uid))->setDec('usdt', $tmoney);
     *         //创建财务日志
     *         $bill['uid'] = $uid;
     *         $bill['username'] = $uinfo['username'];
     *         $bill['num'] = $ctzed;
     *         $bill['coinname'] = "usdt";
     *         $bill['afternum'] = $minfo['usdt'] - $ctzed;
     *         $bill['type'] = 3;
     *         $bill['addtime'] = date("Y-m-d H:i:s", time());
     *         $bill['st'] = 2;
     *         $bill['remark'] = L('购买') . $ccoinname . L('秒合约');
     *         $billre = M("bill")->add($bill);
     *         if ($order && $decre && $billre) {
     *             $this->ajaxReturn(['code' => 1, 'id' => $order, 'time' => $ctime, 'msg' => L('建仓成功')]);
     *         } else {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('建仓失败')]);
     *         }
     *     }
     *
     *     uid （用户的 id） ctime（结算时间） ctzed （投资额度） ccoinname (交易对) ctzfx(合约涨跌1买涨2买跌) cykbl（盈亏比例）
     */
    @GetMapping("/creatorder")
    @ResponseBody
    @NoNeedLogin
    @ApiOperation(value = "秒合约建仓")
    public ResponseDTO creatorder(@RequestParam int uid,
                                  @RequestParam int ctime,
                                  @RequestParam BigDecimal ctzed,
                                  @RequestParam String ccoinname,
                                  @RequestParam int ctzfx,
                                  @RequestParam BigDecimal cykbl) throws IOException {
        return twHyorderService.creatorder(uid,ctime,ctzed,ccoinname,ctzfx,cykbl);
    }

}

