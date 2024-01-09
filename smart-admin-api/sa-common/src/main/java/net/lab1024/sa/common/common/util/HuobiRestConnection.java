package net.lab1024.sa.common.common.util;

import okhttp3.Request;

import java.net.MalformedURLException;
import java.net.URL;

public class HuobiRestConnection {

  public static String executeGet(String url){

    Request executeRequest = new Request.Builder()
        .url(url)
        .addHeader("Content-Type", "application/x-www-form-urlencoded")
        .build();

    String resp = ConnectionFactory.execute(executeRequest);
    return resp;
  }



}
