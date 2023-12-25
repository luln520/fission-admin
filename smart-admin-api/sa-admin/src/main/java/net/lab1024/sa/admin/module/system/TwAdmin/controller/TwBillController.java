package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwBillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 操作日志(TwBill)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:20:24
 */
@RestController
@RequestMapping("twBill")
public class TwBillController {
    /**
     * 服务对象
     */
    @Resource
    private TwBillService twBillService;


}

