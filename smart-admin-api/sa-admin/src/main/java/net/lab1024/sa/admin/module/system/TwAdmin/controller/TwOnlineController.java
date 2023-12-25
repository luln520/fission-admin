package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwOnline;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwOnlineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TwOnline)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:27:16
 */
@RestController
@RequestMapping("twOnline")
public class TwOnlineController {
    /**
     * 服务对象
     */
    @Resource
    private TwOnlineService twOnlineService;



}

