package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwMyzcService;

import javax.annotation.Resource;

/**
 * 提币表(TwMyzc)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:26:37
 */
@RestController
@RequestMapping("twMyzc")
public class TwMyzcController {
    /**
     * 服务对象
     */
    @Resource
    private TwMyzcService twMyzcService;


}

