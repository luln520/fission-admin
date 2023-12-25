package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdver;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAdverService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 广告图片表(TwAdver)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:17:46
 */
@RestController
@RequestMapping("twAdver")
public class TwAdverController {
    /**
     * 服务对象
     */
    @Resource
    private TwAdverService twAdverService;


}

