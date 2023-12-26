package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAdminLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 后台管理员操作日志表(TwAdminLog)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:17:06
 */
@RestController
@RequestMapping("/api")
public class TwAdminLogController {
    /**
     * 服务对象
     */
    @Resource
    private TwAdminLogService twAdminLogService;



}

