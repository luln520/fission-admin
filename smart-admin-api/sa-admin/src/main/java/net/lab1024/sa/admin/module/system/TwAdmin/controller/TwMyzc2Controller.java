package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwMyzc2Service;

import javax.annotation.Resource;

/**
 * 提币表(TwMyzc2)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:26:51
 */
@RestController
@RequestMapping("/api")
public class TwMyzc2Controller {
    /**
     * 服务对象
     */
    @Resource
    private TwMyzc2Service twMyzc2Service;



}

