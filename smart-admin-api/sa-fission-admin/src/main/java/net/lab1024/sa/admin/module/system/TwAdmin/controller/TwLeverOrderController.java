package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwLeverOrderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwLeverageService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/leverorder")
@Api(tags = {AdminSwaggerTagConst.System.TW_LEVERORDER})
public class TwLeverOrderController {

    @Autowired
    private TwLeverOrderService twLeverOrderService;

    @PostMapping("/list")
    @ApiOperation(value = "杠杆订单列表")
    public ResponseDTO<IPage<TwLeverOrder>> listpage(@Valid @RequestBody LeverVo leverVo, HttpServletRequest request) {
        return ResponseDTO.ok(twLeverOrderService.listpage(leverVo,request));
    }


    @GetMapping("/editKonglo")
    @ApiOperation(value = "杠杆单控盈亏")
    public ResponseDTO editKonglo(@RequestParam Integer kongyk,@RequestParam Integer id) {
        return twLeverOrderService.editKonglo(kongyk,id);
    }

}
