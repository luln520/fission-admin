package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwConfig;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAdminLogService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwConfigService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * 网站信息 和 系统配置 （同一个表）
 */
@RestController
@RequestMapping("/api/admin/webConfig")
public class WebConfigController {

    @Autowired
    private TwConfigService twConfigService;

    @GetMapping("/find")
    @ApiOperation(value = "查询网站信息，系统配置")
    @NoNeedLogin
    public ResponseDTO find(@RequestParam int id) {
        return ResponseDTO.ok(twConfigService.find(id));
    }

    @GetMapping("/addOrUpdate")
    @ApiOperation(value = "修改网站信息，系统配置")
    @NoNeedLogin
    public ResponseDTO addOrUpdate(@RequestBody TwConfig twConfig) {
        return ResponseDTO.ok(twConfigService.addOrUpdate(twConfig));
    }

}

