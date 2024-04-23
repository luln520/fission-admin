package net.lab1024.sa.admin.module.system.TwPC.controller;

import cn.hutool.extra.servlet.ServletUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.lock.RedissonLockAnnotation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.UserReq;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 用户登陆/注册
 */
@RestController
@RequestMapping("/api/pc/userLoginOrRegister")
@Api(tags = {AdminSwaggerTagConst.PC.PC_LOGIN})
public class PcLoginController {

    @Autowired
    private TwUserService twUserService;

    @NoNeedLogin
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public ResponseDTO<TwUser> login(@Valid @RequestBody UserReq userReq) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = ServletUtil.getClientIP(request);
        return twUserService.loginUser(userReq, ip);
    }


    @NoNeedLogin
    @PostMapping("/register")
    @ApiOperation("用户注册")
    @RedissonLockAnnotation(keyParts = "username")
    public ResponseDTO register(@Valid @RequestBody UserReq userReq) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = ServletUtil.getClientIP(request);
        return twUserService.register(userReq, ip);
    }



}

