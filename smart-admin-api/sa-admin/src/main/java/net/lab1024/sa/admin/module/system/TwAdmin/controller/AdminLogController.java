package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAdminLogService;
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
 * 管理员操作日志
 */
@RestController
@RequestMapping("/api/admin/adminlog")
public class AdminLogController {

    @Autowired
    private TwAdminLogService twAdminLogService;

    /**
     * 查询操作日志列表 表 AdminLog
     * order by id desc
     * */
    @PostMapping("/list")
    @ApiOperation(value = "管理员操作日志列表")
    @NoNeedLogin
    public ResponseDTO<IPage<TwAdminLog>> listpage(@Valid @RequestBody TwBillVo twBillVo) {
        return ResponseDTO.ok(twAdminLogService.listpage(twBillVo));
    }

    /**
     * 新增或者编辑 表 AdminLog   有id 编辑  无id 新增  对象直接存 （接口可以删，server要实现  管理员操作日志需要写入调取）
     */

}

