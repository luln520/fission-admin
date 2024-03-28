package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @PostMapping("/editpassword")
    @ApiOperation("用户更新密码")
    @NoNeedLogin
    public ResponseDTO editpassword(@Valid @RequestBody UserReq userReq) {
        return twUserService.editpassword(userReq);
    }


    @PostMapping("/auth")
    @ResponseBody
    @ApiOperation(value = "用户提交实名认证")
    @NoNeedLogin
    public ResponseDTO auth(@RequestBody TwUser twUser) {
        ResponseDTO auth = twUserService.auth(twUser);
        log.info("用户认证提交返回数据：ResponseDTO{}:",auth);
        return auth;
    }


    @GetMapping("/usertj")
    @ResponseBody
    @ApiOperation(value = "用户流水统计")
    @NoNeedLogin
    public ResponseDTO usertj(@RequestParam int uid) {
        return twUserService.usertj(uid);
    }


    @GetMapping("/userdk")
    @ResponseBody
    @ApiOperation(value = "用户单控详情")
    @NoNeedLogin
    public ResponseDTO userdk(@RequestParam int uid) {
        return twUserService.userdk(uid);
    }

    @GetMapping("/editPwd")
    @ResponseBody
    @ApiOperation(value = "更新密码")
    @NoNeedLogin
    public ResponseDTO editPasword(@RequestParam int uid,@RequestParam String oldPwd,@RequestParam String newPwd,@RequestParam  String language) {
        return twUserService.editPasword(uid,oldPwd,newPwd,language);
    }

}

