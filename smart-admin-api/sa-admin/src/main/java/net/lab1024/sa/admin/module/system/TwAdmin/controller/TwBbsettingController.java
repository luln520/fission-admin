package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBbsetting;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwBbsettingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 币币交易设置(TwBbsetting)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:20:11
 */
@RestController
@RequestMapping("twBbsetting")
public class TwBbsettingController {
    /**
     * 服务对象
     */
    @Resource
    private TwBbsettingService twBbsettingService;



}

