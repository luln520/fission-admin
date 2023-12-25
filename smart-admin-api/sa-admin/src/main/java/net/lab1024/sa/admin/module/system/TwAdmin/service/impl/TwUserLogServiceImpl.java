package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserCoinDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserLogDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCoinService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserLogService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 用户记录表(TwUserLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:29:10
 */
@Service("twUserLogService")
public class TwUserLogServiceImpl extends ServiceImpl<TwUserLogDao, TwUserLog> implements TwUserLogService {
    @Resource
    private TwUserLogDao twUserLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwUserLog queryById(String id) {
        return this.twUserLogDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twUserLog 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwUserLog> queryByPage(TwUserLog twUserLog, PageRequest pageRequest) {
        long total = this.twUserLogDao.count(twUserLog);
        return new PageImpl<>(this.twUserLogDao.queryAllByLimit(twUserLog, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twUserLog 实例对象
     * @return 实例对象
     */
    @Override
    public TwUserLog insert(TwUserLog twUserLog) {
        this.twUserLogDao.insert(twUserLog);
        return twUserLog;
    }

    /**
     * 修改数据
     *
     * @param twUserLog 实例对象
     * @return 实例对象
     */
    @Override
    public TwUserLog update(TwUserLog twUserLog) {
        this.twUserLogDao.update(twUserLog);
        return this.queryById(twUserLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twUserLogDao.deleteById(id) > 0;
    }
}
