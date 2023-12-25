package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMarketJson;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMarketJsonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TwMarketJson)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:25:08
 */
@RestController
@RequestMapping("twMarketJson")
public class TwMarketJsonController {
    /**
     * 服务对象
     */
    @Resource
    private TwMarketJsonService twMarketJsonService;



}

