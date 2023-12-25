package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwArea;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAreaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TwArea)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:18:24
 */
@RestController
@RequestMapping("twArea")
public class TwAreaController {
    /**
     * 服务对象
     */
    @Resource
    private TwAreaService twAreaService;


}

