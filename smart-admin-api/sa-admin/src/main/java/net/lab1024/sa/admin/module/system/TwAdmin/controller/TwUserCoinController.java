package net.lab1024.sa.admin.module.system.TwAdmin.controller;;

import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCoinService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户币种表(TwUserCoin)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:28:58
 */
@RestController
@RequestMapping("twUserCoin")
public class TwUserCoinController {
    /**
     * 服务对象
     */
    @Resource
    private TwUserCoinService twUserCoinService;


}

