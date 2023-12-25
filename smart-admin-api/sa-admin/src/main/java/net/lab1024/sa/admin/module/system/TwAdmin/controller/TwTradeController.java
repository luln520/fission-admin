package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTrade;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwTradeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 交易下单表(TwTrade)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:27:48
 */
@RestController
@RequestMapping("twTrade")
public class TwTradeController {
    /**
     * 服务对象
     */
    @Resource
    private TwTradeService twTradeService;



}

