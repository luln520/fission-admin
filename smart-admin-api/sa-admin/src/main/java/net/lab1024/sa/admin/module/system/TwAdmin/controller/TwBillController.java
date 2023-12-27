package net.lab1024.sa.admin.module.system.TwAdmin.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwBillService;

import javax.annotation.Resource;

/**
 * 操作日志(TwBill)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:20:24
 */
@RestController
@RequestMapping("/api")
public class TwBillController {
    /**
     * 服务对象
     */
    @Resource
    private TwBillService twBillService;


}

