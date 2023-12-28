package net.lab1024.sa.admin.module.system.TwAdmin.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwContentService;
import net.lab1024.sa.admin.module.system.employee.domain.form.EmployeeQueryForm;
import net.lab1024.sa.admin.module.system.employee.domain.vo.EmployeeVO;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.PageResult;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 公告内容(TwContent)表控制层
 *
 * @author makejava
 * @since 2023-12-23 18:21:31
 */
@RestController
@RequestMapping("/api/admin/article")
@Api(tags = {AdminSwaggerTagConst.System.TW_CONTENT})
public class TwContentController {
    /**
     * 服务对象
     */
    @Resource
    private TwContentService twContentService;


    /**
     * 获取所有公告  表content 全部查询
     */
    @PostMapping("/list")
    @ApiOperation(value = "公告新增列表")
    @NoNeedLogin
    public ResponseDTO<IPage<TwContent>> listpage(@Valid @RequestBody PageParam pageParam) {
        return ResponseDTO.ok(twContentService.listpage(pageParam));
    }

    /**
     * 修改公告 公告对象  表content  直接改 where id=?
     */
    @PostMapping("/addOrUpdate")
    @ApiOperation(value = "公告新增或编辑")
    @NoNeedLogin
    public ResponseDTO addOrUpdate(@RequestBody TwContent twContent) {
        return ResponseDTO.ok(twContentService.addOrUpdate(twContent));
    }

    /**
     * 删除公告 表content where id=?
     */
    @GetMapping("/delete")
    @ApiOperation(value = "删除公告")
    @NoNeedLogin
    public ResponseDTO delete(@RequestParam int id) {
        return ResponseDTO.ok(twContentService.delete(id));
    }

    @GetMapping("/find")
    @ApiOperation(value = "查询公告")
    @NoNeedLogin
    public ResponseDTO find(@RequestParam int id) {
        return ResponseDTO.ok(twContentService.find(id));
    }
}

