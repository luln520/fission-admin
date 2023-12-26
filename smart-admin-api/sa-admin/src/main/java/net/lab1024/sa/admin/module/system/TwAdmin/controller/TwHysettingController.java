package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwHysettingService;

import javax.annotation.Resource;

/**
 * 合约参数(TwHysetting)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:23:33
 */
@RestController
@RequestMapping("twHysetting")
public class TwHysettingController {
    /**
     * 服务对象
     */
    @Resource
    private TwHysettingService twHysettingService;


}

