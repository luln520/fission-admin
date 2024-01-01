package net.lab1024.sa.common.common.SMS;

import lombok.var;

import java.util.Collections;

public class SendSmsLib {

    private static final String BASE_URL = "https://pp8nge.api.infobip.com";
    private static final String API_KEY = "a74fae415d4519486835cc44dabf84f9-9602cc5b-b798-4fae-8f9a-c6c785080d75";
    private static final String RECIPIENT = "639627665869";

    public static void main(String[] args) {
//        // Create the API client and the Send SMS API instances.
//        var apiClient = ApiClient.forApiKey(ApiKey.from(API_KEY))
//                .withBaseUrl(BaseUrl.from(BASE_URL))
//                .build();
//        var sendSmsApi = new SmsApi(apiClient);
//
//        // Create a message to send.
//        var smsMessage = new SmsTextualMessage()
//                .addDestinationsItem(new SmsDestination().to(RECIPIENT))
//                .text("Hello from Infobip Java Client!");
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
    }
}
