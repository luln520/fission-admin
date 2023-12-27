package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwTradeLogService;

import javax.annotation.Resource;

/**
 * (TwTradeLog)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:28:13
 */
@RestController
@RequestMapping("/api")
public class TwTradeLogController {
    /**
     * 服务对象
     */
    @Resource
    private TwTradeLogService twTradeLogService;


}

