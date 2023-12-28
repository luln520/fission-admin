package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import cn.hutool.core.date.DateUtil;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwConfig;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理后台首页
 */
@RestController
@RequestMapping("/api/admin/index")
public class IndexController {

    @Autowired
    private TwUserService twUserService;
    @Autowired
    private TwHyorderService twHyorderService;

    @Autowired
    private TwKjorderService twKjorderService;

    @Autowired
    private TwIssueLogService twIssueLogService;

    @Autowired
    private TwRechargeService twRechargeService;

    @Autowired
    private  TwMyzcService twMyzcService;
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
        twUserService.countAllUsers();
        // 查询秒合约未平仓记录数 M("hyorder")->where 'status' => 1 ->count();
        int allHyOrders = 0;//hyOrderDao.countUnClosedOrders();
        twHyorderService.countUnClosedOrders();
        // 查询全网矿机总数 M("kjorder")->where(array('status' => 1))->count();
        int allKjOrders = 0;//kjOrderDao.countAllOrders();
        twKjorderService.countAllOrders();
        // 查询认购总数  M("issue_log")->where(array('status' => 1))->count();
        int allIssueLogs = 0;//issueLogDao.countAllIssueLogs();
        twIssueLogService.countAllIssueLogs();
        // 查询今日充值数量 $daywhere['addtime'] = ['between', [$statime, $endtime]];
        //        $daycz = M("recharge")->where($daywhere)->where(array('status' => 2))->sum("num");
        BigDecimal dayRecharge = new BigDecimal(0);//rechargeDao.sumDayRecharge(startTime, endTime);
        dayRecharge = twRechargeService.sumDayRecharge(startTime, endTime);
        // 查询充值数量   M("recharge")->where(array('status' => 2))->sum("num");
        BigDecimal allRecharge = new BigDecimal(0);//rechargeDao.sumAllRecharge();
        allRecharge = twRechargeService.sumAllRecharge();
        // 查询提币数量 M("myzc")->where($daywhere)->where(array('status' => 2))->sum("num");
        BigDecimal dayWithdraw = new BigDecimal(0);//myzcDao.sumDayWithdraw(startTime, endTime);
         dayWithdraw = twMyzcService.sumDayWithdraw(startTime, endTime);
        // 查询提币数量 M("myzc")->where(array('status' => 2))->sum("num");
        BigDecimal allWithdraw = new BigDecimal(0);//myzcDao.sumAllWithdraw();
        allWithdraw = twMyzcService.sumAllWithdraw();
        // 查询今日该客量  M("user")->where($linewhere)->count();  查询今天的
        int allLineUsers = 0;//userDao.countLineUsers(nowDate);
        twUserService.countLineUsers(startTime , endTime);
        // 将结果放入Map返回
        result.put("allUser", allUser);
        result.put("allHyOrders", allHyOrders);
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

