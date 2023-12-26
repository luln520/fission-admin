package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwIssueService;

import javax.annotation.Resource;

/**
 * 认购发行表(TwIssue)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:23:47
 */
@RestController
@RequestMapping("twIssue")
public class TwIssueController {
    /**
     * 服务对象
     */
    @Resource
    private TwIssueService twIssueService;



}

