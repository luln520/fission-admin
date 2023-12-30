package net.lab1024.sa.admin.module.system.TwPC.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwContentService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 公告
 */
@RestController
@RequestMapping("/api/pc/content")
public class PcContentController {

    @Autowired
    private TwContentService twContentService;
    /**
     * 公告列表
     * 表 content
     * select content  where  status=1
     * */

    @PostMapping("/list")
    @ApiOperation(value = "获取所有公告")
    @NoNeedLogin
    public ResponseDTO<IPage<TwContent>> listContentPage(@Valid @RequestBody PageParam pageParam) {
        return ResponseDTO.ok(twContentService.listPCpage(pageParam));
    }
    /**
     * 公告详情
     * 参数： id
     * 表 content
     * select content  where  id=?
     * */
    @GetMapping("/detail")
    @ApiOperation(value = "获取所有公告")
    @NoNeedLogin
    public ResponseDTO<TwContent> detail(@RequestParam int id) {
        return ResponseDTO.ok(twContentService.find(id));
    }

}

