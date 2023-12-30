package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 币种
 */
@RestController
@RequestMapping("/api/pc/coin")
public class PcCoinController {

    @Autowired
    private TwCoinService twCoinService;

    /**
     * 币种列表
     * 表 coin
     * select coin  where  txstatus=1
     * */
    @GetMapping("/list")
    @ApiOperation(value = "账单列表")
    @NoNeedLogin
    public ResponseDTO<List<TwCoin>> lists() {
        return ResponseDTO.ok(twCoinService.lists());
    }
}

