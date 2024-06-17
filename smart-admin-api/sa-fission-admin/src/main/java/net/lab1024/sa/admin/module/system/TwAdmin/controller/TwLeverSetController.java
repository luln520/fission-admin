package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwLeverSetService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwLeverageService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/leverset")
@Api(tags = {AdminSwaggerTagConst.System.TW_LEVERSET})
public class TwLeverSetController {

    @Autowired
    private TwLeverSetService twLeverSetService;

    @PostMapping("/list")
    @ApiOperation(value = "杠杆止盈止损设置列表")
    public ResponseDTO<IPage<TwLeverSet>> listpage(@Valid @RequestBody LeverVo leverVo) {
        return ResponseDTO.ok(twLeverSetService.listpage(leverVo));
    }

    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "杠杆止盈止损设置新增或编辑")
    public ResponseDTO addOrUpdate(@RequestBody TwLeverSet twLeverSet) {
        return ResponseDTO.ok(twLeverSetService.addOrUpdate(twLeverSet));
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删新杠杆止盈止损设置")
    public ResponseDTO delete(@RequestParam int id) {
        return ResponseDTO.ok(twLeverSetService.delete(id));
    }

    @GetMapping("/find")
    @ApiOperation(value = "查询杠杆止盈止损设置")
    public ResponseDTO find(@RequestParam int id) {
        return ResponseDTO.ok(twLeverSetService.find(id));
    }
}
