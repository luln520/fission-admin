package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAdminLogDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCoinCommentDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinComment;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAdminLogService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinCommentService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 后台管理员操作日志表(TwAdminLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:17:09
 */
@Service("twAdminLogService")
public class TwAdminLogServiceImpl extends ServiceImpl<TwAdminLogDao, TwAdminLog> implements TwAdminLogService {
    @Resource
    private TwAdminLogDao twAdminLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public TwAdminLog queryById(String id) {
        return this.twAdminLogDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twAdminLog 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    public Page<TwAdminLog> queryByPage(TwAdminLog twAdminLog, PageRequest pageRequest) {
        long total = this.twAdminLogDao.count(twAdminLog);
        return new PageImpl<>(this.twAdminLogDao.queryAllByLimit(twAdminLog, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twAdminLog 实例对象
     * @return 实例对象
     */
    public TwAdminLog insert(TwAdminLog twAdminLog) {
        this.twAdminLogDao.insert(twAdminLog);
        return twAdminLog;
    }

    /**
     * 修改数据
     *
     * @param twAdminLog 实例对象
     * @return 实例对象
     */
    public TwAdminLog update(TwAdminLog twAdminLog) {
        this.twAdminLogDao.update(twAdminLog);
        return this.queryById(twAdminLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(String id) {
        return this.twAdminLogDao.deleteById(id) > 0;
    }
}
