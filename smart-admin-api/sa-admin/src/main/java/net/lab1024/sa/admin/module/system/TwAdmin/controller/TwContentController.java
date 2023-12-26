package net.lab1024.sa.admin.module.system.TwAdmin.controller;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwContentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 公告内容(TwContent)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:21:31
 */
@RestController
@RequestMapping("/api")
public class TwContentController {
    /**
     * 服务对象
     */
    @Resource
    private TwContentService twContentService;


}

