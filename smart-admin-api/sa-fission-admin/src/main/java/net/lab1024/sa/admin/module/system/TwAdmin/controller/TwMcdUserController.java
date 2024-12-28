package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddress;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddressDetail;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMcdUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwReceipt;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AddressVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TransferVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAddressService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMcdInfoService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.code.UserErrorCode;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.wallet.AddressUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/mcd")
@Api(tags = {AdminSwaggerTagConst.System.TW_ADMINLOG})
@Slf4j
public class TwMcdUserController {

    @Autowired
    private TwMcdInfoService twMcdInfoService;

    @PostMapping("/list")
    @ApiOperation(value = "获取分配钱包地址列表")
    public ResponseDTO<IPage<TwMcdUser>> listpage(@Valid @RequestBody PageParam pageParam) {
        return ResponseDTO.ok(twMcdInfoService.listpage(pageParam));
    }

    @GetMapping("/approve")
    @ApiOperation(value = "通过")
    public ResponseDTO approve(@RequestParam int id) {
        twMcdInfoService.approveMcd(id);
        return ResponseDTO.ok();
    }

    @GetMapping("/reject")
    @ApiOperation(value = "驳回")
    public ResponseDTO reject(@RequestParam int id) {
        twMcdInfoService.rejectMcd(id);
        return ResponseDTO.ok();
    }


    @GetMapping("/refreshAddress")
    @NoNeedLogin
    public ResponseDTO<Object> refreshAddress(@RequestParam Integer coinId) {
        try{
            //twAddressService.refreshAddress(coinId);
        }catch(Exception e) {
            log.error(e.getMessage(), e);
        }
        return ResponseDTO.ok();
    }
}
