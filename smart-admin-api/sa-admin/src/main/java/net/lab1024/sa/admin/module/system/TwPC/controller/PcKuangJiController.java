package net.lab1024.sa.admin.module.system.TwPC.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKjorderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKjprofitService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKuangjiService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.TwPCKjprofitVo;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * 矿机
 */
@RestController
@RequestMapping("/api/pc/kuangji")
@Api(tags = {AdminSwaggerTagConst.PC.PC_KUANGJI})
public class PcKuangJiController {

    @Autowired
    private TwKuangjiService twKuangjiService;

    @Autowired
    private TwKjorderService twKjorderService;

    @Autowired
    private TwKjprofitService twKjprofitService;



    /**
     * 获取矿机列表
     * 表 kuangji
     * 参数： type （可选参数）  //不要共享
     *  获取全部：select kuangji  where   status=1 and rtype=1 order by id asc
     *  获取单类型（type 区分）：select kuangji  where  type=1  and status=1 and rtype=1 order by id asc
     * */
    @PostMapping("/pcList")
    @ResponseBody
    @ApiOperation(value = "获取矿机列表")
    @NoNeedLogin
    public ResponseDTO<IPage<TwKuangji>> pcList(@Valid @RequestBody PageParam pageParam) {
        return ResponseDTO.ok(twKuangjiService.pcList(pageParam));
    }


    /**
     * 获取用户矿机列表
     * 表 kjorder
     * 参数： id （用户 id）
     *  select kjorder  where   uid=？  order by id asc
     * */
//合并
    /**
     * 获取用户 运行/过期 的矿机
     * 表 kjorder
     * 参数： id
     *  运行中：select kjorder  where   id=？ and  status=1
     *  过期：select kjorder  where   id=？ and  status！=1
     * */
    @GetMapping("/uidList")
    @ResponseBody
    @ApiOperation(value = "获取用户矿机列表/获取用户 运行/过期 的矿机")
    @NoNeedLogin
    public ResponseDTO<List<TwKjorder>> uidList(@RequestParam int uid) {
        return ResponseDTO.ok(twKjorderService.uidList(uid));
    }
    /**
     * 获取矿机详情
     * 表 kuangji
     * 参数： id
     *  select kuangji  where   id=？
     *  大致逻辑：
     *      1。需要判断是共享矿机 type=2
     *  大致逻辑：
     *      $info = M("kuangji")->where(array('id' => $oid))->find();
     *       if ($info['type'] == 2) {
     *             $typearr = explode("|", $info['sharebl']); sharebl字段 | 分割
     *             $info['fe1'] = $typearr[0];
     *             $info['fe2'] = $typearr[1];
     *         }
     * */
    @GetMapping("/detail")
    @ResponseBody
    @ApiOperation(value = "获取矿机详情")
    @NoNeedLogin
    public ResponseDTO<TwKuangji> detail(@RequestParam int id) {
        return ResponseDTO.ok(twKuangjiService.detail(id));
    }



    /**
     * 矿机收益列表
     * 表 kjprofit
     * 参数： id （用户 id）
     *  select kjprofit  where   uid=？ order by day desc limit 50
     *  大致逻辑：
     *      1.查询后 遍历判断时间
     *  逻辑代码：
     *      $list = M("kjprofit")->where(array('uid' => $id))->order("day desc")->limit(50)->select();
     *         $nowtime = date("Y-m-d", time());（当前时间）
     *         foreach ($list as $key => $vo) {
     *             if ($vo['day'] < $nowtime) { （当前时间以前）
     *                 $list[$key]['status'] = 1;
     *             } else {
     *                 $list[$key]['status'] = 2;
     *             }
     *         }
     * */
    @GetMapping("/kjprofit")
    @ResponseBody
    @ApiOperation(value = "矿机收益列表")
    @NoNeedLogin
    public ResponseDTO<List<TwKjprofit>> kjprofit(@RequestParam int uid) {
        return ResponseDTO.ok(twKjprofitService.kjprofit(uid));
    }


    @GetMapping("/kjprofitSum")
    @ResponseBody
    @ApiOperation(value = "统计矿机收益列表")
    @NoNeedLogin
    public ResponseDTO<TwPCKjprofitVo> kjprofitSum(@RequestParam int uid) {
        return ResponseDTO.ok(twKuangjiService.kjprofitSum(uid));
    }




    /**
     * 购买共享矿机 （很复杂）
     * 表 user，kuangji，kjorder，user_coin，bill
     * 参数： id （用户 id） kid(矿机 id) sharbltxt （分享码）  gxfe（占比份额）
     *  select kjprofit  where   uid=？ order by day desc limit 50
     *  大致逻辑：
     *      1.判断user 条件 表 user
     *      2.判断矿机 状态 表 kuangji
     *      3.建仓矿机订单数据 写入 kjorder
     *      4.扣除会员额度 user_coin
     *      5.写资金日志 bill
     *      6.查看有没有购买奖励 有奖励 增加 user_coin 写资金日志 bill
     *  逻辑代码：
     *      //购买共享矿机
     *     public function buygxmining()
     *     {
     *         $sharbltxt = trim(I('post.sharbltxt'));
     *         $kid = trim(I('post.kid'));
     *         $uid = userid();
     *         $gxfe = trim(I('post.gxfe'));
     *         $uinfo = M("user")->where(array('id' => $uid))->field("id,username,rzstatus")->find();
     *         if ($uinfo['rzstatus'] != 2) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('请先完成实名认证')]);
     *         }
     *         $minfo = M("kuangji")->where(array('id' => $kid))->find();
     *         $fearr = explode('|', $minfo['sharebl']);
     *         if ($gxfe != $fearr[0] && $gxfe != $fearr[1]) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('选择的占比份额不正确')]);
     *         }
     *         $buyinfo = M("kjorder")->where(array('sharbltxt' => $sharbltxt, 'sharebl' => $gxfe))->find();
     *         if (!empty($buyinfo)) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('不要重复购买')]);
     *         }
     *         //查矿机状态
     *         if ($minfo['status'] != 1) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('矿机暂停出售')]);
     *         }
     *         if (($minfo['sellnum'] + $minfo['ycnum']) >= $minfo['allnum']) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('售机已售罄')]);
     *         }
     *         //查看该矿机购买上限
     *         $minecount = M("kjorder")->where(array('kid' => $kid, 'uid' => $uid, 'status' => 1))->count();
     *         if ($minecount >= $minfo['buymax']) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('已达到限购数量')]);
     *         }
     *         //查会员购买资质
     *         $umoney = M("user_coin")->where(array('userid' => $uid))->find();
     *         $buyask = $minfo['buyask'];
     *
     *         //按持仓平台币数量
     *         if ($buyask == 1) {
     *             $ptcoin = strtolower(PT_COIN);
     *             $ptcoind = $ptcoin . "d";
     *             if (($umoney[$ptcoin] + $umoney[$ptcoind]) < $minfo['asknum']) {
     *                 $this->ajaxReturn(['code' => 0, 'msg' => L('持有平台币额度不足')]);
     *             }
     *             //按直推人数
     *         } elseif ($buyask == 2) {
     *             $tzcount = M("user")->where(array('invit_1' => $uid))->count();
     *             if ($tzcount < $minfo['asknum']) {
     *                 $this->ajaxReturn(['code' => 0, 'msg' => L('直推人数未达要求')]);
     *             }
     *         }
     *         //查会员余额
     *         $pricecoin = $minfo['pricecoin'];
     *         $pricenum = $minfo['pricenum'];
     *         $tprice = $pricenum * $gxfe / 100;
     *         if ($umoney[$pricecoin] < $tprice) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('账户余额不足')]);
     *         }
     *         //建仓矿机订单数据
     *         $odate['kid'] = $minfo['id'];
     *         $odate['type'] = 2;
     *         $odate['sharbltxt'] = $sharbltxt;
     *         $odate['sharebl'] = $gxfe;
     *         $odate['uid'] = $uid;
     *         $odate['username'] = $uinfo['username'];
     *         $odate['kjtitle'] = $minfo['title'];
     *         $odate['imgs'] = $minfo['imgs'];
     *         $odate['status'] = 1;
     *         $odate['cycle'] = $minfo['cycle'];
     *         $odate['synum'] = $minfo['cycle'];
     *         $odate['outtype'] = $minfo['outtype'];
     *         $odate['outcoin'] = $minfo['outcoin'];
     *         if ($minfo['outtype'] == 1) {//按产值收益
     *             $odate['outnum'] = '';
     *             $odate['outusdt'] = $minfo['dayoutnum'] * $gxfe / 100;
     *         } elseif ($minfo['outtype'] == 2) {//按币量收益
     *             $odate['outnum'] = $minfo['dayoutnum'] * $gxfe / 100;
     *             $odate['outusdt'] = '';
     *         }
     *         $odate['djout'] = $minfo['djout'];
     *         if ($minfo['djout'] == 2) {
     *             $odate['djnum'] = $minfo['djday'];
     *         } else {
     *             $odate['djnum'] = $minfo['djday'];
     *         }
     *         $odate['addtime'] = date("Y-m-d H:i:s", time());
     *         $odate['endtime'] = date("Y-m-d H:i:s", (time() + 86400 * $minfo['cycle']));
     *         $odate['intaddtime'] = time();
     *         $odate['intendtime'] = time() + 86400 * $minfo['cycle'];
     *         $adre = M("kjorder")->add($odate);
     *         //扣除会员额度
     *         $decre = M("user_coin")->where(array('userid' => $uid))->setDec($pricecoin, $tprice);
     *         //写资金日志
     *         $billdata['uid'] = $uid;
     *         $billdata['username'] = $uinfo['username'];
     *         $billdata['num'] = $tprice;
     *         $billdata['coinname'] = $pricecoin;
     *         $billdata['afternum'] = $umoney[$pricecoin] - $tprice;
     *         $billdata['type'] = 5;
     *         $billdata['addtime'] = date("Y-m-d H:i:s", time());
     *         $billdata['st'] = 2;
     *         $billdata['remark'] = L('购买矿机');
     *         $billre = M("bill")->add($billdata);
     *         if ($adre && $decre && $billre) {
     *             //查看有没有购买奖励
     *             if ($minfo['jlnum'] > 0) {
     *                 $jlcoin = $minfo['jlcoin'];
     *                 $jlnum = $minfo['jlnum'] * $gxfe / 100;
     *                 M("user_coin")->where(array('userid' => $uid))->setInc($jlcoin, $jlnum);
     *                 $jlbilldata['uid'] = $uid;
     *                 $jlbilldata['username'] = $uinfo['username'];
     *                 $jlbilldata['num'] = $jlnum;
     *                 $jlbilldata['coinname'] = $jlcoin;
     *                 $jlbilldata['afternum'] = $umoney[$jlcoin] + $jlnum;
     *                 $jlbilldata['type'] = 6;
     *                 $jlbilldata['addtime'] = date("Y-m-d H:i:s", time());
     *                 $jlbilldata['st'] = 1;
     *                 $jlbilldata['remark'] = L('购机奖励');
     *                 M("bill")->add($jlbilldata);
     *                 M("kuangji")->where(array('id' => $kid))->setInc('sellnum', 1);
     *             }
     *             $this->ajaxReturn(['code' => 1, 'msg' => L('购买成功')]);
     *         } else {
     *             $this->ajaxReturn(['code' => 1, 'msg' => L('购买失败')]);
     *         }
     *     }
     * */




    /**
     * 购买独资矿机 （很复杂，逻辑和购买共享相同，代码大部分也相同）
     * 表 user，kuangji，kjorder，user_coin，bill
     * 参数： id （用户 id） kid(矿机 id) sharbltxt （分享码）  gxfe（占比份额）
     *  select kjprofit  where   uid=？ order by day desc limit 50
     *  大致逻辑：
     *      1.判断user 条件 表 user
     *      2.判断矿机 状态 表 kuangji
     *      3.建仓矿机订单数据 写入 kjorder
     *      4.扣除会员额度 user_coin
     *      5.写资金日志 bill
     *      6.查看有没有购买奖励 有奖励 增加 user_coin 写资金日志 bill
     *  逻辑代码：
     *        //购买独资矿机
     *     public function buydzmining($kid,$uid)
     *     {
     *         $uinfo = M("user")->where(array('id' => $uid))->field("id,username,rzstatus")->find();
     *         if ($uinfo['rzstatus'] != 2) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('请先完成实名认证')]);
     *         }
     *         $minfo = M("kuangji")->where(array('id' => $kid))->find();
     *         //查矿机状态
     *         if ($minfo['status'] != 1) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('矿机暂停出售')]);
     *         }
     *         if (($minfo['sellnum'] + $minfo['ycnum']) >= $minfo['allnum']) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('售机已售罄')]);
     *         }
     *         //查看该矿机购买上限
     *         $minecount = M("kjorder")->where(array('kid' => $kid, 'uid' => $uid, 'status' => 1))->count();
     *         if ($minecount >= $minfo['buymax']) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('已达到限购数量')]);
     *         }
     *         //查会员购买资质
     *         $umoney = M("user_coin")->where(array('userid' => $uid))->find();
     *         $buyask = $minfo['buyask'];
     *         //按持仓平台币数量    这坨逻辑不要
     *         if ($buyask == 1) {
     *             $ptcoin = strtolower(PT_COIN);
     *             $ptcoind = $ptcoin . "d";
     *             if ($umoney[$ptcoin] + $umoney[$ptcoind] < $minfo['asknum']) {
     *                 $this->ajaxReturn(['code' => 0, 'msg' => L('持有平台币额度不足')]);
     *             }
     *             //按直推人数
     *         } elseif ($buyask == 2) {
     *             $tzcount = M("user")->where(array('invit_1' => $uid))->count();
     *             if ($tzcount < $minfo['asknum']) {
     *                 $this->ajaxReturn(['code' => 0, 'msg' => L('直推人数未达要求')]);
     *             }
     *         }
     *         //查会员余额
     *         $pricecoin = $minfo['pricecoin'];
     *         if ($umoney[$pricecoin] < $minfo['pricenum']) {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('账户余额不足')]);
     *         }
     *         //建仓矿机订单数据
     *         $odate['kid'] = $minfo['id'];
     *         $odate['type'] = 1;
     *         $odate['sharebl'] = '';
     *         $odate['sharebl'] = '';
     *         $odate['uid'] = $uid;
     *         $odate['username'] = $uinfo['username'];
     *         $odate['kjtitle'] = $minfo['title'];
     *         $odate['imgs'] = $minfo['imgs'];
     *         $odate['status'] = 1;
     *         $odate['cycle'] = $minfo['cycle'];
     *         $odate['synum'] = $minfo['cycle'];
     *         $odate['outtype'] = $minfo['outtype'];
     *         $odate['outcoin'] = $minfo['outcoin'];
     *         if ($minfo['outtype'] == 1) {//按产值收益
     *             $odate['outnum'] = '';
     *             $odate['outusdt'] = $minfo['dayoutnum'];
     *         } elseif ($minfo['outtype'] == 2) {//按币量收益
     *             $odate['outnum'] = $minfo['dayoutnum'];
     *             $odate['outusdt'] = '';
     *         }
     *         $odate['djout'] = $minfo['djout'];
     *         if ($minfo['djout'] == 2) {
     *             $odate['djnum'] = $minfo['djday'];
     *         } else {
     *             $odate['djnum'] = $minfo['djday'];
     *         }
     *         $odate['addtime'] = date("Y-m-d H:i:s", time());
     *         $odate['endtime'] = date("Y-m-d H:i:s", (time() + 86400 * $minfo['cycle']));
     *         $odate['intaddtime'] = time();
     *         $odate['intendtime'] = time() + 86400 * $minfo['cycle'];
     *         $adre = M("kjorder")->add($odate);
     *         //扣除会员额度
     *         $buyprice = $minfo['pricenum']; //单价的币量
     *         $buycoin = $minfo['pricecoin']; //单价的币种
     *         $decre = M("user_coin")->where(array('userid' => $uid))->setDec($buycoin, $buyprice);
     *         //写资金日志
     *         $billdata['uid'] = $uid;
     *         $billdata['username'] = $uinfo['username'];
     *         $billdata['num'] = $buyprice;
     *         $billdata['coinname'] = $buycoin;
     *         $billdata['afternum'] = $umoney[$buycoin] - $buyprice;
     *         $billdata['type'] = 5;
     *         $billdata['addtime'] = date("Y-m-d H:i:s", time());
     *         $billdata['st'] = 2;
     *         $billdata['remark'] = L('购买矿机');
     *         $billre = M("bill")->add($billdata);
     *         if ($adre && $decre && $billre) {
     *             //查看有没有购买奖励
     *             if ($minfo['jlnum'] > 0) {
     *                 $jlcoin = $minfo['jlcoin'];
     *                 $jlnum = $minfo['jlnum'];
     *                 M("user_coin")->where(array('userid' => $uid))->setInc($jlcoin, $jlnum);
     *                 $jlbilldata['uid'] = $uid;
     *                 $jlbilldata['username'] = $uinfo['username'];
     *                 $jlbilldata['num'] = $jlnum;
     *                 $jlbilldata['coinname'] = $jlcoin;
     *                 $jlbilldata['afternum'] = $umoney[$jlcoin] + $jlnum;
     *                 $jlbilldata['type'] = 6;
     *                 $jlbilldata['addtime'] = date("Y-m-d H:i:s", time());
     *                 $jlbilldata['st'] = 1;
     *                 $jlbilldata['remark'] = L('购机奖励');
     *                 M("bill")->add($jlbilldata);
     *                 M("kuangji")->where(array('id' => $kid))->setInc('sellnum', 1);
     *             }
     *             $this->ajaxReturn(['code' => 1, 'msg' => L('购买成功')]);
     *         } else {
     *             $this->ajaxReturn(['code' => 1, 'msg' => L('购买失败')]);
     *         }
     * */
    @GetMapping("/buyKuangji")
    @ResponseBody
    @ApiOperation(value = "购买独资矿机")
    @NoNeedLogin
    public ResponseDTO buyKuangji(@RequestParam int uid, @RequestParam int kid, @RequestParam BigDecimal buynum) {
        return ResponseDTO.ok(twKuangjiService.buyKuangji(uid,kid,buynum));
    }


}

