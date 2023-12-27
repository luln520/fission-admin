package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwAuthRuleService;

import javax.annotation.Resource;

/**
 * (TwAuthRule)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:19:36
 */
@RestController
@RequestMapping("/api")
public class TwAuthRuleController {
    /**
     * 服务对象
     */
    @Resource
    private TwAuthRuleService twAuthRuleService;



}

