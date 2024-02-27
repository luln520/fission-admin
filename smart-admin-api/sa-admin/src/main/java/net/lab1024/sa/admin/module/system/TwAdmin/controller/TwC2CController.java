package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwC2c;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.C2CVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwC2cService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * C2C
 *
 */
@RestController
@RequestMapping("/api/admin/c2c")
@Api(tags = {AdminSwaggerTagConst.System.TW_C2C})
public class TwC2CController {

    @Autowired
    private TwC2cService twC2cService;

    @PostMapping("/list")
    @ApiOperation(value = "C2C列表")
    public ResponseDTO<IPage<TwC2c>> listpage(@Valid @RequestBody C2CVo c2CVo, HttpServletRequest request) {
        return ResponseDTO.ok(twC2cService.listpage(c2CVo,request));
    }

    @GetMapping("/reject")
    @ApiOperation(value = "C2C充值审核驳回")
    public ResponseDTO reject(@RequestParam int  id) {
        return twC2cService.reject(id);
    }

    @GetMapping("/confirm")
    @ApiOperation(value = "C2C充币审核通过")
    public ResponseDTO confirm(@RequestParam int  id) {
        return twC2cService.confirm(id);
    }

    @GetMapping("/rejectCoin")
    @ApiOperation(value = "C2C驳回提币")
    public ResponseDTO rejectCoin(@RequestParam int  id) {
        return twC2cService.rejectCoin(id);
    }

    @GetMapping("/confirmCoin")
    @ApiOperation(value = "C2C通过提币")
    public ResponseDTO confirmCoin(@RequestParam int  id) {
        return twC2cService.confirmCoin(id);
    }



}
