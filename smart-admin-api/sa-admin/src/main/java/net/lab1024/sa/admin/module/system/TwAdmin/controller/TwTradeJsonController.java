package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTradeJson;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwTradeJsonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 交易图表表(TwTradeJson)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:28:00
 */
@RestController
@RequestMapping("/api")
public class TwTradeJsonController {
    /**
     * 服务对象
     */
    @Resource
    private TwTradeJsonService twTradeJsonService;



}

