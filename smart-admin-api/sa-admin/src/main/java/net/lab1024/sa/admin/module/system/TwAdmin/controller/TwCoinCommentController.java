package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinComment;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TwCoinComment)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:20:50
 */
@RestController
@RequestMapping("twCoinComment")
public class TwCoinCommentController {
    /**
     * 服务对象
     */
    @Resource
    private TwCoinCommentService twCoinCommentService;



}

