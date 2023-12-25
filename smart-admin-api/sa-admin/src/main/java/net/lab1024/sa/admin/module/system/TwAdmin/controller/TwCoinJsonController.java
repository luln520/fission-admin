package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinJson;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinJsonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TwCoinJson)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:21:02
 */
@RestController
@RequestMapping("twCoinJson")
public class TwCoinJsonController {
    /**
     * 服务对象
     */
    @Resource
    private TwCoinJsonService twCoinJsonService;



}

