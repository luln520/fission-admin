package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwIssueLogService;

import javax.annotation.Resource;

/**
 * 认购记录表(TwIssueLog)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:24:00
 */
@RestController
@RequestMapping("/api")
public class TwIssueLogController {
    /**
     * 服务对象
     */
    @Resource
    private TwIssueLogService twIssueLogService;


}

