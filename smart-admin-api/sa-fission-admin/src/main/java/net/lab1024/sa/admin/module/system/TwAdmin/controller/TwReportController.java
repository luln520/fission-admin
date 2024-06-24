package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwReport;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserAgent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AgentVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.ReportVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwReportService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserAgentService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/report")
@Api(tags = {AdminSwaggerTagConst.System.TW_REPORT})
public class TwReportController {

    @Autowired
    private TwReportService twReportService;
    @PostMapping("/list")
    @ApiOperation(value = "每天报表列表")
    public ResponseDTO<IPage<TwReport>> listpage(@Valid @RequestBody ReportVo reportVo, HttpServletRequest request) {
        return ResponseDTO.ok(twReportService.listpage(reportVo,request));
    }
}
