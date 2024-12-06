package net.lab1024.sa.admin.module.system.TwPC.controller;

import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAddressService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static net.lab1024.sa.common.common.code.UserErrorCode.PARAM_ERROR;

/**
 * 币种
 */
@Slf4j
@RestController
@RequestMapping("/api/pc/coin")
@Api(tags = {AdminSwaggerTagConst.PC.PC_COIN})
public class PcCoinController {

    @Autowired
    private TwCoinService twCoinService;

    @Autowired
    private TwAddressService twAddressService;

    /**
     * 币种列表
     * 表 coin
     * select coin  where  txstatus=1
     * */
    @GetMapping("/list")
    @ApiOperation(value = "账单列表")
    @NoNeedLogin
    public ResponseDTO<List<TwCoin>> lists(@RequestParam int companyId,
                                           @RequestParam("userCode") String userCode,
                                           HttpServletRequest request) {
        try{
            return ResponseDTO.ok(twCoinService.lists(userCode, companyId, request));
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            return ResponseDTO.error(PARAM_ERROR);
        }

    }

    /**
     * 获取单个币种信息
     * 表 coin
     * 参数：id(币种 id)
     * select coin  where  id=？
     * */
    @GetMapping("/find")
    @ApiOperation(value = "获取单个币种信息")
    @NoNeedLogin
    public ResponseDTO<TwCoin> find(@RequestParam int id) {
        return ResponseDTO.ok(twCoinService.find(id));
    }


    @GetMapping("/address")
    @ApiOperation(value = "获取对应的虚拟地址")
    @NoNeedLogin
    public ResponseDTO<Object> address(@RequestParam Integer uid,
                                       @RequestParam Integer coinId) {
        log.info("请求虚拟地址接口, uid: {}, coinId: {}", uid, coinId);

        Map<String, String> map = Maps.newHashMap();
        map.put("czaddress", twAddressService.getAddress(uid, coinId));
        return ResponseDTO.ok(map);
    }
}

