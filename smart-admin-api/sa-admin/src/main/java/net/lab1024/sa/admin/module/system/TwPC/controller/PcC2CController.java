package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwArea;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwC2c;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwC2cBank;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAreaService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwC2cService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * C2C
 *
 */
@RestController
@RequestMapping("/api/pc/c2c")
@Api(tags = {AdminSwaggerTagConst.PC.PC_C2C})
public class PcC2CController {

    @Autowired
    private TwC2cService twC2cService;


    @Autowired
    private TwAreaService twAreaService;

    @GetMapping("/arealist")
    @ApiOperation(value = "获取国家")
    @NoNeedLogin
    public ResponseDTO<List<TwArea>> arealist() {
        return ResponseDTO.ok(twAreaService.lists());
    }

    @GetMapping("/cz")
    @ApiOperation(value = "C2C充值")
    @NoNeedLogin
    public ResponseDTO<TwC2c> cz(@RequestParam int  uid, @RequestParam int countryId, @RequestParam BigDecimal num,@RequestParam int bankType,@RequestParam String language) {
        return twC2cService.cz(uid,countryId,num,bankType,language);
    }

    @GetMapping("/czImg")
    @ApiOperation(value = "C2C充值转账交易图片")
    @NoNeedLogin
    public ResponseDTO<TwC2c> czImg(@RequestParam String  orderNo,@RequestParam String img,@RequestParam String language) {
        return twC2cService.czImg(orderNo,img,language);
    }

    @PostMapping("/tx")
    @ApiOperation(value = "C2C提现")
    @NoNeedLogin
    public ResponseDTO<TwC2c> c2ctx(@RequestBody TwC2cBank twC2cBank) {
        return twC2cService.c2ctx(twC2cBank);
    }

    @GetMapping("/czList")
    @ApiOperation(value = "用户获取充值,提款记录(type:1.充值，2.提现)")
    @NoNeedLogin
    public ResponseDTO<List<TwC2c>> czList(@RequestParam int type,@RequestParam int uid) {
        return twC2cService.czList(type,uid);
    }

    @GetMapping("/info")
    @ApiOperation(value = "用充值 提款 单条记录")
    @NoNeedLogin
    public ResponseDTO<TwC2c> info(@RequestParam String orderNo) {
        return twC2cService.info(orderNo);
    }
}
