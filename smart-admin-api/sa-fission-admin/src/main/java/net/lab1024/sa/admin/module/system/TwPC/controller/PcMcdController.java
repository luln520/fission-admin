package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.FollowVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.McdInfoVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMcdInfoService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pc/mcd")
@Api(tags = {AdminSwaggerTagConst.PC.PC_FOLLOW})
@Slf4j
public class PcMcdController {

    @Autowired
    private TwMcdInfoService twMcdInfoService;

    @GetMapping("/list")
    @ApiOperation("获取跟单员列表")
    @NoNeedLogin
    public ResponseDTO<List<McdInfoVo>> mcdList(@RequestParam String companyId) {
        return ResponseDTO.ok(twMcdInfoService.listMcdUser(companyId));
    }

    @GetMapping("/info")
    @ApiOperation("获取跟单员详情")
    @NoNeedLogin
    public ResponseDTO<McdInfoVo> mcdInfo(@RequestParam int uid) {
        return ResponseDTO.ok(twMcdInfoService.queryMcdUser(uid));
    }

    @GetMapping("/my/follow")
    @ApiOperation("获取我的跟单员列表")
    @NoNeedLogin
    public ResponseDTO<List<FollowVo>> myFollow(@RequestParam int uid) {
        return ResponseDTO.ok(twMcdInfoService.listMyFollow(uid));
    }

    @GetMapping("/follow/add")
    @ApiOperation("加入跟单")
    @NoNeedLogin
    public ResponseDTO addFollow(@RequestParam int followUid, @RequestParam int uid,
                                               @RequestParam BigDecimal investProp) {
        twMcdInfoService.addFollow(followUid, uid, investProp);
        return ResponseDTO.ok();
    }

    @GetMapping("/follow/delete")
    @ApiOperation("结束跟单")
    @NoNeedLogin
    public ResponseDTO delFollow(@RequestParam int followUid, @RequestParam int uid) {
        twMcdInfoService.delFollow(followUid, uid);
        return ResponseDTO.ok();
    }

    @GetMapping("/apply")
    @ApiOperation("申请跟单员")
    @NoNeedLogin
    public ResponseDTO apply(@RequestParam int uid) {
        twMcdInfoService.applyMcd(uid);
        return ResponseDTO.ok();
    }
}
