package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwBillService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 资金流水
 */
@RestController
@RequestMapping("/api/admin/amountlog")
@Api(tags = {AdminSwaggerTagConst.System.TW_AMOUNTLOG})
public class TwAmountLogController {

    @Autowired
    private TwBillService twBillService;

    /**
     * 获取所有 表bill
     * order by id desc
     */
    @PostMapping("/list")
    @ApiOperation(value = "资金流水列表")
    public ResponseDTO<IPage<TwBill>> listpage(@Valid @RequestBody TwBillVo twBillVo, HttpServletRequest request) {
        return ResponseDTO.ok(twBillService.listpage(twBillVo,request));
    }


}

