package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwConfigService;

import javax.annotation.Resource;

/**
 * 网站配置表(TwConfig)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:21:16
 */
@RestController
@RequestMapping("twConfig")
public class TwConfigController {
    /**
     * 服务对象
     */
    @Resource
    private TwConfigService twConfigService;



}

