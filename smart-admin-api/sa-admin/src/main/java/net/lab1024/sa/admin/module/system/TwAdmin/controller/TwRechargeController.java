package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwRechargeService;

import javax.annotation.Resource;

/**
 * 充值记录(TwRecharge)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:27:30
 */
@RestController
@RequestMapping("/api")
public class TwRechargeController {
    /**
     * 服务对象
     */
    @Resource
    private TwRechargeService twRechargeService;


}

