package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthGroupAccess;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAuthGroupAccessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

