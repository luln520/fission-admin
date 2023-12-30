package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNoticeService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 通知管理
 */
@RestController
@RequestMapping("/api/admin/notice")
public class TwNoticeController {

    @Autowired
    private TwNoticeService twNoticeService;
    /**
     * 获取所有通知 表notice
     * order by id desc
     */
    @PostMapping("/list")
    @ApiOperation(value = "通知管理列表")
    @NoNeedLogin
    public ResponseDTO<IPage<TwNotice>> listpage(@Valid @RequestBody PageParam pageParam) {
        return ResponseDTO.ok(twNoticeService.listpage(pageParam));
    }

}

