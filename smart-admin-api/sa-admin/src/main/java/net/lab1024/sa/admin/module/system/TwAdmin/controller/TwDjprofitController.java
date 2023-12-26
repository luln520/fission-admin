package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwDjprofitService;

import javax.annotation.Resource;

/**
 * 数字币冻结记录表(TwDjprofit)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:22:14
 */
@RestController
@RequestMapping("twDjprofit")
public class TwDjprofitController {
    /**
     * 服务对象
     */
    @Resource
    private TwDjprofitService twDjprofitService;



}

