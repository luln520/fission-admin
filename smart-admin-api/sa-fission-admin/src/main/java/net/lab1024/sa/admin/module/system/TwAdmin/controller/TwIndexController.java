package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.*;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.constant.RequestHeaderConst;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理后台首页
 */
@RestController
@RequestMapping("/api/admin/index")
@Api(tags = {AdminSwaggerTagConst.System.TW_INDEX})
public class TwIndexController {

    @Autowired
    private TwUserService twUserService;
    @Autowired
    private TwHyorderService twHyorderService;

    @Autowired
    private TwKjorderService twKjorderService;

    @Autowired
    private TwRechargeService twRechargeService;

    @Autowired
    private  TwMyzcService twMyzcService;

    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private TwLeverOrderService twLeverOrderService;


    /**
     * 系统首页数据
     */
    @GetMapping("/getIndexData")
    @ResponseBody
    public ResponseDTO getIndexData(HttpServletRequest request) {

        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(RequestHeaderConst.TOKEN);
        Long uidToken = tokenService.getUIDToken(xHeaderToken);
        EmployeeEntity byId = employeeService.getById(uidToken);
        int companyId = byId.getCompanyId();

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        Map<String, Object> result = new HashMap<>();
        // 获取当前时间
        Date now = new Date();
        String nowDate = DateUtil.format(now, "yyyy-MM-dd");
        // 获取今日开始和结束时间
        String startTime = nowDate + " 00:00:00";
        String endTime = nowDate + " 23:59:59";


        // 获取今天的日期
        LocalDate today = LocalDate.now();

        // 获取今天的开始时间和结束时间
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        // 将时间转换为时间戳（秒级别）
        long startTimestamp = startOfDay.toEpochSecond(ZoneOffset.UTC);
        long endTimestamp = endOfDay.toEpochSecond(ZoneOffset.UTC);

        //今日注册人数
        int todayUser = 0;
        todayUser = twUserService.countTodayUsers(startTimestamp,endTimestamp,companyId);
        // 查询全网总人数 M("user")->count()
        int allUser = 0;//userDao.countAllUsers();
        allUser = twUserService.countAllUsers(companyId);
        int ytUser = twUserService.countYtUsers(companyId);

        //用户总余额
        BigDecimal userCoinSum = new BigDecimal(100);
        userCoinSum = twUserCoinService.sumUserCoin(companyId);

        // 查询秒合约未平仓记录数 M("hyorder")->where 'status' => 1 ->count();
        int allHyOrders = 0;//hyOrderDao.countUnClosedOrders();
        allHyOrders = twHyorderService.countUnClosedOrders(companyId);
        // 查询全网矿机总数 M("kjorder")->where(array('status' => 1))->count();
        int allKjOrders = 0;//kjOrderDao.countAllOrders();
        allKjOrders = twKjorderService.countAllOrders(companyId);
        // 查询今日充值数量 $daywhere['addtime'] = ['between', [$statime, $endtime]];
        //        $daycz = M("recharge")->where($daywhere)->where(array('status' => 2))->sum("num");
        BigDecimal dayRecharge = new BigDecimal(0);//rechargeDao.sumDayRecharge(startTime, endTime);
        dayRecharge = twRechargeService.sumDayRecharge(startTime, endTime,companyId);
        // 查询充值数量   M("recharge")->where(array('status' => 2))->sum("num");
        BigDecimal allRecharge = new BigDecimal(0);//rechargeDao.sumAllRecharge();
        allRecharge = twRechargeService.sumAllRecharge(companyId);
        // 查询提币数量 M("myzc")->where($daywhere)->where(array('status' => 2))->sum("num");
        BigDecimal dayWithdraw = new BigDecimal(0);//myzcDao.sumDayWithdraw(startTime, endTime);
         dayWithdraw = twMyzcService.sumDayWithdraw(startTime, endTime,companyId);
        // 查询提币数量 M("myzc")->where(array('status' => 2))->sum("num");
        BigDecimal allWithdraw = new BigDecimal(0);//myzcDao.sumAllWithdraw();
        allWithdraw = twMyzcService.sumAllWithdraw(companyId);
        // 查询今日该客量  M("user")->where($linewhere)->count();  查询今天的
        int allLineUsers = 0;//userDao.countLineUsers(nowDate);
        allLineUsers = twUserService.countLineUsers(startTime, endTime,companyId);

        StatisticUserVo statisticUserVo = twUserService.statisticPerUserByDate(startDate, endDate, companyId);

        StatisticAmountVo hyStatisticAmountVo = twHyorderService.statisticProfitLoss(companyId);
        StatisticAmountVo leverStatisticAmountVo = twLeverOrderService.statisticProfitLoss(companyId);

        StatisticNumVo hyStatisticNumVo = twHyorderService.statisticNum(startDate, endDate, companyId);
        StatisticNumVo leverStatisticNumVo = twLeverOrderService.statisticNum(startDate, endDate, companyId);

        result.put("userStatistic", statisticUserVo);

        result.put("hyStatistic", hyStatisticAmountVo);
        result.put("leverStatistic", leverStatisticAmountVo);

        result.put("hyStatisticNum", hyStatisticNumVo);
        result.put("leverStatisticNum", leverStatisticNumVo);



        // 将结果放入Map返回
        result.put("todayUser",todayUser);
        result.put("allUser", allUser);
        result.put("userCoinSum",userCoinSum);
        result.put("ytUser", ytUser);

        result.put("allHyOrders", allHyOrders);
        result.put("allKjOrders", allKjOrders);
        result.put("dayRecharge", dayRecharge);
        result.put("allRecharge", allRecharge);
        result.put("dayWithdraw", dayWithdraw);
        result.put("allWithdraw", allWithdraw);
        result.put("allLineUsers", allLineUsers);

        return ResponseDTO.ok(result);
    }


}

