package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKjorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKjprofitDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjprofitVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKjorderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKjprofitService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.TwPCKjprofitVo;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * 矿机收益表(TwKjprofit)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:29
 */
@Service("twKjprofitService")
public class TwKjprofitServiceImpl extends ServiceImpl<TwKjprofitDao, TwKjprofit> implements TwKjprofitService {

    @Override
    public IPage<TwKjprofit> listpage(TwKjprofitVo twKjprofitVo) {
        Page<TwKjprofit> objectPage = new Page<>(twKjprofitVo.getPageNum(), twKjprofitVo.getPageSize());
        objectPage.setRecords(baseMapper.listpage(objectPage, twKjprofitVo));
        return objectPage;
    }

    @Override
    public List<TwKjprofit> kjprofit(int uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid",uid);
        // 按照 ID 倒序排列
        queryWrapper.orderByDesc("day");
        // 设置查询条数限制
        queryWrapper.last("LIMIT 50");
        List<TwKjprofit> list = this.list(queryWrapper);
        for (TwKjprofit twKjprofit:list){
            beforeDay(twKjprofit);
        }
         return list;
    }


    public void beforeDay(TwKjprofit twKjprofit) {
        Date day = twKjprofit.getDay();
        // 获取当前日期
        LocalDate localDate = LocalDate.now();
        // 指定一个日期
       // 将Date对象转换为Instant对象
        Instant instant = day.toInstant();

        // 将Instant对象转换为LocalDate对象
        LocalDate targetDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        // 判断当前日期是否在指定日期之前
        if (localDate.isBefore(targetDate)) {
//            status = 2
            twKjprofit.setStatus(2);
        } else if (localDate.isAfter(targetDate)) {
//            status = 1
            twKjprofit.setStatus(1);
        } else {
//            status = 2
            twKjprofit.setStatus(2);
        }
    }
}
