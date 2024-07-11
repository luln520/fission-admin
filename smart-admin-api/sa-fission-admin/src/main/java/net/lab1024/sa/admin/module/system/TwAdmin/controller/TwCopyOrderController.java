package net.lab1024.sa.admin.module.system.TwAdmin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopyOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopyTake;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCopyOrderService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/copyorder")
@Api(tags = {AdminSwaggerTagConst.System.TW_COPYORDER})
public class TwCopyOrderController {

    @Autowired
    private TwCopyOrderService twCopyOrderService;

    @PostMapping("/list")
    @ApiOperation(value = "接单员列表")
    public ResponseDTO<IPage<TwCopyOrder>> listpage(@Valid @RequestBody TwUserVo twUserVo, HttpServletRequest request) {
        return ResponseDTO.ok(twCopyOrderService.listpage(twUserVo,request));
    }
}
