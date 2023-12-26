package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwMenuService;

import javax.annotation.Resource;

/**
 * (TwMenu)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:26:22
 */
@RestController
@RequestMapping("twMenu")
public class TwMenuController {
    /**
     * 服务对象
     */
    @Resource
    private TwMenuService twMenuService;


}

