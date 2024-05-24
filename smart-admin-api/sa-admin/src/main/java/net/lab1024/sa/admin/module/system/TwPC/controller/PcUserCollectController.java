package net.lab1024.sa.admin.module.system.TwPC.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCollectService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pc/usercollect")
@Api(tags = {AdminSwaggerTagConst.PC.PC_COLLECT})
@Slf4j
public class PcUserCollectController {

    @Autowired
    private TwUserCollectService twUserCollectService;
    @GetMapping("/list")
    @ApiOperation(value = "获取收藏列表")
    @NoNeedLogin
    public ResponseDTO lists(@RequestParam int uid) {
        return twUserCollectService.lists(uid);
    }

    @GetMapping("/sel")
    @ApiOperation(value = "是否收藏")
    @NoNeedLogin
    public ResponseDTO sel(@RequestParam int uid,String coinname,String language) {
        return twUserCollectService.sel(uid,coinname,language);
    }

    @GetMapping("/add")
    @ApiOperation(value = "收藏")
    @NoNeedLogin
    public ResponseDTO add(@RequestParam int uid,String coinname,String language) {
        return twUserCollectService.add(uid,coinname,language);
    }
    @GetMapping("/del")
    @ApiOperation(value = "取消收藏")
    @NoNeedLogin
    public ResponseDTO del(@RequestParam int uid,String coinname,String language) {
        return twUserCollectService.del(uid,coinname,language);
    }
}
