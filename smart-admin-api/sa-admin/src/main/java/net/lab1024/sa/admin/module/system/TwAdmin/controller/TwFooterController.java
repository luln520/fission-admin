package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwFooterService;

import javax.annotation.Resource;

/**
 * (TwFooter)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:22:59
 */
@RestController
@RequestMapping("twFooter")
public class TwFooterController {
    /**
     * 服务对象
     */
    @Resource
    private TwFooterService twFooterService;


}

