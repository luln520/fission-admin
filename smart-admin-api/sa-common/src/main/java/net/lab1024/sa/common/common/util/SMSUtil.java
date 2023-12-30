package net.lab1024.sa.common.common.util;

import cn.hutool.http.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信发送工具
 */
public class SMSUtil {

    /**
     *短信发送工具  表 config
     * 参数： phone content（内容）
     * 大致逻辑：
     *      1.读取 config 表的四个字段  sms_url,sms_key,sms_id,smstemple    where id =1  得到发送短信的配置信息
     *      2.拼接参数
     *      3.拼接请求头和协议等  post请求 去除 ssl验证  header 添加 Content-Type: application/x-www-form-urlencoded
     *      4,.发送请求
     *      5.读取返回 code   0是成功  相反非0 失败
     *
     * 逻辑代如下：
     *         $config = $clist = M("config")->where(array('id' => 1))->field("sms_url,sms_key,sms_id,smstemple")->find(); （逻辑 1）
     *         $data = array( （逻辑 2）
     *             "appkey" => $config['sms_id'],
     *             "secretkey" => $config['sms_key'],
     *             "phone" => $phone, （传入的手机号）
     *             "content" => $config['smstemple'] . ":" . $content,    拼接   取config字段content的值 加上 : 符号 再拼接上 传入的 content（验证码）
     *         );
     *
     */

}
