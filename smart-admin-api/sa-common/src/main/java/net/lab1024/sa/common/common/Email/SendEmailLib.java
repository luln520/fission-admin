package net.lab1024.sa.common.common.Email;

import cn.hutool.crypto.SecureUtil;

import cn.hutool.http.Header;

import cn.hutool.http.HttpRequest;

import cn.hutool.http.HttpResponse;

import cn.hutool.json.JSONUtil;



import java.time.LocalDateTime;

import java.time.ZoneId;

public class SendEmailLib {

    private static final String BASE_URL = "https://pp8nge.api.infobip.com";
    private static final String API_KEY = "a74fae415d4519486835cc44dabf84f9-9602cc5b-b798-4fae-8f9a-c6c785080d75";

    private static final String SENDER_EMAIL_ADDRESS = "lilin li <1119076443luln@gmail.com>";
//    private static final String RECIPIENT_EMAIL_ADDRESS = "1119076443luln@gmail.com";

    private static final String EMAIL_SUBJECT = "26335";
    private static final String EMAIL_TEXT = "26335";

//    public static void email(String email) {
////         Create the API client and the Email API instances.
//        var apiClient = ApiClient.forApiKey(ApiKey.from(API_KEY))
//                .withBaseUrl(BaseUrl.from(BASE_URL))
//                .build();
//        var sendEmailApi = new EmailApi(apiClient);
//
//        try {
//            // Create the email and send it.
////            var to = new ArrayList<>(List.of(RECIPIENT_EMAIL_ADDRESS));
//            var to = new ArrayList<String>();
//            to.add(email);
//            var emailResponse = sendEmailApi
//                    .sendEmail(to)
//                    .from(SENDER_EMAIL_ADDRESS)
//                    .subject(EMAIL_SUBJECT)
//                    .text(EMAIL_TEXT)
//                    .execute();
//
//            System.out.println("Response body: " + emailResponse);
//
//            // Get delivery reports. It may take a few seconds to show the above-sent message.
//            var reportsResponse = sendEmailApi.getEmailDeliveryReports().execute();
//            System.out.println(reportsResponse.getResults());
//        } catch (ApiException e) {
//            System.out.println("HTTP status code: " + e.responseStatusCode());
//            System.out.println("Response body: " + e.rawResponseBody());
//        }
//    }

    public static final void email(){

            final String baseUrl = "https://api.itniotech.com/email";

            final String apiKey = "83kYOpti6Sh9l5u8q1ncuDoD6M2dhsOO";

            final String apiPwd = "CalQJycyXBDZXA5P4wmO9kD8pSC5F9Oz";

            final String appId = "appId";

            final String fromEmailAddress = "fromEmailAddress"; // The Mail address configured in the ITNIO

            final String toAddress = "toAddress";  //Recipient Address

            final String subject = "subject"; //Send Message Subject

            final String templateID = "templateID"; //Send Template ID

            final String templateData = "templateData"; //Template variable parameters

//            final int adFlag = adFlag; //Whether to add the advertising logo

            final String language = "language"; //language

//            final boolean checkEmailAddress = checkEmailAddress; //Enable email address validity check



            final String url = baseUrl.concat("/sendEmail");

            HttpRequest request = HttpRequest.post(url);

            // currentTime
            final String datetime = String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());

            // generate md5 key
            final String sign = SecureUtil.md5(apiKey.concat(apiPwd).concat(datetime));

            request.header(Header.CONNECTION, "Keep-Alive")

                    .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")

                    .header("Sign", sign)  //Signature with encryption

                    .header("Timestamp", datetime) //Current system time stamp (second)

                    .header("Api-Key", apiKey); //API KEY（Home-Developer options）



            final String params = JSONUtil.createObj()

                    .set("appId", appId)

                    .set("fromEmailAddress", fromEmailAddress)

                    .set("toAddress", toAddress)

                    .set("subject", subject)

                    .set("templateID", templateID)

                    .set("templateData", templateData)

//                    .set("adFlag", adFlag)

                    .set("language", language)

//                    .put("checkEmailAddress", checkEmailAddress)

                    .toString();



            HttpResponse response = request.body(params).execute();

            if (response.isOk()) {

                String result = response.body();

                System.out.println(result);

            }
    }
}

