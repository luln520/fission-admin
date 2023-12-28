package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwIssueDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKjorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwIssue;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwIssueService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKjorderService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 矿机订单表(TwKjorder)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:16
 */
@Service("twKjorderService")
public class TwKjorderServiceImpl extends ServiceImpl<TwKjorderDao, TwKjorder> implements TwKjorderService {

    @Override
    public int countAllOrders() {
        QueryWrapper<TwKjorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "1"); // 添加查询条件
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }
}
