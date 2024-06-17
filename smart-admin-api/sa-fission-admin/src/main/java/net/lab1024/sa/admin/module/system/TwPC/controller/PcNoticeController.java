package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNoticeService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知
 */
@RestController
@RequestMapping("/api/pc/notice")
@Api(tags = {AdminSwaggerTagConst.PC.PC_NOTICE})
public class PcNoticeController {

    @Autowired
    private TwNoticeService twNoticeService;

    /**
     * 通知列表
     * 表 notice
     * 参数： id （用户 id）
     * select notice  where  uid=？
     * */
    @GetMapping("/list")
    @ApiOperation("通知列表")
    @NoNeedLogin
    public ResponseDTO<List<TwNotice>> list(@RequestParam int uid,@RequestParam int companyId) {
        return twNoticeService.notice(uid,companyId);
    }

    /**
     * 通知详情
     * 参数： id （ 公告 id）
     * 表 notice
     * select notice  where  id=?
     * */

    @GetMapping("/noticeDetail")
    @ApiOperation("通知详情")
    @NoNeedLogin
    public ResponseDTO<TwNotice> noticeDetail(@RequestParam int id) {
        return twNoticeService.noticeDetail(id);
    }

    /**
     * 标记全部已经读取
     * 参数：token
     * 表 notice
     * update notice set status=2  where  uid=?（通过 token 拿到id）
     * */
    @GetMapping("/read")
    @ApiOperation("标记全部已经读取")
    @NoNeedLogin
    public ResponseDTO read(@RequestParam String token) {
        return twNoticeService.read(token);
    }

    /**
     * 标记单个已经读取
     * 参数：id(notice的id)
     * 表 notice
     * update notice set status=2  where  id=?
     * */
    @GetMapping("/readone")
    @ApiOperation("标记单个已经读取")
    @NoNeedLogin
    public ResponseDTO readone(@RequestParam int id) {
        return twNoticeService.readone(id);
    }
    /**
     * 删除我的全部通知
     * 参数： token
     * 表 notice
     * delete notice  where  uid=?（通过 token 拿到id）
     * */
    @GetMapping("/delete")
    @ApiOperation("删除我的全部通知")
    @NoNeedLogin
    public ResponseDTO delete(@RequestParam String token) {
        return twNoticeService.delete(token);
    }


    /**
     * 删除我单个通知
     * 参数： id(notice的id)
     * 表 notice
     * delete notice  where  id=?
     * */
    @GetMapping("/deleteOne")
    @ApiOperation("删除我单个通知")
    @NoNeedLogin
    public ResponseDTO deleteOne(@RequestParam int id) {
        return twNoticeService.deleteOne(id);
    }

}

