package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserLogService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserQianbaoService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.TwUserQianbaoRes;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户钱包表(TwUserQianbao)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:29:25
 */
@Service("twUserQianbaoService")
public class TwUserQianbaoServiceImpl extends ServiceImpl<TwUserQianbaoDao, TwUserQianbao> implements TwUserQianbaoService {

    @Autowired
    private TwUserService twUserService;
    @Autowired
    private TwCoinService twCoinService;
    @Autowired
    private TwKjorderDao twKjorderDao;
    @Autowired
    private TwHyorderDao twHyorderDao;
    @Autowired
    private TwKjprofitDao twKjprofitDao;

    @Override
    public IPage<TwUserQianbao> listpage(TwUserVo twUserVo) {
            Page<TwUserQianbao> objectPage = new Page<>(twUserVo.getPageNum(), twUserVo.getPageSize());
            objectPage.setRecords(baseMapper.listpage(objectPage, twUserVo));
            return objectPage;
        }

    @Override
    public List<TwUserQianbao> lists(int uid, int companyId) {
        QueryWrapper<TwUserQianbao> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", uid);
        queryWrapper.eq("company_id", companyId);
        return this.list(queryWrapper);
    }

    @Override
    public boolean addUpdate(TwUserQianbao userQianBao) {
        return this.saveOrUpdate(userQianBao);
    }

    @Override
    public boolean del(int id) {
        return this.removeById(id);
    }

    @Override
    public ResponseDTO add(int uid, int oid, String address, String remark, String czline) {
        QueryWrapper<TwUserQianbao> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("userid", uid);
        List<TwUserQianbao> list = this.list(queryWrapper2);
        if (list.size()>0) {
            return ResponseDTO.userErrorParam("To add or modify an address, please contact customer service");
        }
        QueryWrapper<TwUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", uid);
        TwUser twUser = twUserService.getOne(queryWrapper);

        QueryWrapper<TwCoin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", oid);
        TwCoin twCoin = twCoinService.getOne(queryWrapper1);

        TwUserQianbao twUserQianbao = new TwUserQianbao();
        twUserQianbao.setUserid(uid);
        twUserQianbao.setCoinname(twUser.getUsername());
        twUserQianbao.setCzline(czline);
        twUserQianbao.setCompanyId(twUser.getCompanyId());
        twUserQianbao.setName(twCoin.getName());
        twUserQianbao.setRemark(remark);
        twUserQianbao.setAddr(address);
        twUserQianbao.setSort(1);
        twUserQianbao.setAddtime(new Date());
        twUserQianbao.setStatus(1);
        this.save(twUserQianbao);
        return ResponseDTO.ok();
    }


    @Override
    public ResponseDTO qbSum(int uid) {

        TwUserQianbaoRes twUserQianbaoRes = new TwUserQianbaoRes();

        // 设置时区为美东时间
        ZoneId easternTimeZone = ZoneId.of("America/New_York");

        // 获取当前日期
        LocalDate currentDate = LocalDate.now(easternTimeZone);

        // 早上八点
        LocalTime startOfDay1 = LocalTime.of(8, 0, 0);

        // 第二天早上八点
        LocalTime endOfDay1 = startOfDay1.plusHours(24L);

        // 一天的开始时间
        LocalDateTime startOfDay = LocalDateTime.of(currentDate, startOfDay1);

        // 一天的结束时间
        LocalDateTime endOfDay = LocalDateTime.of(currentDate.plusDays(1), endOfDay1);


//        // 获取当前日期
//        LocalDate today = LocalDate.now();
//
//        // 获取当天零晨（起始时间）
//        LocalDateTime startOfDay = today.atStartOfDay();
//
//        // 获取当天结束时间（23:59:59）
//        LocalDateTime endOfDay = LocalDateTime.of(today, LocalTime.MAX);

        // 格式化起始时间和结束时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTime = startOfDay.format(formatter);
        String endTime = endOfDay.format(formatter);

        BigDecimal todayKjnum = new BigDecimal(0);
        QueryWrapper<TwKjorder> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.select("IFNULL(SUM(buynum), 0) as buynum")
                .eq("uid", uid)
                .eq("status", 1)
                .ge("addtime", startTime)
                .lt("addtime", endTime);
        List<Map<String, Object>> result1 = twKjorderDao.selectMaps(queryWrapper1);
        if (result1.isEmpty()) {
            todayKjnum  = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object totalNumObject1 = result1.get(0).get("buynum");
        if (totalNumObject1 instanceof BigDecimal) {
            todayKjnum = ((BigDecimal) totalNumObject1).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject1 instanceof Long) {
            todayKjnum = BigDecimal.valueOf((Long) totalNumObject1).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject1 instanceof Integer) {
            todayKjnum = BigDecimal.valueOf((Integer) totalNumObject1).setScale(2, RoundingMode.HALF_UP);
        } else {
            // 处理其他可能的类型
            todayKjnum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal todayHynum = new BigDecimal(0);
        QueryWrapper<TwHyorder> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.select("IFNULL(SUM(num), 0) as num")
                .eq("uid", uid)
                .eq("status", 2)
                .ge("buytime", startTime)
                .lt("buytime", endTime);
        List<Map<String, Object>> result2 = twHyorderDao.selectMaps(queryWrapper2);
        if (result2.isEmpty()) {
            todayHynum  = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object totalNumObject2 = result2.get(0).get("num");
        if (totalNumObject2 instanceof BigDecimal) {
            todayHynum = ((BigDecimal) totalNumObject2).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject2 instanceof Long) {
            todayHynum = BigDecimal.valueOf((Long) totalNumObject2).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject2 instanceof Integer) {
            todayHynum = BigDecimal.valueOf((Integer) totalNumObject2).setScale(2, RoundingMode.HALF_UP);
        } else {
            // 处理其他可能的类型
            todayHynum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal sumnum = todayKjnum.add(todayHynum);
        twUserQianbaoRes.setSumnum(sumnum);

        BigDecimal todayKjOutnum = new BigDecimal(0);
        QueryWrapper<TwKjprofit> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.select("IFNULL(SUM(num), 0) as num")
                .eq("uid", uid)
                .ge("addtime", startTime)
                .lt("addtime", endTime);
        List<Map<String, Object>> result3 = twKjprofitDao.selectMaps(queryWrapper3);
        if (result3.isEmpty()) {
            todayKjOutnum  = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object totalNumObject3 = result3.get(0).get("num");
        if (totalNumObject3 instanceof BigDecimal) {
            todayKjOutnum = ((BigDecimal) totalNumObject3).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject3 instanceof Long) {
            todayKjOutnum = BigDecimal.valueOf((Long) totalNumObject3).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject3 instanceof Integer) {
            todayKjOutnum = BigDecimal.valueOf((Integer) totalNumObject3).setScale(2, RoundingMode.HALF_UP);
        } else {
            // 处理其他可能的类型
            todayKjOutnum = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }


        BigDecimal todayHywin = new BigDecimal(0);
        QueryWrapper<TwHyorder> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.select("IFNULL(SUM(ploss), 0) as ploss")
                .eq("uid", uid)
                .eq("is_win", 1)
                .ge("buytime", startTime)
                .lt("buytime", endTime);
        List<Map<String, Object>> result4 = twHyorderDao.selectMaps(queryWrapper4);
        if (result4.isEmpty()) {
            todayHywin  = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object totalNumObject4 = result4.get(0).get("ploss");
        if (totalNumObject4 instanceof BigDecimal) {
            todayHywin = ((BigDecimal) totalNumObject4).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject4 instanceof Long) {
            todayHywin = BigDecimal.valueOf((Long) totalNumObject4).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject4 instanceof Integer) {
            todayHywin = BigDecimal.valueOf((Integer) totalNumObject4).setScale(2, RoundingMode.HALF_UP);
        } else {
            // 处理其他可能的类型
            todayHywin = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal todayHyloss = new BigDecimal(0);
        QueryWrapper<TwHyorder> queryWrapper5 = new QueryWrapper<>();
        queryWrapper5.select("IFNULL(SUM(ploss), 0) as ploss")
                .eq("uid", uid)
                .eq("is_win", 2)
                .ge("buytime", startTime)
                .lt("buytime", endTime);
        List<Map<String, Object>> result5 = twHyorderDao.selectMaps(queryWrapper5);
        if (result5.isEmpty()) {
            todayHyloss  = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        Object totalNumObject5 = result5.get(0).get("ploss");
        if (totalNumObject5 instanceof BigDecimal) {
            todayHyloss = ((BigDecimal) totalNumObject5).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject5 instanceof Long) {
            todayHyloss = BigDecimal.valueOf((Long) totalNumObject5).setScale(2, RoundingMode.HALF_UP);
        } else if (totalNumObject5 instanceof Integer) {
            todayHyloss = BigDecimal.valueOf((Integer) totalNumObject5).setScale(2, RoundingMode.HALF_UP);
        } else {
            // 处理其他可能的类型
            todayHyloss = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal hySubtract = todayHywin.subtract(todayHyloss);


        BigDecimal todaynum = todayKjOutnum.add(hySubtract);
        twUserQianbaoRes.setTodaynum(todaynum);

        QueryWrapper queryKjorder = new QueryWrapper();
        queryKjorder.eq("uid",uid);
        queryKjorder.eq("status",1);
        queryKjorder.ge("addtime", startTime);
        queryKjorder.lt("addtime", endTime);
        int kjCount = twKjorderDao.selectCount(queryKjorder).intValue();

        QueryWrapper queryHyorder = new QueryWrapper();
        queryHyorder.eq("uid",uid);
        queryHyorder.eq("status",2);
        queryHyorder.ge("buytime", startTime);
        queryHyorder.lt("buytime", endTime);
        int hyCount = twHyorderDao.selectCount(queryHyorder).intValue();

        int count = kjCount + hyCount;
        twUserQianbaoRes.setCount(count);
        return ResponseDTO.ok(twUserQianbaoRes);
    }
}
