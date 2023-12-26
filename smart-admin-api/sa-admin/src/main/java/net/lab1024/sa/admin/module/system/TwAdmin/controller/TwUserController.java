package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TwUserService;

import javax.annotation.Resource;

/**
 * 用户信息表(TwUser)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:28:46
 */
@RestController
@RequestMapping("twUser")
public class TwUserController {
    /**
     * 服务对象
     */
    @Resource
    private TwUserService twUserService;


}

