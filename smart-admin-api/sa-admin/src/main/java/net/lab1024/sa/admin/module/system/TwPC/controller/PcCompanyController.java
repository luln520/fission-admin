package net.lab1024.sa.admin.module.system.TwPC.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCompany;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.CompanyVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCompanyService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/pc/company")
@Api(tags = {AdminSwaggerTagConst.PC.PC_COMPANY})
public class PcCompanyController {

    @Autowired
    private TwCompanyService twCompanyService;

    @GetMapping("/domain")
    @NoNeedLogin
    @ApiOperation(value = "根据域名获取公司信息")
    public ResponseDTO<TwCompany> companyDomain(@RequestParam String domain) {
        return ResponseDTO.ok(twCompanyService.companyDomain(domain));
    }

    @GetMapping("/info")
    @NoNeedLogin
    @ApiOperation(value = "根据id获取公司信息")
    public ResponseDTO<TwCompany> companyId(@RequestParam int id) {
        return ResponseDTO.ok(twCompanyService.detail(id));
    }


}
