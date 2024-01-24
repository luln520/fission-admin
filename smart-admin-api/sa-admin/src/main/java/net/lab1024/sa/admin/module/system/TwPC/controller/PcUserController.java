package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.UserReq;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户
 */
@RestController
@RequestMapping("/api/pc/user")
@Api(tags = {AdminSwaggerTagConst.PC.PC_USER})
public class PcUserController {

    @Autowired
    private TwUserService twUserService;

    /**
     * 获取用户信息
     * 表 user
     * 参数： token
     * select user  where  uid=？
     * */
    @GetMapping("/userInfo")
    @ApiOperation("获取用户信息")
    @NoNeedLogin
    public ResponseDTO userInfo(@RequestParam String token) {
        return twUserService.userInfo(token);
    }

    /**
     * 用户重置密码  表 user，notice
     * 参数： username  password  oldPassword（老密码）
     * 大致逻辑：
     *      1.通过用户名查询用户，先判断该用户是否存在
     *      3.判断账号老密码是否正确MD5
     *      4.都通过  MD5 加密password 修改password 字段
     *      5.新增通知记录 （表 notice）
     *
     *
     * 参考代码：
     *      //通知写入
     *       $data['uid'] = $uinfo['id'];（用户id）
     *       $data['account'] = $uinfo['username']; （用户 username）
     *       $data['title'] = '重置密码'
     *       $data['content'] = '登陆密码重置成功';
     *       $data['addtime'] = date("Y-m-d H:i:s", time());
     *       $data['status'] = 1;
     *       M("notice")->add($data); （写入通知表）
     * */
    @PostMapping("/editpassword")
    @ApiOperation("用户重置密码")
    @NoNeedLogin
    public ResponseDTO editpassword(@Valid @RequestBody UserReq userReq) {
        return twUserService.editpassword(userReq);
    }


    /**
     * 用户提交实名认证  表 user，notice
     * 参数：id(用户id),phone(手机号), real_name（真实姓名）, cardzm（身份证正面）, cardfm（身份证背面）
     * 大致逻辑：
     *      1.判断四个参数是否为空
     *      3.写入 user表   phone(手机号), real_name（真实姓名）, cardzm（身份证正面）, cardfm（身份证背面  where id=？
     *      4.写入通知表 notice
     *
     *
     * 参考代码：
     *          $data['cardzm'] = $cardzm;
     *         $data['cardfm'] = $cardfm;
     *         $data['real_name'] = $real_name;
     *         $data['rzstatus'] = 1;
     *         $data['rztime'] = time();
     *         $re = M("user")->where(array('id' => $id))->save($data);（逻辑 2）
     *         $userinfo = M("user")->where(array('id' => $id))->find();
     *         if ($re) {
     *             $notice['uid'] = $id;
     *             $notice['account'] = $userinfo['username']; （读取用户的 username）
     *             $notice['title'] = L('认证资料提交成功');
     *             $notice['content'] = L('实名资料提成功，耐心等待管理员审核');
     *             $notice['addtime'] = date("Y-m-d H:i:s", time());
     *             $notice['status'] = 1;
     *             M("notice")->add($notice); （逻辑 3）
     *             $this->ajaxReturn(['code' => 1, 'info' => L('认证资料提交成功')]);
     *         } else {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('认证资料提交失败')]);
     *         }
     * */

    /**
     * 账户总览 USDT余额查询  表 user_coin
     * 参数：id(用户id)
     * select user_coin where  userid =?
     * */
    @PostMapping("/auth")
    @ResponseBody
    @ApiOperation(value = "用户提交实名认证")
    @NoNeedLogin
    public ResponseDTO auth(@RequestBody TwUser twUser) {
        return twUserService.auth(twUser);
    }
}

