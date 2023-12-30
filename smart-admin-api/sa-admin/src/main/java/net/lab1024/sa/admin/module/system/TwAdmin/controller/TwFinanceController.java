package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMyzc;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRecharge;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwMyzcVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwRechargeVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwBillService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMyzcService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwRechargeService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 财务记录
 */
@RestController
@RequestMapping("/api/admin/financeConfig")
public class TwFinanceController {

    @Autowired
    private TwBillService twBillService;

    @Autowired
    private TwRechargeService twRechargeService;

    @Autowired
    private TwMyzcService twMyzcService;

    /**
     * 账务明细 表bill order by id desc
     */
    @PostMapping("/billList")
    @ApiOperation(value = "账务明细列表")
    @NoNeedLogin
    public ResponseDTO<IPage<TwBill>> listpage(@Valid @RequestBody TwBillVo twBillVo) {
        return ResponseDTO.ok(twBillService.listpage(twBillVo));
    }


    /**
     * 充币列表  表recharge  order by id desc
     */
    @PostMapping("/rechargeList")
    @ApiOperation(value = "充币列表")
    @NoNeedLogin
    public ResponseDTO<IPage<TwRecharge>> listpage(@Valid @RequestBody TwRechargeVo twRechargeVo) {
        return ResponseDTO.ok(twRechargeService.listpage(twRechargeVo));
    }

    /**
     * 提币列表  表myzc  order by id desc
     */
    @PostMapping("/list")
    @ApiOperation(value = "充币列表")
    @NoNeedLogin
    public ResponseDTO<IPage<TwMyzc>> listpage(@Valid @RequestBody TwMyzcVo twMyzcVo) {
        return ResponseDTO.ok(twMyzcService.listpage(twMyzcVo));
    }


    /**
     * 驳回充值
     */
    @PostMapping("/reject")
    @ApiOperation(value = "驳回充值")
    @NoNeedLogin
    public ResponseDTO reject(@RequestParam int  id) {
        return twRechargeService.reject(id);
    }
    /**
     * 驳回充币  表recharge  id=？   先判断订单是否存在   修改 表recharge  status=3
     * 然后 添加 notice（通知表） 字段内容如下
     *
     * 参考逻辑和代码：
     *          $info = M("recharge")->where(array('id' => $id))->find();（id 查询）
     *          if (empty($info)) {
     *             $this->error("充币订单不存在");
     *             exit();
     *         }
     *         if ($info['status'] != 1) {
     *             $this->error("此订单已处理");
     *             exit();1
     *         }
     *          //修改订单状态
     *         $save['updatetime'] = date("Y-m-d H:i:s", time());
     *         $save['status'] = 3;
     *         $upre = M("recharge")->where(array('id' => $id))->save($save); （id 修改）
     *         if ($upre) {
     *
     *             $data['uid'] = $info['uid'];
     *             $data['account'] = $info['username'];
     *             $data['title'] = L('充币审核');
     *             $data['content'] = L('您的充币记录被系统驳回，请联系客服');
     *             $data['addtime'] = date("Y-m-d H:i:s", time());
     *             $data['status'] = 1;
     *             M("notice")->add($data);（添加通知）
     *
     *             $this->success("充值驳回成功");
     *         } else {
     *             $this->error("驳回失败");
     *         }
     */


    @PostMapping("/confirm")
    @ApiOperation(value = "确认充币")
    @NoNeedLogin
    public ResponseDTO confirm(@RequestParam int  id) {
        return twRechargeService.confirm(id);
    }
    /**
     * 确认充币 （逻辑如下） 四个表 ：recharge（充值表 ） user_coin（用户资产表） bill （记录表）  notice（通知表）
     * 1.表recharge（充值表 ）  id=？   先判断订单是否存在  查询出来的结果为 info
     * 1.先修改 表recharge（充值表 ）  status=2
     * 2.然后 增加会员资产 表user_coin （用户资产表）
     * 3. 然后 添加充值日志 表 bill（记录表）
     * 4. 在前面三步都成功的情况下   添加 表notice（通知表）
     *
     * 参考逻辑和代码：
     *         $info = M("recharge")->where(array('id' => $id))->find();（id 查询）
     *         $uid = $info['uid'];
     *         $num = $info['num'];
     *         $coinname = strtolower(trim($info['coin']));（取出来然后转小写） 比如 $info['coin'] 是USDT $coinname则为 usdt
     *         $minfo = M("user_coin")->where(array('userid' => $uid))->find();（userid 查询）
     *         //修改订单状态
     *         $save['updatetime'] = date("Y-m-d H:i:s", time());
     *         $save['status'] = 2;
     *         $upre = M("recharge")->where(array('id' => $id))->save($save); （id 修改）
     *         //增加会员资产
     *         $incre = M("user_coin")->where(array('userid' => $uid))->setInc($coinname, $num); (提示sql:UPDATE user_coin SET （这里就为 usdt） = （这里就为 usdt） + num WHERE userid = uid;)
     *         //增加充值日志
     *         $data['uid'] = $info['uid'];
     *         $data['username'] = $info['username'];
     *         $data['num'] = $num;
     *         $data['coinname'] = $coinname;
     *         $data['afternum'] = $minfo[$coinname] + $num;（这里就是 取 minfo字段为 usdt的值）
     *         $data['type'] = 17;
     *         $data['addtime'] = date("Y-m-d H:i:s", time());
     *         $data['st'] = 1;
     *         $data['remark'] = L('充币到账');
     *         $addre = M("bill")->add($data);（添加充值日志）
     *         if ($upre && $incre && $addre) {（前面三个都成功  添加通知表）
     *             $notice['uid'] = $info['uid'];
     *             $notice['account'] = $info['username'];
     *             $notice['title'] = L('充币审核');
     *             $notice['content'] = L('您的充值金额已到账，请注意查收');
     *             $notice['addtime'] = date("Y-m-d H:i:s", time());
     *             $notice['status'] = 1;
     *             M("notice")->add($notice);(添加通知)
     *             $this->success("处理成功");
     *         } else {
     *             $this->error("处理失败");
     *         }
     */


    @PostMapping("/rejectCoin")
    @ApiOperation(value = "驳回提币")
    @NoNeedLogin
    public ResponseDTO rejectCoin(@RequestParam int  id) {
        return twMyzcService.rejectCoin(id);
    }

    /**
     * 驳回提币 （逻辑如下） 四个表 ：myzc（提币表 ） user_coin（用户资产表） bill （充值记录表）  notice（通知表）
     * 1.表myzc（提币表 ）  id=？   先判断订单是否存在  查询出来的结果为 info
     * 1.先修改 表myzc（提币表 ）  status=3
     * 2.然后 增加会员资产 表user_coin （用户资产表）
     * 3. 然后 添加日志 表 bill（记录表）
     * 4. 在前面三步都成功的情况下   添加 表notice（通知表）
     *
     * 参考逻辑和代码：
     *         $info = M("myzc")->where(array('id' => $id))->find();
     *         if (empty($info)) {
     *             $this->error("提币订单不存在");
     *             exit();
     *         }
     *         if ($info['status'] != 1) {
     *             $this->error("此订单已处理");
     *             exit();
     *         }
     *
     *         $uid = $info['userid'];
     *         $num = $info['num'];
     *         $coinname = strtolower(trim($info['coinname']));
     *         //修改记录状态
     *         $save['endtime'] = date("Y-m-d H:i:s", time());
     *         $save['status'] = 3;
     *         $upre = M("myzc")->where(array('id' => $id))->save($save);
     *         //把提币的数量返回给账号户，并写入日志
     *         $minfo = M("user_coin")->where(array('userid' => $uid))->find();
     *         $incre = M("user_coin")->where(array('userid' => $uid))->setInc($coinname, $num);
     *         $bill['uid'] = $uid;
     *         $bill['username'] = $info['username'];
     *         $bill['num'] = $num;
     *         $bill['coinname'] = $info['coinname'];
     *         $bill['afternum'] = $minfo[$coinname] + $num;
     *         $bill['type'] = 16;
     *         $bill['addtime'] = date("Y-m-d H:i:s", time());
     *         $bill['st'] = 1;
     *         $bill['remark'] = L('提币驳回，退回资金');;
     *         $billre = M("bill")->add($bill);
     *         if ($upre && $incre && $billre) {
     *             $notice['uid'] = $uid;
     *             $notice['account'] = $info['username'];
     *             $notice['title'] = L('提币审核');
     *             $notice['content'] = L('您的提币申请被驳回，请联系管理员');
     *             $notice['addtime'] = date("Y-m-d H:i:s", time());
     *             $notice['status'] = 1;
     *             M("notice")->add($notice);
     *
     *             $this->success("操作成功");
     *             exit();
     *         } else {
     *             $this->error("操作失败");
     *             exit();
     *         }
     */

    @PostMapping("/confirmCoin")
    @ApiOperation(value = "通过提币")
    @NoNeedLogin
    public ResponseDTO confirmCoin(@RequestParam int  id) {
        return twMyzcService.confirmCoin(id);
    }

    /**
     * 通过提币 （逻辑如下） 四个表 ：myzc（提币表 ）   notice（通知表）
     * 1.表myzc（提币表 ）  id=？   先判断订单是否存在  查询出来的结果为 info
     * 2.先修改 表myzc（提币表 ）  status=2
     * 3.然后 添加 表notice（通知表）
     *
     * 参考逻辑和代码：
     *         $info = M("myzc")->where(array('id' => $id))->find();
     *         if (empty($info)) {
     *             $this->error("提币订单不存在");
     *             exit();
     *         }
     *         if ($info['status'] != 1) {
     *             $this->error("此订单已处理");
     *             exit();
     *         }
     *         $save['endtime'] = date("Y-m-d H:i:s", time());
     *         $save['status'] = 2;
     *         $result = M("myzc")->where(array('id' => $id))->save($save);
     *         if ($result) {
     *
     *             $notice['uid'] = $info['userid'];
     *             $notice['account'] = $info['username'];
     *             $notice['title'] = L('提币审核');
     *             $notice['content'] = L('您的提币申请已通过，请及时查询');
     *             $notice['addtime'] = date("Y-m-d H:i:s", time());
     *             $notice['status'] = 1;
     *             M("notice")->add($notice);
     *
     *             $this->success('处理成功！', U('Finance/myzc'));
     *         } else {
     *             $this->error("处理失败");
     *             exit();
     *         }
     */



}

