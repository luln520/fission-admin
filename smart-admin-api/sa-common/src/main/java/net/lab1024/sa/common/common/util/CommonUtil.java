package net.lab1024.sa.common.common.util;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class CommonUtil {
    private static final String PASSWORD_SALT_FORMAT = "smart_%s_admin_$^&*";
    /**
     * 获取 加密后 的密码
     *
     * @param password
     * @return
     */
    public static String getEncryptPwd(String password) {
        return DigestUtils.md5Hex(String.format(PASSWORD_SALT_FORMAT, password));
    }

    /**
     * 获取用户IP
     * @param request
     * @return
     */
    public  static String getClientIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }


    public  static String getIP(HttpServletRequest request) {
        return  ServletUtil.getClientIP(request);
    }



     /**
     * 实现 传入 ip地址  通过用户真实ip  拿到ip属于哪个国家 （日志需要）
     */
     public static String getLocationByIP(String ipAddress) throws IOException {
         String apiUrl = "http://ip-api.com/json/" + ipAddress;
         URL url = new URL(apiUrl);

         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("GET");

         int responseCode = connection.getResponseCode();
         if (responseCode == HttpURLConnection.HTTP_OK) {
             BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             StringBuilder response = new StringBuilder();
             String line;

             while ((line = reader.readLine()) != null) {
                 response.append(line);
             }
             reader.close();

             // 解析 JSON 响应，提取地理位置信息
             String jsonResponse = response.toString();
             // 这里的解析方式仅作为演示，实际中你可能需要使用更复杂的 JSON 解析库
             String country = jsonResponse.split(",")[1].split(":")[1].replaceAll("\"", "");
             String region = jsonResponse.split(",")[4].split(":")[1].replaceAll("\"", "");

             return country + ", " + region;
         } else {
             throw new IOException("Unable to retrieve location information. HTTP Response Code: " + responseCode);
         }
     }

    /**
     * httpUtil   发送 get post 等请求（第三方接口请求需要）
     */
    /**
     * Get方式请求
     * @param charset 编码方式
     * @param proxyIp 代理IP
     * @return
     */
    public static Map<String, Object> doGet(String url, String charset, String proxyIp) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (null == charset) {
            charset = "utf-8";
        }
        try {

            SocketAddress addr = new InetSocketAddress(proxyIp.split(":")[0], Integer.parseInt(proxyIp.split(":")[1]));
            cn.hutool.http.HttpRequest httpRequest = HttpUtil.createGet(String.valueOf(url))           //设置代理端口
                    .timeout(10000);//发送GET请求
            String body = httpRequest.execute().body();
            map.put("res", body);
        } catch (Exception e) {
            map.put("res", "error");
            System.out.println(e.getMessage());
        } finally {
            try {
            } catch (NullPointerException e) {
                map.put("res", "error");
            }
        }

        return map;
    }



    /**
     * doPost 表单方式请求
     * @param charset 编码方式
     * @param params  参数
     * @param proxyIp 代理IP
     * @return
     */
    public static Map<String, Object> doPost(String url, String charset, String params, String proxyIp) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (null == charset) {
            charset = "utf-8";
        }
        try {
            SocketAddress addr = new InetSocketAddress(proxyIp.split(":")[0], Integer.parseInt(proxyIp.split(":")[1]));
            cn.hutool.http.HttpRequest httpRequest = HttpUtil.createPost(String.valueOf(url))
                    //设置代理端口
                    .body(params)
                    .timeout(10000);//发送GET请求

            String body = httpRequest.execute().body();
            map.put("res", body);
        } catch (Exception e) {
            map.put("res", "error");
            System.out.println(e.getMessage());
        } finally {
            try {
            } catch (NullPointerException e) {
                map.put("res", "error");
            }
        }

        return map;
    }

}
