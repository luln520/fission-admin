package net.lab1024.sa.common.common.SMS;

import io.lettuce.core.ScriptOutputType;
import okhttp3.*;

import java.io.IOException;

public class SendSmsBasic {

    private static final String BASE_URL = "https://pp8nge.api.infobip.com";
    private static final String API_KEY = "App a74fae415d4519486835cc44dabf84f9-9602cc5b-b798-4fae-8f9a-c6c785080d75";
    private static final String MEDIA_TYPE = "application/json";

    private static final String SENDER = "InfoSMS";
    private static final String RECIPIENT = "639627665869";
    private static final String MESSAGE_TEXT = "This is a sample message";


    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://pp8nge.api.infobip.com/sms/1/inbox/reports?limit=2")
                .addHeader("Authorization", API_KEY)
                .addHeader("Accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response);
    }

    private static Request prepareHttpRequest(RequestBody body) {
        return new Request.Builder()
                .url(String.format("%s/sms/2/text/advanced", BASE_URL))
                .method("POST", body)
                .addHeader("Authorization", API_KEY)
                .addHeader("Content-Type", MEDIA_TYPE)
                .addHeader("Accept", MEDIA_TYPE)
                .build();
    }
}
