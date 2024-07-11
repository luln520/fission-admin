package net.lab1024.sa.admin.module.system.TwAdmin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserAgent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserTeam;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AgentVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TeamVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserAgentService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserTeamService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/userteam")
@Api(tags = {AdminSwaggerTagConst.System.TW_USERTEAM})
public class TwUserTeamController {

    @Autowired
    private TwUserTeamService twUserTeamService;
    @PostMapping("/list")
    @ApiOperation(value = "会员代理列表")
    public ResponseDTO<IPage<TwUserTeam>> listpage(@Valid @RequestBody TeamVo teamVo, HttpServletRequest request) {
        return ResponseDTO.ok(twUserTeamService.listpage(teamVo,request));
    }

    @PostMapping("/teamlist")
    @ApiOperation(value = "查看团队成员")
    public ResponseDTO<IPage<TwUser>> teamlist(@Valid @RequestBody TeamVo teamVo, HttpServletRequest request) {
        return ResponseDTO.ok(twUserTeamService.teamlist(teamVo,request));
    }
}
