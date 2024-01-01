package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCtmarketService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 市场配置
 */
@RestController
@RequestMapping("/api/admin/ctmarketConfig")
@Api(tags = {AdminSwaggerTagConst.System.TW_CTMARKET})
public class TwCtmarketConfigController {

    @Autowired
    private TwCtmarketService twCtmarketService;

    /**
     * 获取所有市场信息 表ctmarket order by sort asc
     */
    @PostMapping("/list")
    @ApiOperation(value = "市场配置列表")
    @NoNeedLogin
    public ResponseDTO<IPage<TwCtmarket>> listpage(@Valid @RequestBody PageParam pageParam) {
        return ResponseDTO.ok(twCtmarketService.listpage(pageParam));
    }

    /**
     * 设置 币状态 表Coin   修改status 1或者 2
     */
    @PostMapping("/updateStatus")
    @ApiOperation(value = "市场配置禁用启用")
    @NoNeedLogin
    public ResponseDTO updateStatus(@RequestParam int id, @RequestParam int status) {
        return ResponseDTO.ok(twCtmarketService.updateStatus(id,status));
    }

    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "市场配置新增或编辑")
    @NoNeedLogin
    public ResponseDTO addOrUpdate(@RequestBody TwCtmarket twCtmarket) {
        return ResponseDTO.ok(twCtmarketService.addOrUpdate(twCtmarket));
    }


    @GetMapping("/find")
    @ApiOperation(value = "查询市场配置")
    @NoNeedLogin
    public ResponseDTO find(@RequestParam int id) {
        return ResponseDTO.ok(twCtmarketService.find(id));
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除市场配置")
    @NoNeedLogin
    public ResponseDTO delete(@RequestParam int id) {
        return ResponseDTO.ok(twCtmarketService.delete(id));
    }
}

