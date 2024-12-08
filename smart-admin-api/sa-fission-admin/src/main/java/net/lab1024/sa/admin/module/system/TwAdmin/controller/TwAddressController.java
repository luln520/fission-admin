package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddress;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddressDetail;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwArea;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwReceipt;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AddressVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TransferVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAddressService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.code.UserErrorCode;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.wallet.AddressUtils;
import net.lab1024.sa.common.common.wallet.CertificateManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/address")
@Api(tags = {AdminSwaggerTagConst.System.TW_ADMINLOG})
@Slf4j
public class TwAddressController {

    @Autowired
    private TwAddressService twAddressService;

    @PostMapping("/list")
    @ApiOperation(value = "获取分配钱包地址列表")
    public ResponseDTO<IPage<TwAddress>> listpage(@Valid @RequestBody AddressVo addressVo) {
        return ResponseDTO.ok(twAddressService.listpage(addressVo));
    }

    @PostMapping("/detail/list")
    @ApiOperation(value = "获取分配钱包地址列表")
    public ResponseDTO<List<TwAddressDetail>> detaillistpage(@RequestBody AddressVo addressVo) {
        return ResponseDTO.ok(twAddressService.listDetail(addressVo));
    }

    @PostMapping("/receipt/list")
    @ApiOperation(value = "票据列表")
    public ResponseDTO<IPage<TwReceipt>> receiptListpage(@RequestBody AddressVo addressVo) {
        return ResponseDTO.ok(twAddressService.listReceiptPage(addressVo));
    }

    /**
     *
     * @param transferVo
     * @return
     */
    @PostMapping("/transfer")
    @ApiOperation(value = "发送能量")
    public ResponseDTO<String> transfer(@RequestBody TransferVo transferVo) {
        if(StringUtils.isEmpty(transferVo.getPrivateKey())) {
            return ResponseDTO.error(UserErrorCode.PARAM_ERROR);
        }
        if(!isValidPrivateKey(transferVo.getPrivateKey())) {
            return ResponseDTO.error(UserErrorCode.PARAM_ERROR, "私钥格式错误");
        }

        if(StringUtils.isEmpty(transferVo.getAddress())) {
            return ResponseDTO.error(UserErrorCode.PARAM_ERROR, "地址为空");
        }

        String ethAddress = AddressUtils.getAddressFromPrivateKey(transferVo.getPrivateKey());
        String tronAddress = AddressUtils.ethToTron(ethAddress);
        if(!transferVo.getAddress().equalsIgnoreCase(ethAddress)
                && !transferVo.getAddress().equalsIgnoreCase(tronAddress)) {
            return ResponseDTO.error(UserErrorCode.PARAM_ERROR, "私钥与地址校验失败，请检查私钥");
        }
        twAddressService.transfer(transferVo.getId(), transferVo.getPrivateKey());

        return ResponseDTO.okMsg("已提交发送能量作业，后续查询余额变化");
    }

    private boolean isValidPrivateKey(String privateKey) {
        return privateKey.matches("^[0-9a-fA-F]{64}$");
    }

    /**
     * 上传证书归集
     * @param keystoreFile
     * @return
     */
    @PostMapping("/upload-keystore")
    @ApiOperation(value = "上传证书归集")
    public ResponseDTO<String> uploadKeystoreAndDecrypt(
            @RequestPart("keystoreFile") MultipartFile keystoreFile,
            @RequestParam("coinId") Integer coinId) {
        try {
            if(coinId == null || coinId == 0) {
                return ResponseDTO.error(UserErrorCode.PARAM_ERROR, "缺少参数coinId");
            }

            List<TwAddress> twAddressList = twAddressService.listBalanceAddress(coinId);
            if(CollectionUtils.isEmpty(twAddressList)) {
                return ResponseDTO.error(UserErrorCode.PARAM_ERROR, "所有子账户没有余额，请稍后尝试");
            }

            twAddressService.oneKeyCollect(coinId, keystoreFile.getBytes());
            return ResponseDTO.okMsg("已提交归集作业，后续查询余额变化");
        } catch (Exception e) {
            return ResponseDTO.error(UserErrorCode.PARAM_ERROR);
        }
    }

    @GetMapping("/resetBlock")
    @NoNeedLogin
    public ResponseDTO<Object> resetBlock() {
        try{
            twAddressService.resetBlock();
        }catch(Exception e) {
            log.error(e.getMessage(), e);
        }
        return ResponseDTO.ok();
    }
}
