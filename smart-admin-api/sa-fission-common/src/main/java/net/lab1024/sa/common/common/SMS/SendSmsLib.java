package net.lab1024.sa.common.common.SMS;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import lombok.var;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
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
//                .text("Your verification code is: 56565 . Please pay attention to account security and do not disclose your account password, verification code and other security information to anyone. Please be vigilant to avoid asset losses");
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

    public static void phone(String phone,String code) throws IOException {
//            final String baseUrl = "https://api.itniotech.com/sms";
//            final String apiKey = "83kYOpti6Sh9l5u8q1ncuDoD6M2dhsOO";
//            final String apiPwd = "CalQJycyXBDZXA5P4wmO9kD8pSC5F9Oz";
//            final String appId = "3GRpJ47I";
//
//            final String content = "Your verification code is: "+code+" . Please pay attention to account security and do not disclose your account password, verification code and other security information to anyone. Please be vigilant to avoid asset losses";
//            final String senderId = "";
//
//            final String url = baseUrl.concat("/sendSms");
//
//            HttpRequest request = HttpRequest.post(url);
//
//            // currentTime
//            final String datetime = String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
//            // generate md5 key
//            final String sign = SecureUtil.md5(apiKey.concat(apiPwd).concat(datetime));
//            request.header(Header.CONNECTION, "Keep-Alive")
//                    .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
//                    .header("Sign", sign)
//                    .header("Timestamp", datetime)
//                    .header("Api-Key", apiKey);
//
//
//            final String params = JSONUtil.createObj()
//                    .set("appId", appId)
//                    .set("numbers", phone)
//                    .set("content", content)
//                    .set("senderId", senderId)
//                    .toString();
//
//            HttpResponse response = request.body(params).execute();
//            if (response.isOk()) {
//                String result = response.body();
//                System.out.println(result);
//            }


        final String baseUrl = "https://api.itniotech.com/sms";
        final String apiKey = "83kYOpti6Sh9l5u8q1ncuDoD6M2dhsOO";
        final String apiPwd = "CalQJycyXBDZXA5P4wmO9kD8pSC5F9Oz";
        final String appId = "3GRpJ47I";

        final String content = "Your login verification code:"+code;
        final String url = baseUrl.concat("/sendSms");

        // currentTime
        final String datetime = String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        // generate md5 key
        final String sign = SecureUtil.md5(apiKey.concat(apiPwd).concat(datetime));

        final String params = JSONUtil.createObj()
                .set("appId", appId)
                .set("numbers", phone)
                .set("content", content)
                .toString();

        trustAllHosts();
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.setRequestProperty("Sign", sign);
        connection.setRequestProperty("Timestamp", datetime);
        connection.setRequestProperty("Api-Key", apiKey);

        connection.connect();
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        writer.write(params);
        writer.flush();

        int responseCode = connection.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            String result = getResponseBody(connection);
            System.out.println(result);
        }
        connection.disconnect();
    }

    public static String getResponseBody(HttpsURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        InputStream inputStream;
        if (responseCode >= 200 && responseCode < 300) {
            inputStream = connection.getInputStream();
        } else {
            inputStream = connection.getErrorStream();
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }

        bufferedReader.close();

        return response.toString();
    }

    public static void trustAllHosts() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        }};
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
