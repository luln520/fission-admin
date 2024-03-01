package net.lab1024.sa.admin.module.system.TwPC.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwLeverOrderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwLeverSetService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwLeverageService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pc/leverorder")
@Api(tags = {AdminSwaggerTagConst.PC.PC_LEVERORDER})
public class PcLeverOrderController {


    @Autowired
    private TwLeverOrderService twLeverOrderService;
    @Autowired
    private TwLeverageService twLeverageService;
    @Autowired
    private TwLeverSetService twLeverSetService;

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "杠杆订单列表")
    @NoNeedLogin
    public ResponseDTO<IPage<TwLeverOrder>> listpage(@Valid @RequestBody LeverVo leverVo) {
        return ResponseDTO.ok(twLeverOrderService.listPcPage(leverVo));
    }

    @GetMapping("/getTwLeverSet")
    @ResponseBody
    @ApiOperation(value = "获取杠杆止盈止损设置")
    @NoNeedLogin
    public ResponseDTO<List<TwLeverSet>> getTwLeverSet(@RequestParam String symbol, @RequestParam int type,@RequestParam int companyId){
        return twLeverSetService.getTwLeverSet(symbol,type,companyId);
    }

    @GetMapping("/getTwLeverage")
    @ResponseBody
    @ApiOperation(value = "获取杠杆倍数")
    @NoNeedLogin
    public ResponseDTO<List<TwLeverage>> getTwLeverage(@RequestParam String symbol,@RequestParam int companyId){
        return twLeverageService.getTwLeverage(symbol,companyId);
    }

    @GetMapping("/creatorder")
    @ResponseBody
    @ApiOperation(value = "杠杆建仓")
    @NoNeedLogin
    public ResponseDTO creatorder(@RequestParam int uid,            //用户id
                                  @RequestParam String ccoinname,   //币种
                                  @RequestParam int win,            //止赢
                                  @RequestParam int loss,           //止损
                                  @RequestParam int fold,           //倍数
                                  @RequestParam int hyzd,           //1.做多 2做空
                                  @RequestParam BigDecimal num,     //数量
                                  @RequestParam BigDecimal ploss,   //利润
                                  @RequestParam BigDecimal premium, //手续费
                                  @RequestParam String language){
        return twLeverOrderService.creatorder(uid,ccoinname,win,loss,fold,hyzd,num,ploss,premium,language);
    }


    @GetMapping("/closeorder")
    @ResponseBody
    @ApiOperation(value = "平仓")
    @NoNeedLogin
    public ResponseDTO closeorder(@RequestParam int uid,@RequestParam int lid,@RequestParam String language){
        return twLeverOrderService.closeorder(uid,lid,language);
    }


}
