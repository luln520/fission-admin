package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNewsService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新闻
 */
@RestController
@RequestMapping("/api/pc/news")
@Api(tags = {AdminSwaggerTagConst.PC.PC_NEWS})
public class PcNewsController {

    @Autowired
    private TwNewsService twNewsService;


    @GetMapping("/list")
    @ApiOperation(value = "新闻列表")
    @NoNeedLogin
    public ResponseDTO<List<TwNews>> list(@RequestParam int companyId) {
        return ResponseDTO.ok(twNewsService.listType(companyId));
    }

    @GetMapping("/findType")
    @ApiOperation(value = "类型查询新闻")
    @NoNeedLogin
    public ResponseDTO find(@RequestParam int id) {
        return ResponseDTO.ok(twNewsService.find(id));
    }
}
