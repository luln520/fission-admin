package net.lab1024.sa.admin.module.system.TwPC.controller;

import cn.hutool.extra.servlet.ServletUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.lock.RedissonLockAnnotation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwC2c;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwPledge;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwPledgeOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwPledgeOrderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwPledgeService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.UserReq;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pc/pledge")
@Api(tags = {AdminSwaggerTagConst.PC.PC_PLEDGE})
public class PcPledgeController {

     @Autowired
     private TwPledgeOrderService twPledgeOrderService;

     @Autowired
     private TwPledgeService twPledgeService;

    @GetMapping("/setInfo")
    @ApiOperation(value = "贷款设置详情")
    public ResponseDTO<TwPledge> setInfo(@RequestParam int companyId) {
        return ResponseDTO.ok(twPledgeService.getTwPledge(companyId));
    }

    @GetMapping("/pledgeList")
    @ApiOperation(value = "用户获取提币列表")
    @NoNeedLogin
    public ResponseDTO<List<TwPledgeOrder>> pledgeList(@RequestParam int uid) {
        return twPledgeOrderService.pledgeList(uid);
    }

    @GetMapping("/info")
    @ApiOperation(value = "提币单条记录")
    @NoNeedLogin
    public ResponseDTO<TwPledgeOrder> info(@RequestParam String orderNo) {
        return twPledgeOrderService.info(orderNo);
    }

    @PostMapping("/pledge")
    @ApiOperation("借币提交")
    public ResponseDTO pledge( @RequestBody TwPledgeOrder twPledgeOrder) {
        return twPledgeOrderService.pledge(twPledgeOrder);
    }
}
