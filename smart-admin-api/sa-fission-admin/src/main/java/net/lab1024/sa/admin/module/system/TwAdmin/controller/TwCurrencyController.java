package net.lab1024.sa.admin.module.system.TwAdmin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCurrency;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.CompanyVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCurrencyService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/currency")
@Api(tags = {AdminSwaggerTagConst.System.TW_CURRENCY})
public class TwCurrencyController {

    @Autowired
    private TwCurrencyService twCurrencyService;

    @PostMapping("/list")
    @ApiOperation(value = "货币列表")
    public ResponseDTO<IPage<TwCurrency>> listpage(@Valid @RequestBody CompanyVo companyVo) {
        return ResponseDTO.ok(twCurrencyService.listpage(companyVo));
    }

    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "货币新增或编辑")
    public ResponseDTO addOrUpdate(@RequestBody TwCurrency twCurrency) {
        return twCurrencyService.addOrUpdate(twCurrency);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "货币详情")
    public ResponseDTO detail(@RequestParam int id) {
        return ResponseDTO.ok(twCurrencyService.detail(id));
    }


    @GetMapping("/delete")
    @ApiOperation(value = "删除货币")
    public ResponseDTO delete(@RequestParam int id) {
        return ResponseDTO.ok(twCurrencyService.delete(id));
    }
}
