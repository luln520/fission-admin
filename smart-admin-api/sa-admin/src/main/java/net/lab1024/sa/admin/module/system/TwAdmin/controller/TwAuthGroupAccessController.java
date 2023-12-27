package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwAuthGroupAccessService;

import javax.annotation.Resource;

/**
 * (TwAuthGroupAccess)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:19:21
 */
@RestController
@RequestMapping("/api")
public class TwAuthGroupAccessController {
    /**
     * 服务对象
     */
    @Resource
    private TwAuthGroupAccessService twAuthGroupAccessService;


}

