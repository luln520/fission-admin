package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHyorderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 合约订单表(TwHyorder)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:23:17
 */
@RestController
@RequestMapping("/api")
public class TwHyorderController {
    /**
     * 服务对象
     */
    @Resource
    private TwHyorderService twHyorderService;


}

