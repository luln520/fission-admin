package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTyhyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwTyhyorderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 合约订单表(TwTyhyorder)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:28:27
 */
@RestController
@RequestMapping("twTyhyorder")
public class TwTyhyorderController {
    /**
     * 服务对象
     */
    @Resource
    private TwTyhyorderService twTyhyorderService;


}

