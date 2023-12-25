package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthExtend;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAuthExtendService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TwAuthExtend)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:18:46
 */
@RestController
@RequestMapping("twAuthExtend")
public class TwAuthExtendController {
    /**
     * 服务对象
     */
    @Resource
    private TwAuthExtendService twAuthExtendService;


}

