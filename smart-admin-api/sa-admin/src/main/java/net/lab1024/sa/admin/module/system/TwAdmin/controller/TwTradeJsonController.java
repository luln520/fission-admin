package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwTradeJsonService;

import javax.annotation.Resource;

/**
 * 交易图表表(TwTradeJson)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:28:00
 */
@RestController
@RequestMapping("twTradeJson")
public class TwTradeJsonController {
    /**
     * 服务对象
     */
    @Resource
    private TwTradeJsonService twTradeJsonService;



}

