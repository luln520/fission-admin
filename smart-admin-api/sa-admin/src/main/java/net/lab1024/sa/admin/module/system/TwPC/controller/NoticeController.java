package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNoticeService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.UserReq;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 通知
 */
@RestController
@RequestMapping("/api/pc/notice")
public class NoticeController {

    @Autowired
    private TwNoticeService twNoticeService;

    /**
     * 通知列表
     * 表 notice
     * 参数： id （用户 id）
     * select notice  where  uid=？
     * */
    @NoNeedLogin
    @GetMapping("/notice")
    @ApiOperation("通知列表")
    public ResponseDTO<List<TwNotice>> notice(@RequestParam int uid) {
        return twNoticeService.notice(uid);
    }

    /**
     * 通知详情
     * 参数： id （ 公告 id）
     * 表 notice
     * select notice  where  id=?
     * */

    @NoNeedLogin
    @GetMapping("/noticeDetail")
    @ApiOperation("通知详情")
    public ResponseDTO<TwNotice> noticeDetail(@RequestParam int id) {
        return twNoticeService.noticeDetail(id);
    }

}

