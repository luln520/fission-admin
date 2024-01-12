package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwContentService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNewsService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 新闻
 */
@RestController
@RequestMapping("/api/admin/news")
@Api(tags = {AdminSwaggerTagConst.System.TW_NEWS})
public class TwNewsController {

    @Autowired
    private TwNewsService twNewsService;

    @PostMapping("/list")
    @ApiOperation(value = "新闻列表")
    @NoNeedLogin
    public ResponseDTO<IPage<TwNews>> listpage(@Valid @RequestBody PageParam pageParam) {
        return ResponseDTO.ok(twNewsService.listpage(pageParam));
    }

    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "新闻新增或编辑")
    @NoNeedLogin
    public ResponseDTO addOrUpdate(@RequestBody TwNews twNews) {
        return ResponseDTO.ok(twNewsService.addOrUpdate(twNews));
    }

    /**
     * 删除公告 表content where id=?
     */
    @GetMapping("/delete")
    @ApiOperation(value = "删除新闻")
    @NoNeedLogin
    public ResponseDTO delete(@RequestParam int id) {
        return ResponseDTO.ok(twNewsService.delete(id));
    }

    @GetMapping("/find")
    @ApiOperation(value = "查询新闻")
    @NoNeedLogin
    public ResponseDTO find(@RequestParam int id) {
        return ResponseDTO.ok(twNewsService.find(id));
    }
}
