package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNewsType;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNewsService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNewsTypeService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 新闻类型
 */
@RestController
@RequestMapping("/api/admin/newstype")
@Api(tags = {AdminSwaggerTagConst.System.TW_NEWSTYPE})
public class TwNewsTypeController {


    @Autowired
    private TwNewsTypeService twNewsTypeService;

    @PostMapping("/list")
    @ApiOperation(value = "新闻列表")
    @NoNeedLogin
    public ResponseDTO<List<TwNewsType>> listpage() {
        return ResponseDTO.ok(twNewsTypeService.listpage());
    }

    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "新闻新增或编辑")
    @NoNeedLogin
    public ResponseDTO addOrUpdate(@RequestBody TwNewsType twNewsType) {
        return ResponseDTO.ok(twNewsTypeService.addOrUpdate(twNewsType));
    }

    /**
     * 删除公告 表content where id=?
     */
    @GetMapping("/delete")
    @ApiOperation(value = "删除新闻")
    @NoNeedLogin
    public ResponseDTO delete(@RequestParam int id) {
        return ResponseDTO.ok(twNewsTypeService.delete(id));
    }

    @GetMapping("/find")
    @ApiOperation(value = "查询新闻")
    @NoNeedLogin
    public ResponseDTO find(@RequestParam int id) {
        return ResponseDTO.ok(twNewsTypeService.find(id));
    }
}
