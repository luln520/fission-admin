package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwConfig;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwConfigService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置获取
 */
@RestController
@RequestMapping("/api/pc/config")
public class PcConfigController {

    @Autowired
    private TwConfigService twConfigService;

    /**
     * 获取配置表信息
     * 表 config
     *  获取全部：select config  where   id=1 （查询不敏感的数据，剔除敏感数据）
     * */
    @GetMapping("/find")
    @ApiOperation(value = "查询网站信息，系统配置")
    @NoNeedLogin
    public ResponseDTO<TwConfig> find() {
        return ResponseDTO.ok(twConfigService.find());
    }



}

