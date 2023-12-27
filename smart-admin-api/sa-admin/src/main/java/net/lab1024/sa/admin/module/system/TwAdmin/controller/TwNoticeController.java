package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNoticeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 通知表(TwNotice)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:27:04
 */
@RestController
@RequestMapping("/api")
public class TwNoticeController {
    /**
     * 服务对象
     */
    @Resource
    private TwNoticeService twNoticeService;



}

