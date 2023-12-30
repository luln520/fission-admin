package net.lab1024.sa.common.common.Email;

import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.ApiKey;
import com.infobip.BaseUrl;
import com.infobip.api.EmailApi;
import lombok.var;

import java.util.ArrayList;

import static org.apache.poi.hslf.record.RecordTypes.List;

public class SendEmailLib {

    private static final String BASE_URL = "https://pp8nge.api.infobip.com";
    private static final String API_KEY = "a74fae415d4519486835cc44dabf84f9-9602cc5b-b798-4fae-8f9a-c6c785080d75";

    private static final String SENDER_EMAIL_ADDRESS = "LULN1@selfserviceib.com";
    private static final String RECIPIENT_EMAIL_ADDRESS = "1119076443luln@gmail.com";

    private static final String EMAIL_SUBJECT = "This is a sample email subject";
    private static final String EMAIL_TEXT = "This is a sample email message.";

    public static void main(String[] args) {
        // Create the API client and the Email API instances.
        var apiClient = ApiClient.forApiKey(ApiKey.from(API_KEY))
                .withBaseUrl(BaseUrl.from(BASE_URL))
                .build();
        var sendEmailApi = new EmailApi(apiClient);

        try {
            // Create the email and send it.
//            var to = new ArrayList<>(List.of(RECIPIENT_EMAIL_ADDRESS));
            var to = new ArrayList<String>();
            to.add(RECIPIENT_EMAIL_ADDRESS);
            var emailResponse = sendEmailApi
                    .sendEmail(to)
                    .from(SENDER_EMAIL_ADDRESS)
                    .subject(EMAIL_SUBJECT)
                    .text(EMAIL_TEXT)
                    .execute();

            System.out.println("Response body: " + emailResponse);

            // Get delivery reports. It may take a few seconds to show the above-sent message.
            var reportsResponse = sendEmailApi.getEmailDeliveryReports().execute();
            System.out.println(reportsResponse.getResults());
        } catch (ApiException e) {
            System.out.println("HTTP status code: " + e.responseStatusCode());
            System.out.println("Response body: " + e.rawResponseBody());
        }
    }
}

