package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwMarketJsonService;

import javax.annotation.Resource;

/**
 * (TwMarketJson)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:25:08
 */
@RestController
@RequestMapping("/api")
public class TwMarketJsonController {
    /**
     * 服务对象
     */
    @Resource
    private TwMarketJsonService twMarketJsonService;



}

