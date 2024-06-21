package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserAgent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AgentVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserAgentService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.TwNewsVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/useragent")
@Api(tags = {AdminSwaggerTagConst.System.TW_USERAGENT})
public class TwUserAgentController {

    @Autowired
    private TwUserAgentService twUserAgentService;
    @PostMapping("/list")
    @ApiOperation(value = "会员代理列表")
    public ResponseDTO<IPage<TwUserAgent>> listpage(@Valid @RequestBody AgentVo agentVo, HttpServletRequest request) {
        return ResponseDTO.ok(twUserAgentService.listpage(agentVo,request));
    }

}
