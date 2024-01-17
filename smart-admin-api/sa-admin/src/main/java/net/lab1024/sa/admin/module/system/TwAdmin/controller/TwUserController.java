package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHysettingService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserKuangjiService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * 用户
 */
@RestController
@RequestMapping("/api/admin/user")
@Api(tags = {AdminSwaggerTagConst.System.TW_USER})
public class TwUserController {

    @Autowired
    private TwUserService twUserService;

    @Autowired
    private TwHysettingService twHysettingService;

    @Autowired
    private TwUserKuangjiService twUserKuangjiService;
    /**
     * 获取所有用户  表User 全部查询 order by id desc
     * 所有表：User(用户) user_log（用户登陆日志） coin （用户财产）
     * 1.遍历user  通过 id=invit_1 id=invit_2 id=invit_3 查询表User到对应的username  当前用户的三个上级
     * 2.遍历user  通过 userid=？ 和 type=login 查询 user_log到上次登陆的信息
     * 。。。。
     * 逻辑代码：
     *         $where = array();
     *         $list = M('User')->where($where)->order('id desc')->select(); (用户列表，下面遍历)
     *         foreach ($list as $k => $v) {
     *             $list[$k]['invit_1'] = M('User')->where(array('id' => $v['invit_1']))->getField('username');（一级代理名字）
     *             $list[$k]['invit_2'] = M('User')->where(array('id' => $v['invit_2']))->getField('username');（二级代理名字）
     *             $list[$k]['invit_3'] = M('User')->where(array('id' => $v['invit_3']))->getField('username');（三级代理名字）
     *             $user_login_state = M('user_log')->where(array('userid' => $v['id'], 'type' => 'login'))->order('id desc')->find(); （最近登陆时间 1条）
     *             $list[$k]['state'] = $user_login_state['state'];
     *         }
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取所有用户")
    @NoNeedLogin
    public ResponseDTO<IPage<TwUser>> listUserpage(@Valid @RequestBody TwUserVo twUserVo, HttpServletRequest request) {
        return ResponseDTO.ok(twUserService.listUserpage(twUserVo,request));
    }


    /**
     * 禁止/允许交易  表User update set buy_on=? where id=?
     * 参数：id buy_on
     * 说明：buy_on=2 禁止    buy_on=1 允许
     */
    @GetMapping("/setBuy")
    @ApiOperation(value = "禁止/允许交易")
    @NoNeedLogin
    public ResponseDTO setBuy(@RequestParam int id,@RequestParam int buyOn) {
        return ResponseDTO.ok(twUserService.setBUy(id,buyOn));
    }


    /**
     * 指定必赢/指定必输/正常输赢  表User 表hysetting
     * 调取写入 操作日志AdminLog
     * 参数：id type
     * 说明type=1指定必赢 type=2指定必输 type=3正常输赢
     *     表hysetting: hy_ksid 输 的所有用户id,hy_ylid 赢 的所有用户id   都是 | 竖线作为分割  例如：759|757|758|744|760|753
     * 功能1：指定必赢
     *        删除该用户输（hy_ksid）字段的id ，id写入 （hy_ylid）字段的
     * 功能2：指定必输
     *      与上面相反
     * 功能3：正常输赢
     *      两个字段内都把 该用户 id 删除就行
     */
    @GetMapping("/setWin")
    @ApiOperation(value = "指定必赢/指定必输/正常输赢")
    @NoNeedLogin
    public ResponseDTO setWin(@RequestParam int id,@RequestParam int type,@RequestParam int uid, HttpServletRequest request) {
        return ResponseDTO.ok(twHysettingService.setWin(id,type,uid,request));
    }

    /**
     * 修改会员状态（冻结，解冻，启动提币禁止提币 ，删除会员）  表User update
     * 调取写入 操作日志AdminLog
     * 参数：id type
     * 说明type=1 冻结 type=2 解冻 type=3启动提币 type =4 禁止提币 type =5 删除会员
     *     冻结/解冻： update status=? where id=？
     *     启动/禁止提币： update txstate=? where id=？
     *     删除会员：删除表较多 看代码
     *  代码逻辑如下：
     *                 //删除会员
     *                 M("user")->where($where)->delete();
     *                 M("user_coin")->where(["userid" => $id])->delete();
     *                 M("hyorder")->where(["uid" => $id])->delete();
     *                 M("bborder")->where(["uid" => $id])->delete();
     *                 M("bill")->where(["uid" => $id])->delete();
     *                 M("recharge")->where(["uid" => $id])->delete();
     *                 M("myzc")->where(["userid" => $id])->delete();
     *                 M("notice")->where(["uid" => $id])->delete();
     *                 M("online")->where(["uid" => $id])->delete();
     *                 M("kjprofit")->where(["uid" => $id])->delete();
     *                 M("user_log")->where(["userid" => $id])->delete();
     *                 M("issue_log")->where(["uid" => $id])->delete();
     *                 M("kjorder")->where(["uid" => $id])->delete();
     *
     */

    //不要删除会员
    @GetMapping("/setUser")
    @ApiOperation(value = "修改会员状态（冻结，解冻，启动提币禁止提币 ，删除会员）")
    @NoNeedLogin
    public ResponseDTO setUser(@RequestParam int id,@RequestParam int type,@RequestParam int uid) {
        return ResponseDTO.ok(twUserService.setUser(id,type,uid));
    }


    /**
     * 给会员发送通知或者群发  表User 表notice
     * 参数：id type  title  content imgs
     * 说明type=1 单发 type=2 群发
     *  功能1：单发功能-》通过用户id拿到用户信息，取用户信息合并写入通知表
     *         逻辑代码：
     *             $uinfo = M("user")->where(array('id' => $id))->field("id,username")->find();
     *             $data['uid'] = $uinfo['id'];
     *             $data['account'] = $uinfo['username'];
     *             $data['title'] = $title;
     *             $data['content'] = $content;
     *             $data['imgs'] = $imgs;
     *             $data['addtime'] = date("Y-m-d H:i:s", time());
     *             $data['status'] = 1;
     *             $result = M("notice")->add($data);
     *  功能1：单发功能-》拿到所有用户信息，循环写入通知表
     *              $ulist = M("user")->field("id,username")->select();
     *             foreach ($ulist as $key => $vo) {
     *                 $data['uid'] = $vo['id'];
     *                 $data['account'] = $vo['username'];
     *                 $data['title'] = $title;
     *                 $data['content'] = $content;
     *                 $data['imgs'] = $imgs;
     *                 $data['addtime'] = date("Y-m-d H:i:s", time());
     *                 $data['status'] = 1;
     *                 M("notice")->add($data);
     *             }
     *
     *
     */
    @GetMapping("/userNotice")
    @ApiOperation(value = "给会员发送通知或者群发")
    @NoNeedLogin
    public ResponseDTO userNotice(@RequestParam int id,
                                  @RequestParam int type,
                                  @RequestParam String title,
                                  @RequestParam String content,
                                  @RequestParam String imgs) {
        return ResponseDTO.ok(twUserService.userNotice(id,type,title,content,imgs));
    }


    /**
     * 修改用户余额  表User user_coin  recharge bill
     * 调取写入 操作日志AdminLog
     * 参数：id（用户） type  money bizhong（币种）
     * 说明type=1 增加 type=2 减少
     *  逻辑代码如下：（很长 很主要 仔细观看）
     *         $uid = $_REQUEST['userid']; （传入的用户id）
     *         $money = $_REQUEST["money"];（传入money）
     *         $type = $_REQUEST["money_type"];（传入typr）
     *         $bizhong = strtolower(trim($_REQUEST["bizhong"]));（传入 bizhong）
     *         $uinfo = M("user")->where(["id" => $uid])->find(); （查询用户信息）
     *         $userinfo = M("user_coin")->where(["userid" => $uid])->find(); （查询 用户资产）
     *         if ($type == 2) {
     *             $result = M("user_coin")->where(array('id' => $userinfo['id']))->setDec($bizhong, $money); （减去）
     *         }
     *         if ($type == 1) {
     *             $result = M("user_coin")->where(array('id' => $userinfo['id']))->setInc($bizhong, $money); （增加）
     *             if ($result) {
     *                 $rechargeData = array(
     *                     "uid" => $_REQUEST['userid'],
     *                     "username" => $uinfo['username'],
     *                     "coin" => strtoupper($bizhong),
     *                     "num" => $money,
     *                     "addtime" => date("Y-m-d H:i:s"),
     *                     "updatetime" => date("Y-m-d H:i:s"),
     *                     "status" => 2,
     *                     "payimg" => "",
     *                     "msg" => "",
     *                     "atype" => 1,
     *                 );
     *                 M("recharge")->add($rechargeData);（写入充值币的列表）
     *             }
     *         }
     *         if ($type == 2) {
     *             $remark = "管理员手动减少";
     *             $afternum = $userinfo[$bizhong] - $money;
     *         } else {
     *             $remark = "管理员手动增加";
     *             $afternum = $userinfo[$bizhong] + $money;
     *         }
     *         $billData = array(
     *             "uid" => $_REQUEST['userid'],
     *             "username" => $uinfo['username'],
     *             "coinname" => strtoupper($bizhong),
     *             "num" => $money,
     *             "afternum" => $afternum,
     *             "type" => 1,
     *             "addtime" => date("Y-m-d H:i:s"),
     *             "st" => 1,
     *             "remark" => $remark,
     *         );
     *         M("bill")->add($billData); （写入资金流水日志）
     *
     *
     */

    @GetMapping("/setMoney")
    @ApiOperation(value = "修改用户余额")
    @NoNeedLogin
    public ResponseDTO setMoney(@RequestParam int uid, @RequestParam int type, @RequestParam BigDecimal money,HttpServletRequest request) {
        return ResponseDTO.ok(twUserService.setMoney(uid,type,money,request));
    }


    /**
     * 编辑或新增会员  表User 判断id 是否存在  存在就修改 不存在就插入
     * 新增：判断 username phone 是否重复
     *       判断 invit 上级人 是否存在
     *       password paypassword  使用一般 MD5加密
     *       生成邀请码 qwertyuiopasdfghjklzxcvbnmABCDEFGHIJKLMNPQRSTUVWXYZ随机字符 5位
     *       ip地址：代码获取
     *       地区：通过ip定位找
     *       改完后 对象 直接存入 表 user
     *       调取写入 操作日志AdminLog
     * 修改：
     *      password paypassword  使用一般 MD5加密
     *      其余这段不修改 直接写入 表user
     *      调取写入 操作日志AdminLog
     */
    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "编辑或新增会员")
    @NoNeedLogin
    public ResponseDTO addOrUpdate(@RequestBody TwUser twUser, HttpServletRequest request) throws IOException {
        return twUserService.addOrUpdate(twUser,request);
    }


    @PostMapping("/authList")
    @ApiOperation(value = "获取认证用户")
    @NoNeedLogin
    public ResponseDTO<IPage<TwUser>> authList(@Valid @RequestBody TwUserVo twUserVo) {
        return ResponseDTO.ok(twUserService.authList(twUserVo));
    }

    @GetMapping("/authProcess")
    @ApiOperation(value = "认证用户审核")
    @NoNeedLogin
    public ResponseDTO authProcess(@RequestParam int uid, @RequestParam int type, HttpServletRequest request) {
        return ResponseDTO.ok(twUserService.authProcess(uid,type,request));
    }


    @PostMapping("/updatekj")
    @ApiOperation(value = "用户矿机单控")
    @NoNeedLogin
    public ResponseDTO updatekj(@Valid @RequestBody TwUserKuangji twUserKuangji) {
        return ResponseDTO.ok(twUserKuangjiService.updatekj(twUserKuangji));
    }

}

