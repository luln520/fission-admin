package net.lab1024.sa.admin.module.system.TwPC.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNewsType;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNewsService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNewsTypeService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.TwNewsVo;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 新闻
 */
@RestController
@RequestMapping("/api/pc/news")
@Api(tags = {AdminSwaggerTagConst.PC.TW_NEWS})
public class PcNewsController {

    @Autowired
    private TwNewsService twNewsService;

    @Autowired
    private TwNewsTypeService twNewsTypeService;


    @GetMapping("/listType")
    @ApiOperation(value = "新闻类型列表")
    @NoNeedLogin
    public ResponseDTO<List<TwNewsType>> listType() {
        return ResponseDTO.ok(twNewsTypeService.listType());
    }

    @GetMapping("/list")
    @ApiOperation(value = "新闻列表")
    @NoNeedLogin
    public ResponseDTO<List<TwNews>> list(@RequestParam String type) {
        return ResponseDTO.ok(twNewsService.listType(type));
    }

    @GetMapping("/findType")
    @ApiOperation(value = "类型查询新闻")
    @NoNeedLogin
    public ResponseDTO find(@RequestParam int id) {
        return ResponseDTO.ok(twNewsService.find(id));
    }
}
