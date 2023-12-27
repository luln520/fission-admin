package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwAppcService;

import javax.annotation.Resource;

/**
 * (TwAppc)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:18:07
 */
@RestController
@RequestMapping("/api")
public class TwAppcController {
    /**
     * 服务对象
     */
    @Resource
    private TwAppcService twAppcService;



}

