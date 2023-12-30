package net.lab1024.sa.common.common.Email;

import okhttp3.*;

import java.io.IOException;

public class SendEmailBasic {


    private static final String BASE_URL = "https://pp8nge.api.infobip.com";
    private static final String API_KEY = "App a74fae415d4519486835cc44dabf84f9-9602cc5b-b798-4fae-8f9a-c6c785080d75";

    private static final String SENDER_EMAIL_ADDRESS = "LULN1@selfserviceib.com";
    private static final String RECIPIENT_EMAIL_ADDRESS = "1119076443luln@gmail.com";

    private static final String EMAIL_SUBJECT = "This is a sample email subject";
    private static final String EMAIL_TEXT = "This is a sample email message.";

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("from", SENDER_EMAIL_ADDRESS)
                .addFormDataPart("to", RECIPIENT_EMAIL_ADDRESS)
                .addFormDataPart("subject", EMAIL_SUBJECT)
                .addFormDataPart("text", EMAIL_TEXT).build();

        Request request = prepareHttpRequest(body);
        Response response = client.newCall(request).execute();

        System.out.println("HTTP status code: " + response.code());
        System.out.println("Response body: " + response.body().string());
    }

    private static Request prepareHttpRequest(RequestBody body) {
        return new Request.Builder()
                .url(String.format("%s/email/2/send", BASE_URL))
                .method("POST", body)
                .addHeader("Authorization", API_KEY)
                .addHeader("Content-Type", "text/plain")
                .addHeader("Accept", "application/json")
                .build();
    }
}
