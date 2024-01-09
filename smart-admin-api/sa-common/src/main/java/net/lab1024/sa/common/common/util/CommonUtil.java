package net.lab1024.sa.common.common.util;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import cn.hutool.http.ssl.SSLSocketFactoryBuilder;

import javax.net.ssl.SSLContext;

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
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }


    public  static String getIP(HttpServletRequest request) {
        return  ServletUtil.getClientIP(request);
    }



     /**
     * 实现 传入 ip地址  通过用户真实ip  拿到ip属于哪个国家 （日志需要）
     */
     public static String getAddress(String ipAddress) throws IOException {
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
     * @return
     */
    public static Map<String, Object> doGet(String url, String charset) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (null == charset) {
            charset = "utf-8";
        }
        try {
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
     * httpUtil   发送 get post 等请求（第三方接口请求需要）
     */
    /**
     * Get方式请求
     * @param charset 编码方式
     * @return
     */
    public static Map<String, Object> doOKHGet(String url, String charset) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (null == charset) {
            charset = "utf-8";
        }
        try {
            OkHttpClient client = new OkHttpClient();
            // 1.1 assemble params（组装参数）

            Request request = new Request.Builder().url(url)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded").build();
            // 1.3 execute request and print result （执行请求并打印结果）
                Response resp = client.newCall(request).execute();
                map.put("res", resp.body().string());
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

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long end = start + (20 * 60 * 1000); // 20分钟的毫秒数
        String str = "https://apilist.tronscanapi.com/api/new/token_trc20/transfers?limit=20&start=0&contract_address=TWxjzcUnA8weSCVdtwL4WzR2qyQFDP7xK1&start_timestamp="+start+"&end_timestamp="+end+"&confirm=&filterTokenValue=1";
        Map<String, Object> stringObjectMap = doGet(str,null);
        System.out.println(stringObjectMap);
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
