package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCurrenyListService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/currencylist")
@Api(tags = {AdminSwaggerTagConst.System.TW_CURRENCY})
public class TwCurrenyListController {

    @Autowired
    private TwCurrenyListService twCurrenyListService;
    @GetMapping("/add")
    @ApiOperation(value = "货币汇率总表")
    @NoNeedLogin
    public ResponseDTO deaddtail() {
        return ResponseDTO.ok(twCurrenyListService.add());
    }
}
