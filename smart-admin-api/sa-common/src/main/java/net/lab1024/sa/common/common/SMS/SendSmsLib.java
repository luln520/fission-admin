package net.lab1024.sa.common.common.SMS;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SendSmsLib {

    private static final String BASE_URL = "https://pp8nge.api.infobip.com";
    private static final String API_KEY = "a74fae415d4519486835cc44dabf84f9-9602cc5b-b798-4fae-8f9a-c6c785080d75";
//    private static final String RECIPIENT = "639627665869";



//    public static void phone(String phone) {
//        // Create the API client and the Send SMS API instances.
//        var apiClient = ApiClient.forApiKey(ApiKey.from(API_KEY))
//                .withBaseUrl(BaseUrl.from(BASE_URL))
//                .build();
//        var sendSmsApi = new SmsApi(apiClient);
//
//        // Create a message to send.
//        var smsMessage = new SmsTextualMessage()
//                .addDestinationsItem(new SmsDestination().to(phone))
//                .entityId("4575A374484F5CC8C67609C2F745DB5A")
//                .applicationId("111907")
//                .text("26356");
//
//        // Create a send message request.
//        var smsMessageRequest = new SmsAdvancedTextualRequest()
//                .messages(Collections.singletonList(smsMessage));
//
//        try {
//            // Send the message.
//            var smsResponse = sendSmsApi.sendSmsMessage(smsMessageRequest).execute();
//            System.out.println("Response body: " + smsResponse);
//
//            // Get delivery reports. It may take a few seconds to show the above-sent message.
//            var reportsResponse = sendSmsApi.getOutboundSmsMessageDeliveryReports().execute();
//            System.out.println(reportsResponse.getResults());
//        } catch (ApiException e) {
//            System.out.println("HTTP status code: " + e.responseStatusCode());
//            System.out.println("Response body: " + e.rawResponseBody());
//        }
//
//    }

    public static void phone(String phone){
            final String baseUrl = "https://api.itniotech.com/sms";
            final String apiKey = "83kYOpti6Sh9l5u8q1ncuDoD6M2dhsOO";
            final String apiPwd = "CalQJycyXBDZXA5P4wmO9kD8pSC5F9Oz";
            final String appId = "3GRpJ47I";

            final String content = "Your verification code is: 56565 . Please pay attention to account security and do not disclose your account password, verification code and other security information to anyone. Please be vigilant to avoid asset losses";
            final String senderId = "";

            final String url = baseUrl.concat("/sendSms");

            HttpRequest request = HttpRequest.post(url);

            // currentTime
            final String datetime = String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
            // generate md5 key
            final String sign = SecureUtil.md5(apiKey.concat(apiPwd).concat(datetime));
            request.header(Header.CONNECTION, "Keep-Alive")
                    .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
                    .header("Sign", sign)
                    .header("Timestamp", datetime)
                    .header("Api-Key", apiKey);


            final String params = JSONUtil.createObj()
                    .set("appId", appId)
                    .set("numbers", phone)
                    .set("content", content)
                    .set("senderId", senderId)
                    .toString();

            HttpResponse response = request.body(params).execute();
            if (response.isOk()) {
                String result = response.body();
                System.out.println(result);
            }
    }
}
