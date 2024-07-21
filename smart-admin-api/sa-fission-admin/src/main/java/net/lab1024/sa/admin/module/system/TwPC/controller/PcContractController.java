package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.lock.RedissonLockAnnotation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHysetting;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHyorderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHysettingService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.HyorderOneRes;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合约
 */
@RestController
@RequestMapping("/api/pc/contract")
@Api(tags = {AdminSwaggerTagConst.PC.PC_CONTRACT})
public class PcContractController {

    @Autowired
    private TwHyorderService twHyorderService;

    @Autowired
    private TwHysettingService twHysettingService;

    @GetMapping("/getHyorderOne")
    @ResponseBody
    @ApiOperation(value = "合约倒计时")
    @NoNeedLogin
    public ResponseDTO<HyorderOneRes> getHyorderOne(@RequestParam int id) {
        return twHyorderService.getHyorderOne(id);
    }


    @GetMapping("/gethyorder")
    @ResponseBody
    @ApiOperation(value = "获取合约记录")
    @NoNeedLogin
    public ResponseDTO<List<TwHyorder>> gethyorder(@RequestParam int uid) {
        return twHyorderService.gethyorder(uid);
    }



    @GetMapping("/contractTy")
    @ResponseBody
    @ApiOperation(value = "合约未平仓、已平仓订单")
    @NoNeedLogin
    public ResponseDTO<List<TwHyorder>> contractTy(@RequestParam int uid ,@RequestParam int type) {
        return twHyorderService.contractTy(uid,type);
    }


    @GetMapping("/cbillinfo")
    @ResponseBody
    @ApiOperation(value = "购买合约详情")
    @NoNeedLogin
    public ResponseDTO<TwHyorder> cbillinfo(@RequestParam int uid ,@RequestParam int id) {
        return twHyorderService.cbillinfo(uid,id);
    }

    /**
     *获取合约设置
     * 表：hysetting
     *  hysetting  where  id=1
     */
    @GetMapping("/hysetInfo")
    @ResponseBody
    @ApiOperation(value = "获取合约设置")
    @NoNeedLogin
    public ResponseDTO<TwHysetting> hysetInfo(@RequestParam int companyId) {
        return ResponseDTO.ok(twHysettingService.hysettingId(companyId));
    }


    @GetMapping("/cbillList")
    @ResponseBody
    @ApiOperation(value = "合约购买记录")
    @NoNeedLogin
    public ResponseDTO<List<TwHyorder>> cbillList(@RequestParam int uid) {
        return twHyorderService.cbillList(uid);
    }


    @GetMapping("/creatorder")
    @ResponseBody
    @ApiOperation(value = "秒合约建仓")
    @NoNeedLogin
    @RedissonLockAnnotation(keyParts = "uid")
    public ResponseDTO creatorder(@RequestParam int uid,
                                  @RequestParam String ctime,
                                  @RequestParam BigDecimal ctzed,
                                  @RequestParam String ccoinname,
                                  @RequestParam int ctzfx,
                                  @RequestParam String plantime,
                                  @RequestParam int intplantime,
                                  @RequestParam BigDecimal cykbl,
                                  @RequestParam String language){
        return twHyorderService.creatorder(uid,ctime,ctzed,ccoinname,ctzfx,cykbl,language, plantime, intplantime);
    }

    @GetMapping("/orderNo")
    @ResponseBody
    @ApiOperation(value = "订单号查询")
    @NoNeedLogin
    public ResponseDTO orderNo(@RequestParam String orderNo) {
        return ResponseDTO.ok(twHyorderService.orderNo(orderNo));
    }

}

