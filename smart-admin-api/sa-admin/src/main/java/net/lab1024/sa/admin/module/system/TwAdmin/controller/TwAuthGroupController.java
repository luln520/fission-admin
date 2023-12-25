package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthGroup;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAuthGroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TwAuthGroup)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:19:04
 */
@RestController
@RequestMapping("twAuthGroup")
public class TwAuthGroupController {
    /**
     * 服务对象
     */
    @Resource
    private TwAuthGroupService twAuthGroupService;



}

