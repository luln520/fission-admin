package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwPledge;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwPledgeOrderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwPledgeService;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * pledge
 *
 */
@RestController
@RequestMapping("/api/admin/pledge")
@Api(tags = {AdminSwaggerTagConst.System.TW_PLEDGE})
public class TwPledgeController {

    @Autowired
    private TwPledgeService twPledgeService;

    @Autowired
    private TwPledgeOrderService twPledgeOrderService;

    @GetMapping("/info")
    @ApiOperation(value = "贷款详情")
    public ResponseDTO<TwPledge> info(@RequestParam int companyId) {
        return ResponseDTO.ok(twPledgeService.getTwPledge(companyId));
    }


    @PostMapping("/edit")
    @ApiOperation(value = "编辑")
    public ResponseDTO edit(@RequestBody TwPledge twPledge) {
        return twPledgeService.edit(twPledge);
    }


    @GetMapping("/reject")
    @ApiOperation(value = "贷款审核驳回")
    public ResponseDTO reject(@RequestParam int  id) {
        return twPledgeOrderService.reject(id);
    }

    @GetMapping("/confirm")
    @ApiOperation(value = "贷款审核通过")
    public ResponseDTO confirm(@RequestParam int  id) {
        return twPledgeOrderService.confirm(id);
    }


}
