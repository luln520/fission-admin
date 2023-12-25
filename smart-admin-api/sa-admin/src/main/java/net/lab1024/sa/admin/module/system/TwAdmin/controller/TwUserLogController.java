package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户记录表(TwUserLog)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:29:10
 */
@RestController
@RequestMapping("twUserLog")
public class TwUserLogController {
    /**
     * 服务对象
     */
    @Resource
    private TwUserLogService twUserLogService;


}

