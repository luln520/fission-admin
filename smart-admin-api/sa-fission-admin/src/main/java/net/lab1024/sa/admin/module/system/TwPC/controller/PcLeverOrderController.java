package net.lab1024.sa.admin.module.system.TwPC.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.lock.RedissonLockAnnotation;
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
    @RedissonLockAnnotation(keyParts = "uid")
    public ResponseDTO closeorder(@RequestParam int uid,@RequestParam int lid,@RequestParam String language){
        return twLeverOrderService.closeorder(uid,lid,language);
    }

    @GetMapping("/orderNo")
    @ResponseBody
    @ApiOperation(value = "订单号查询")
    @NoNeedLogin
    public ResponseDTO orderNo(@RequestParam String orderNo) {
        return ResponseDTO.ok(twLeverOrderService.orderNo(orderNo));
    }

    @GetMapping("/creatorderNew")
    @ResponseBody
    @ApiOperation(value = "杠杆建仓")
    @NoNeedLogin
    @RedissonLockAnnotation(keyParts = "uid")
    public ResponseDTO creatorderNew(@RequestParam int uid,            //用户id
                                  @RequestParam String ccoinname,   //币种
                                  @RequestParam int win,            //止赢
                                  @RequestParam int loss,           //止损
                                  @RequestParam int fold,           //倍数
                                  @RequestParam int hyzd,           //1.做多 2做空
                                  @RequestParam BigDecimal num,     //数量
                                  @RequestParam BigDecimal ploss,   //利润
                                  @RequestParam BigDecimal premium, //手续费
                                  @RequestParam String language,
                                  @RequestParam(name = "lossPrice", defaultValue = "0") BigDecimal lossPrice, //止损价格
                                  @RequestParam(name = "winPrice", defaultValue = "0") BigDecimal winPrice, //止盈价格
                                  @RequestParam BigDecimal boomPrice  ){
        return twLeverOrderService.creatorderNew(uid,ccoinname,win,loss,fold,hyzd,num,ploss,premium,language,lossPrice,winPrice,boomPrice);
    }

    @GetMapping("/addnum")
    @ResponseBody
    @ApiOperation(value = "加仓")
    @NoNeedLogin
    @RedissonLockAnnotation(keyParts = "uid")
    public ResponseDTO addnum(       @RequestParam int uid,            //用户id
                                     @RequestParam String orderNo,   //币种
                                     @RequestParam BigDecimal num,     //数量
                                     @RequestParam String language,
                                     @RequestParam BigDecimal boomPrice  ){
        return twLeverOrderService.addnum(uid,num,orderNo,language,boomPrice);
    }

    @GetMapping("/strutcnum")
    @ResponseBody
    @ApiOperation(value = "减仓")
    @NoNeedLogin
    @RedissonLockAnnotation(keyParts = "uid")
    public ResponseDTO strutcnum(       @RequestParam int uid,            //用户id
                                     @RequestParam String orderNo,   //币种
                                     @RequestParam BigDecimal num,     //数量
                                     @RequestParam String language,
                                     @RequestParam BigDecimal boomPrice  ){
        return twLeverOrderService.strutcnum(uid,num,orderNo,language,boomPrice);
    }


    @GetMapping("/editLossWin")
    @ResponseBody
    @ApiOperation(value = "修改止盈止损")
    @NoNeedLogin
    public ResponseDTO editLossWin(     @RequestParam int uid,            //用户id
                                        @RequestParam String orderNo,   //币种
                                        @RequestParam String language,
                                        @RequestParam BigDecimal lossPrice, //止损价格
                                        @RequestParam BigDecimal winPrice //止盈价格
                                         ){
        return twLeverOrderService.editLossWin(uid,orderNo,language,lossPrice,winPrice);
    }

}
