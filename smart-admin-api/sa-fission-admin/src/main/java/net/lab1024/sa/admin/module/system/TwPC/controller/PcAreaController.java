package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwArea;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAreaService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pc/area")
@Api(tags = {AdminSwaggerTagConst.PC.PC_AREA})
public class PcAreaController {

    @Autowired
    private TwAreaService twAreaService;

    @GetMapping("/list")
    @ApiOperation(value = "获取区号")
    @NoNeedLogin
    public ResponseDTO<List<TwArea>> list() {
        return ResponseDTO.ok(twAreaService.lists());
    }
}
