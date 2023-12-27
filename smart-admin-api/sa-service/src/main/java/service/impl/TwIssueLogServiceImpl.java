package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwIssueLog;
import net.lab1024.sa.admin.module.dao.TwIssueLogDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwIssueLogService;

import javax.annotation.Resource;

/**
 * 认购记录表(TwIssueLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:00
 */
@Service("twIssueLogService")
public class TwIssueLogServiceImpl extends ServiceImpl<TwIssueLogDao, TwIssueLog> implements TwIssueLogService {
    @Resource
    private TwIssueLogDao twIssueLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwIssueLog queryById(String id) {
        return this.twIssueLogDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twIssueLog 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwIssueLog> queryByPage(TwIssueLog twIssueLog, PageRequest pageRequest) {
        long total = this.twIssueLogDao.count(twIssueLog);
        return new PageImpl<>(this.twIssueLogDao.queryAllByLimit(twIssueLog, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twIssueLog 实例对象
     * @return 实例对象
     */
    @Override
    public TwIssueLog insert(TwIssueLog twIssueLog) {
        this.twIssueLogDao.insert(twIssueLog);
        return twIssueLog;
    }

    /**
     * 修改数据
     *
     * @param twIssueLog 实例对象
     * @return 实例对象
     */
    @Override
    public TwIssueLog update(TwIssueLog twIssueLog) {
        this.twIssueLogDao.update(twIssueLog);
        return this.queryById(twIssueLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twIssueLogDao.deleteById(id) > 0;
    }
}
