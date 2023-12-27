package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserQianbao;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserQianbaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户钱包表(TwUserQianbao)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:29:25
 */
@RestController
@RequestMapping("/api")
public class TwUserQianbaoController {
    /**
     * 服务对象
     */
    @Resource
    private TwUserQianbaoService twUserQianbaoService;



}

