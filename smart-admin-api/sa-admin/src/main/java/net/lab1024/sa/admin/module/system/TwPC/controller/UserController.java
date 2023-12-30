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
     * 通知详情
     * 参数： id （ 公告 id）
     * 表 notice
     * select notice  where  id=?
     * */

}

