package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.ApiOperation;
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
public class UserController {

    @Autowired
    private TwUserService twUserService;

    /**
     * 获取用户信息
     * 表 user
     * 参数： token
     * select user  where  uid=？
     * */
    @NoNeedLogin
    @GetMapping("/userInfo")
    @ApiOperation("获取用户信息")
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
    @NoNeedLogin
    @PostMapping("/editpassword")
    @ApiOperation("用户重置密码")
    public ResponseDTO editpassword(@Valid @RequestBody UserReq userReq) {
        return twUserService.editpassword(userReq);
    }

}

