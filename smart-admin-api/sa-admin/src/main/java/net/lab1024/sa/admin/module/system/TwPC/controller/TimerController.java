package net.lab1024.sa.admin.module.system.TwPC.controller;

import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.service.impl.TimerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时器
 */
@Component
@Slf4j

public class TimerController {
    @Autowired
    private TimerServiceImpl timerService;

    /**
     * 工具方法：执行api （api调用）
     * 方法名：getCurl
     * 参数：url（api的）
     * 大致逻辑：
     *         1.设置 禁用 SSL 证书验证
     *         2.设置请求头 agent：Mozilla/5.0 (Linux; U; Android 4.4.1; zh-cn) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.5 Mobile Safari/533.1
     *         3.发送请求 并直接返回 请求完成的数据
     * */

//暂时不用这个功能
    /**
     * 对USDT地址执行查询
     * 参数：address（钱包地址）
     * 大致逻辑：
     *         1.构建请求参数 然后get请求
     *         2.返回的数据 遍历提取并结算需要的字段   基础url需要拼接参数：https://apilist.tronscan.org/api/token_trc20/transfers?  获取转账列表
     *         3.返回list
     *
     * 参考代码：
     *      //对USDT地址执行查询
     *     public function getTransferInList($address)
     *     {
     *         $result = [];
     *         $end = time() * 1000;
     *         $start = (time() - 1800) * 1000;//监控时间  20分钟
     *         $params = [
     *             'limit' => 300,
     *             'start' => 0,
     *             'direction' => 'in',
     *             'relatedAddress' => $address,
     *             'start_timestamp' => $start,
     *             'end_timestamp' => $end,
     *         ];
     *         $api = "https://apilist.tronscan.org/api/token_trc20/transfers?" . http_build_query($params);
     *         $resp = $this->get_curl($api);
     *         $data = json_decode($resp, true);
     *         foreach ($data['token_transfers'] as $transfer) {
     *             if ($transfer['to_address'] == $address && $transfer['finalResult'] == 'SUCCESS') {
     *                 $result[] = [
     *                     'time' => $transfer['block_ts'] / 1000,
     *                     'money' => $transfer['quant'] / 1000000,
     *                     'trade_id' => $transfer['transaction_id'],
     *                     'buyer' => $transfer['from_address'],
     *                     'mybuyer' => $transfer['to_address'],
     *                     'trc20id' => $transfer['transaction_id'],
     *                     'tokenAbbr' => $transfer['tokenInfo']['tokenAbbr'],//必须币种是USDT
     *                     'finalResult' => $transfer['finalResult'],//最终结果必须是成功的
     *                 ];
     *             }
     *         }
     *         return $result;
     *     }
     * */
    /** 获取足球赛程  每隔一分钟执行一次   */
    @Scheduled(cron = "0 */1 * * * ?")
    public void getTransferInList() {
//        timerService.getTransferInList();
    }



    //暂时不用这个功能
    /**
     * 对USDT地址执行查询
     * 参数：address（钱包地址）
     * 大致逻辑：
     *         1.构建请求参数 然后get请求
     *         2.返回的数据 遍历提取并结算需要的字段   基础url需要拼接参数：https://apilist.tronscan.org/api/token_trc20/transfers?
     *         3.返回list
     *
     * 参考代码：
     *      //对USDT地址执行查询
     *     public function getTransferInList($address)
     *     {
     *         $result = [];
     *         $end = time() * 1000;
     *         $start = (time() - 1800) * 1000;//监控时间  20分钟
     *         $params = [
     *             'limit' => 300,
     *             'start' => 0,
     *             'direction' => 'in',
     *             'relatedAddress' => $address,
     *             'start_timestamp' => $start,
     *             'end_timestamp' => $end,
     *         ];
     *         $api = "https://apilist.tronscan.org/api/token_trc20/transfers?" . http_build_query($params);
     *         $resp = $this->get_curl($api);
     *         $data = json_decode($resp, true);
     *         foreach ($data['token_transfers'] as $transfer) {
     *             if ($transfer['to_address'] == $address && $transfer['finalResult'] == 'SUCCESS') {
     *                 $result[] = [
     *                     'time' => $transfer['block_ts'] / 1000,
     *                     'money' => $transfer['quant'] / 1000000,
     *                     'trade_id' => $transfer['transaction_id'],
     *                     'buyer' => $transfer['from_address'],
     *                     'mybuyer' => $transfer['to_address'],
     *                     'trc20id' => $transfer['transaction_id'],
     *                     'tokenAbbr' => $transfer['tokenInfo']['tokenAbbr'],//必须币种是USDT
     *                     'finalResult' => $transfer['finalResult'],//最终结果必须是成功的
     *                 ];
     *             }
     *         }
     *         return $result;
     *     }
     * */






}

