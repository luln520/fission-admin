package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwKuangjiService;

import javax.annotation.Resource;

/**
 * 矿机列表(TwKuangji)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:24:41
 */
@RestController
@RequestMapping("/api")
public class TwKuangjiController {
    /**
     * 服务对象
     */
    @Resource
    private TwKuangjiService twKuangjiService;


}

