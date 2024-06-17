package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwLeverageService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNewsService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.TwNewsVo;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/leverage")
@Api(tags = {AdminSwaggerTagConst.System.TW_LEVERAGE})
public class TwLeverageController {

    @Autowired
    private TwLeverageService twLeverageService;

    @PostMapping("/list")
    @ApiOperation(value = "杠杆倍数列表")
    public ResponseDTO<IPage<TwLeverage>> listpage(@Valid @RequestBody LeverVo leverVo) {
        return ResponseDTO.ok(twLeverageService.listpage(leverVo));
    }

    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "杠杆倍数新增或编辑")
    public ResponseDTO addOrUpdate(@RequestBody TwLeverage twLeverage) {
        return ResponseDTO.ok(twLeverageService.addOrUpdate(twLeverage));
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删新杠杆倍数")
    public ResponseDTO delete(@RequestParam int id) {
        return ResponseDTO.ok(twLeverageService.delete(id));
    }

    @GetMapping("/find")
    @ApiOperation(value = "查询杠杆倍数")
    public ResponseDTO find(@RequestParam int id) {
        return ResponseDTO.ok(twLeverageService.find(id));
    }
}
