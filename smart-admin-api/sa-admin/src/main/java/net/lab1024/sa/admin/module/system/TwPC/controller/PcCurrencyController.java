package net.lab1024.sa.admin.module.system.TwPC.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCurrency;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.CompanyVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCurrencyService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/currency")
@Api(tags = {AdminSwaggerTagConst.PC.PC_CURRENCY})
public class PcCurrencyController {

    @Autowired
    private TwCurrencyService twCurrencyService;

    @GetMapping("/list")
    @ApiOperation(value = "货币列表")
    public ResponseDTO lists(@RequestParam int companyId) {
        return twCurrencyService.lists(companyId);
    }

}
