package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwBillService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwNoticeService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCoinService;
import net.lab1024.sa.common.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;

@Service
public class TimerServiceImpl {

    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private TwBillService twBillService;

    @Autowired
    private TwNoticeService twNoticeService;

    /**
     * 工具方法：获取行情数据 （api调用）
     * 方法名：getnewprice
     * 参数：url（api的）
     *参考代码：
     *          //获取行情数据
     *     public function getnewprice($api)
     *     {
     *         $result = httpGet(url);(请求 并得到返回结果)
     *         $price_arr = $result['data'][0];
     *         $close = $price_arr['close'];//现价
     *         return $close;
     *     }
     *
     * */

    public BigDecimal getnewprice(String url){
        Map<String, Object> map = CommonUtil.doGet(url, null);
        JSONObject res = JSONObject.parseObject(map.get("res").toString());
        JSONArray data = JSONArray.parseArray(res.get("data").toString());
        JSONObject jsonObject = JSONObject.parseObject(data.get(0).toString());

        BigDecimal close = new BigDecimal(jsonObject.get("close").toString()).setScale(2, RoundingMode.HALF_UP);
        return close;
    }

    /**
     * 工具方法：写财务日志
     * 表：user_coin，bill，notice
     * 参数：uid (用户id)  username （用户名） money （钱）
     * 大致逻辑：
     *         1.查询 user_coin where userid=?
     *         2.拼接参数写入  bill表
     *         3.拼接参数写入  notice表
     * 参考代码：
     *     //写财务日志
     *     public function addlog($uid, $username, $money)
     *     {
     *         $minfo = M("user_coin")->where(array('userid' => $uid))->find();
     *         $data['uid'] = $uid;
     *         $data['username'] = $username;
     *         $data['num'] = $money;
     *         $data['coinname'] = "usdt";
     *         $data['afternum'] = $minfo['usdt'] + $money;
     *         $data['type'] = 4;
     *         $data['addtime'] = date("Y-m-d H:i:s", time());
     *         $data['st'] = 1;
     *         $data['remark'] = L('合约出售');
     *         M("bill")->add($data);
     *         $notice['uid'] = $uid;
     *         $notice['account'] = $username;
     *         $notice['title'] = L('秒合约交易');
     *         $notice['content'] = L('秒合约已平仓，请及时加仓');
     *         $notice['addtime'] = date("Y-m-d H:i:s", time());
     *         $notice['status'] = 1;
     *         M("notice")->add($notice);
     *     }
     * */

    public void addlog(int uid, String username,BigDecimal money){
        QueryWrapper<TwUserCoin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",uid);
        TwUserCoin twUserCoin = twUserCoinService.getOne(queryWrapper);

        //创建财务日志
        TwBill twBill = new TwBill();
        twBill.setUid(uid);
        twBill.setUsername(twUserCoin.getUsername());
        twBill.setNum(money);
        twBill.setCoinname("usdt");
        twBill.setAfternum(twUserCoinService.afternum(uid,"usdt"));
        twBill.setType(4);
        twBill.setAddtime(new Date());
        twBill.setSt(1);
        twBill.setRemark("合约出售");
        twBillService.save(twBill);

        TwNotice twNotice = new TwNotice();
        twNotice.setUid(uid);
        twNotice.setAccount(twUserCoin.getUsername());
        twNotice.setTitle("秒合约交易");
        twNotice.setContent("秒合约已平仓，请及时加仓");
        twNotice.setAddtime(new Date());
        twNotice.setStatus(1);
        twNoticeService.save(twNotice);
    }



}
