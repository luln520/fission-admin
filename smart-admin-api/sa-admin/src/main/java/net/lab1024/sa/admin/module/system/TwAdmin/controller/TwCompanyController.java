package net.lab1024.sa.admin.module.system.TwAdmin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.CompanyVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/company")
@Api(tags = {AdminSwaggerTagConst.System.TW_COMPANY})
public class TwCompanyController {

    @Autowired
    private TwCompanyService twCompanyService;

    @PostMapping("/list")
    @ApiOperation(value = "公司列表")
    public ResponseDTO<IPage<TwCompany>> listpage(@Valid @RequestBody CompanyVo companyVo) {
        return ResponseDTO.ok(twCompanyService.listpage(companyVo));
    }

    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "公司新增或编辑")
    public ResponseDTO addOrUpdate(@RequestBody TwCompany twCompany) {
        return twCompanyService.addOrUpdate(twCompany);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "公司详情")
    public ResponseDTO detail(@RequestParam int id) {
        return ResponseDTO.ok(twCompanyService.detail(id));
    }


    @GetMapping("/updateStatus")
    @ApiOperation(value = "公司禁用启用")
    public ResponseDTO updateStatus(@RequestParam int id,@RequestParam Integer status) {
        return ResponseDTO.ok(twCompanyService.updateStatus(id,status));
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除公司")
    public ResponseDTO delete(@RequestParam int id) {
        return ResponseDTO.ok(twCompanyService.delete(id));
    }


}
