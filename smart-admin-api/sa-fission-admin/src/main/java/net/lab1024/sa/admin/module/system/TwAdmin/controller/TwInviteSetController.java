package net.lab1024.sa.admin.module.system.TwAdmin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwInviteSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwInviteSetService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwLeverSetService;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/teamset")
@Api(tags = {AdminSwaggerTagConst.System.TW_USERTEAMSET})
public class TwInviteSetController {

    @Autowired
    private TwInviteSetService twInviteSetService;

    @PostMapping("/list")
    @ApiOperation(value = "团队规则列表")
    public ResponseDTO<IPage<TwInviteSet>> listpage(@Valid @RequestBody PageParam pageParam) {
        return ResponseDTO.ok(twInviteSetService.listpage(pageParam));
    }

    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "团队规则新增或编辑")
    public ResponseDTO addOrUpdate(@RequestBody TwInviteSet twInviteSet) {
        return ResponseDTO.ok(twInviteSetService.addOrUpdate(twInviteSet));
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删新团队规则")
    public ResponseDTO delete(@RequestParam int id) {
        return ResponseDTO.ok(twInviteSetService.delete(id));
    }

    @GetMapping("/find")
    @ApiOperation(value = "查询团队规则")
    public ResponseDTO find(@RequestParam int id) {
        return ResponseDTO.ok(twInviteSetService.find(id));
    }
}
