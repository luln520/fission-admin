package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKuangjiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 矿机列表(TwKuangji)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:24:41
 */
@RestController
@RequestMapping("twKuangji")
public class TwKuangjiController {
    /**
     * 服务对象
     */
    @Resource
    private TwKuangjiService twKuangjiService;


}

