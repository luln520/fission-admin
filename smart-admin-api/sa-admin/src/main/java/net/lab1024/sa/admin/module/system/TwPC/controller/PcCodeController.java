package net.lab1024.sa.admin.module.system.TwPC.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/pc/code")
@Api(tags = {AdminSwaggerTagConst.PC.PC_CODE})
public class PcCodeController {


    @Autowired
    private TwUserService twUserService;

    @GetMapping("/code")
    @ApiOperation(value = "手机/邮箱验证码")
    @NoNeedLogin
    public ResponseDTO code(@RequestParam String username,@RequestParam String area,@RequestParam int type,@RequestParam String language,@RequestParam int companyId) throws IOException {
        return twUserService.code(username,area,type,language,companyId);
    }

    @GetMapping("/codeResp")
    @ApiOperation(value = "获取手机/邮箱验证码返回")
    @NoNeedLogin
    public ResponseDTO codeResp() throws IOException {
        return twUserService.codeResp();
    }


}
