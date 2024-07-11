package net.lab1024.sa.admin.module.system.TwAdmin.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCopySet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHysetting;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCopySetService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/copyset")
@Api(tags = {AdminSwaggerTagConst.System.TW_COPYSET})
public class TwCopySetController {
    @Autowired
    private TwCopySetService twCopySetService;


    /**
     * 合约设置 获取 并返回    表hysetting where id=1
     */
    @GetMapping("/hysettingId")
    @ResponseBody
    @ApiOperation(value = "合约设置")
    public ResponseDTO hysettingId(@RequestParam int companyId) {
        return ResponseDTO.ok(twCopySetService.hysettingId(companyId));
    }

    /**
     * 合约设置 编辑    表hysetting where id=1  对象直接保存
     */
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "合约设置 编辑")
    public ResponseDTO edit(@RequestBody TwCopySet twCopySet) {
        return ResponseDTO.ok(twCopySetService.edit(twCopySet));
    }

}
