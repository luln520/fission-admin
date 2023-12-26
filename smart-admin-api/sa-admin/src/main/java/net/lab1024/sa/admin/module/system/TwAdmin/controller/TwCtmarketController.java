package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwCtmarketService;

import javax.annotation.Resource;

/**
 * 合约交易对配置(TwCtmarket)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:21:46
 */
@RestController
@RequestMapping("twCtmarket")
public class TwCtmarketController {
    /**
     * 服务对象
     */
    @Resource
    private TwCtmarketService twCtmarketService;



}

