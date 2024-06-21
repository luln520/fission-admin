package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import io.swagger.annotations.Api;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/report")
@Api(tags = {AdminSwaggerTagConst.System.TW_REPORT})
public class TwReportController {
}
