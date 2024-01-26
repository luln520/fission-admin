package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwMessageRep;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAdminLogService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.domain.RequestUser;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.module.support.jwe.JweAspect;
import net.lab1024.sa.common.module.support.jwe.JweUserKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.function.Function;

/**
 * 管理员操作日志
 */
@RestController
@RequestMapping("/api/admin/adminlog")
@Api(tags = {AdminSwaggerTagConst.System.TW_ADMINLOG})
public class TwAdminLogController {

    @Autowired
    private TwAdminLogService twAdminLogService;

    private Function<HttpServletRequest, JweUserKey> userFunction;
    /**
     * 查询操作日志列表 表 AdminLog
     * order by id desc
     * */
    @PostMapping("/list")
    @ApiOperation(value = "管理员操作日志列表")
    public ResponseDTO<IPage<TwAdminLog>> listpage(@Valid @RequestBody TwBillVo twBillVo,HttpServletRequest request) {
        return ResponseDTO.ok(twAdminLogService.listpage(twBillVo,request));
    }

    /**
     *
     * 首页通知消息
     *
     * */
    @PostMapping("/message")
    @ApiOperation(value = "首页通知消息")
    public ResponseDTO<TwMessageRep> message(HttpServletRequest request) {
        return ResponseDTO.ok(twAdminLogService.message(request));
    }
}

