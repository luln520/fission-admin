package net.lab1024.sa.admin.module.system.TwPC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图表
 */
@RestController
@RequestMapping("/api/pc/chart")
public class ChartController {

    /**
     *获取市场专业json数据
     * 表：trade_json
     * 参数：step
     * 大致逻辑：
     *       1.判断 step 得到 type的值
     *       2.查询   trade_json where type=?
     *       3.遍历拼接参数并返回list
     *参考代码：
     *      //获取市场专业json数据
     *      public function getMarketSpecialtyJsonAitc($step = null)
     *     {
     *         if ($step == 60) {
     *             $type = 1;
     *         } elseif ($step == 300) {
     *             $type = 5;
     *         } elseif ($step == 900) {
     *             $type = 15;
     *         } elseif ($step == 1800) {
     *             $type = 30;
     *         } elseif ($step == 1600) {
     *             $type = 60;
     *         } elseif ($step == 14400) {
     *             $type = 240;
     *         } elseif ($step == 86400) {
     *             $type = 1440;
     *         }
     *         $list = M("trade_json")->where(array('type' => $type))->select();
     *         foreach ($list as $key => $vo) {
     *             $vd = json_decode($vo['data']);
     *             $data[$key]['time'] = intval($vd[0]);
     *             $data[$key]['volume'] = $vd[1];
     *             $data[$key]['open'] = $vd[2];
     *             $data[$key]['high'] = $vd[3];
     *             $data[$key]['low'] = $vd[4];
     *             $data[$key]['close'] = $vd[5];
     *         }
     *         return json_encode($data);
     */


    /**
     *获取最后一个市场专业json数据
     * 表：trade_json
     * 大致逻辑：
     *       2.查询   trade_json where type=1 order by id desc limit 1
     *       3.拼接参数并返回对象
     *参考代码：
     *      public function getMarketSpecialtyJsonAitclast()
     *     {
     *         $list = M("trade_json")->where(array('type' => 1))->order('id desc')->limit(1)->select();
     *         foreach ($list as $key => $vo) {
     *             $vd = json_decode($vo['data']);
     *             $data[$key]['time'] = intval($vd[0]);
     *             $data[$key]['open'] = $vd[2];
     *             $data[$key]['high'] = $vd[3];
     *             $data[$key]['low'] = $vd[4];
     *             $data[$key]['close'] = $vd[5];
     *             $data[$key]['volume'] = $vd[1];
     *         }
     *         return json_encode($data);
     *     }
     */

}

