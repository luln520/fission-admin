package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwHysettingDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwIssueLogDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHysetting;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwIssueLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHysettingService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwIssueLogService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 认购记录表(TwIssueLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:00
 */
@Service("twIssueLogService")
public class TwIssueLogServiceImpl extends ServiceImpl<TwIssueLogDao, TwIssueLog> implements TwIssueLogService {

    @Override
    public int countAllIssueLogs() {
        QueryWrapper<TwIssueLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "1"); // 添加查询条件
        return this.baseMapper.selectCount(queryWrapper).intValue();
    }
}
