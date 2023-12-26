package net.lab1024.sa.admin.module.system.TwAdmin.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwContentService;

import javax.annotation.Resource;

/**
 * 公告内容(TwContent)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:21:31
 */
@RestController
@RequestMapping("twContent")
public class TwContentController {
    /**
     * 服务对象
     */
    @Resource
    private TwContentService twContentService;


}

