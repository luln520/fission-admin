package net.lab1024.sa.admin.module.system.TwPC.controller;


import com.infobip.*;
import com.infobip.api.SmsApi;
import com.infobip.model.SmsDeliveryResult;
import com.infobip.model.SmsReport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pc/code")
@Api(tags = {AdminSwaggerTagConst.PC.PC_CODE})
public class PcCodeController {


    @Autowired
    private TwUserService twUserService;

    @GetMapping("/code")
    @ApiOperation(value = "手机/邮箱验证码")
    @NoNeedLogin
    public ResponseDTO code(@RequestParam String phone,@RequestParam int type,@RequestParam String email) throws IOException {
        return twUserService.code(phone,type,email);
    }

    @GetMapping("/codeResp")
    @ApiOperation(value = "获取手机/邮箱验证码返回")
    @NoNeedLogin
    public ResponseDTO codeResp() throws IOException {
        return twUserService.codeResp();
    }

    private  final String BASE_URL = "https://pp8nge.api.infobip.com";
    private  final String API_KEY = "a74fae415d4519486835cc44dabf84f9-9602cc5b-b798-4fae-8f9a-c6c785080d75";

    @PostMapping("/delivery-reports")
    @NoNeedLogin
    public void receiveDeliveryReports(HttpServletRequest request) throws IOException, ApiException {
        var apiClient = ApiClient.forApiKey(ApiKey.from(API_KEY))
                .withBaseUrl(BaseUrl.from(BASE_URL))
                .build();
        var sendSmsApi = new SmsApi(apiClient);
        SmsDeliveryResult deliveryReports = sendSmsApi.getOutboundSmsMessageDeliveryReports()
                .execute();
        for (SmsReport report : deliveryReports.getResults()) {
            System.out.println(report);

        }
    }
}
