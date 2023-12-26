package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwKjprofitService;

import javax.annotation.Resource;

/**
 * 矿机收益表(TwKjprofit)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:24:29
 */
@RestController
@RequestMapping("twKjprofit")
public class TwKjprofitController {
    /**
     * 服务对象
     */
    @Resource
    private TwKjprofitService twKjprofitService;



}

