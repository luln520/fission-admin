package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwIssue;
import net.lab1024.sa.admin.module.dao.TwIssueDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwIssueService;

import javax.annotation.Resource;

/**
 * 认购发行表(TwIssue)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:23:47
 */
@Service("twIssueService")
public class TwIssueServiceImpl extends ServiceImpl<TwIssueDao, TwIssue> implements TwIssueService {
    @Resource
    private TwIssueDao twIssueDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwIssue queryById(String id) {
        return this.twIssueDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twIssue 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwIssue> queryByPage(TwIssue twIssue, PageRequest pageRequest) {
        long total = this.twIssueDao.count(twIssue);
        return new PageImpl<>(this.twIssueDao.queryAllByLimit(twIssue, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twIssue 实例对象
     * @return 实例对象
     */
    @Override
    public TwIssue insert(TwIssue twIssue) {
        this.twIssueDao.insert(twIssue);
        return twIssue;
    }

    /**
     * 修改数据
     *
     * @param twIssue 实例对象
     * @return 实例对象
     */
    @Override
    public TwIssue update(TwIssue twIssue) {
        this.twIssueDao.update(twIssue);
        return this.queryById(twIssue.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twIssueDao.deleteById(id) > 0;
    }
}
