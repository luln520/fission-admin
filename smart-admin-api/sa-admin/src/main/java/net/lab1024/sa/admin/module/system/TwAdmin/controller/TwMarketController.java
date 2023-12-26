package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMarket;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMarketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 行情配置表(TwMarket)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:24:55
 */
@RestController
@RequestMapping("/api")
public class TwMarketController {
    /**
     * 服务对象
     */
    @Resource
    private TwMarketService twMarketService;


}

