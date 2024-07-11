package net.lab1024.sa.admin.module.system.TwAdmin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopyTake;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCopyTakeService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/copytake")
@Api(tags = {AdminSwaggerTagConst.System.TW_COPYTAKE})
public class TwCopyTakeController {

    @Autowired
    private TwCopyTakeService twCopyTakeService;

    @PostMapping("/list")
    @ApiOperation(value = "接单员列表")
    public ResponseDTO<IPage<TwCopyTake>> listpage(@Valid @RequestBody TwUserVo twUserVo, HttpServletRequest request) {
        return ResponseDTO.ok(twCopyTakeService.listpage(twUserVo,request));
    }

    @GetMapping("/win")
    @ApiOperation(value = "结算赢")
    public ResponseDTO win(@RequestParam String orderNo,@RequestParam int type) {
        return ResponseDTO.ok(twCopyTakeService.win(orderNo,type));
    }

    @GetMapping("/loss")
    @ApiOperation(value = "结算输")
    public ResponseDTO loss(@RequestParam String orderNo,@RequestParam int type) {
        return ResponseDTO.ok(twCopyTakeService.loss(orderNo,type));
    }

}
