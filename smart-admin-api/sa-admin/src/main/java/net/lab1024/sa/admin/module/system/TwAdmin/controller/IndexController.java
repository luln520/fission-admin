package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import cn.hutool.core.date.DateUtil;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwConfig;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAdminLogService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理后台首页
 */
@RestController
@RequestMapping("/api/admin/index")
public class IndexController {

    /**
     * 系统首页数据
     */
    @GetMapping("/getIndexData")
    @ResponseBody
    @NoNeedLogin
    public ResponseDTO getIndexData() {
        Map<String, Object> result = new HashMap<>();
        // 获取当前时间
        Date now = new Date();
        String nowDate = DateUtil.format(now, "yyyy-MM-dd");
        // 获取今日开始和结束时间
        String startTime = nowDate + " 00:00:00";
        String endTime = nowDate + " 23:59:59";

        // 查询全网总人数 M("user")->count()
        int allUser = 0;//userDao.countAllUsers();

        // 查询秒合约未平仓记录数 M("hyorder")->where 'status' => 1 ->count();
        int allHyOrders = 0;//hyOrderDao.countUnClosedOrders();

        // 查询币币交易额度 M("kjorder")->where(array('status' => 1))->count();
        double bbTradeVolume = 0;//bbOrderDao.sumTradeVolume();

        // 查询全网矿机总数 M("kjorder")->where(array('status' => 1))->count();
        int allKjOrders = 0;//kjOrderDao.countAllOrders();

        // 查询认购总数  M("issue_log")->where(array('status' => 1))->count();
        int allIssueLogs = 0;//issueLogDao.countAllIssueLogs();

        // 查询今日充值数量 $daywhere['addtime'] = ['between', [$statime, $endtime]];
        //        $daycz = M("recharge")->where($daywhere)->where(array('status' => 2))->sum("num");
        double dayRecharge = 0;//rechargeDao.sumDayRecharge(startTime, endTime);

        // 查询充值数量   M("recharge")->where(array('status' => 2))->sum("num");
        double allRecharge = 0;//rechargeDao.sumAllRecharge();

        // 查询提币数量 M("myzc")->where($daywhere)->where(array('status' => 2))->sum("num");
        double dayWithdraw = 0;//myzcDao.sumDayWithdraw(startTime, endTime);

        // 查询提币数量 M("myzc")->where(array('status' => 2))->sum("num");
        double allWithdraw = 0;//myzcDao.sumAllWithdraw();

        // 查询今日该客量  M("user")->where($linewhere)->count();  查询今天的
        int allLineUsers = 0;//userDao.countLineUsers(nowDate);

        // 将结果放入Map返回
        result.put("allUser", allUser);
        result.put("allHyOrders", allHyOrders);
        result.put("bbTradeVolume", bbTradeVolume);
        result.put("allKjOrders", allKjOrders);
        result.put("allIssueLogs", allIssueLogs);
        result.put("dayRecharge", dayRecharge);
        result.put("allRecharge", allRecharge);
        result.put("dayWithdraw", dayWithdraw);
        result.put("allWithdraw", allWithdraw);
        result.put("allLineUsers", allLineUsers);

        return ResponseDTO.ok(result);
    }
}

