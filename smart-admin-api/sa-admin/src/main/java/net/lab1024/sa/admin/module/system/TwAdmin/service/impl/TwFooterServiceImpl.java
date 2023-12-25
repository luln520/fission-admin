package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwDjprofitDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwFooterDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwDjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwFooter;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwDjprofitService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwFooterService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwFooter)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:22:59
 */
@Service("twFooterService")
public class TwFooterServiceImpl extends ServiceImpl<TwFooterDao, TwFooter> implements TwFooterService {
    @Resource
    private TwFooterDao twFooterDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwFooter queryById(String id) {
        return this.twFooterDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twFooter 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwFooter> queryByPage(TwFooter twFooter, PageRequest pageRequest) {
        long total = this.twFooterDao.count(twFooter);
        return new PageImpl<>(this.twFooterDao.queryAllByLimit(twFooter, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twFooter 实例对象
     * @return 实例对象
     */
    @Override
    public TwFooter insert(TwFooter twFooter) {
        this.twFooterDao.insert(twFooter);
        return twFooter;
    }

    /**
     * 修改数据
     *
     * @param twFooter 实例对象
     * @return 实例对象
     */
    @Override
    public TwFooter update(TwFooter twFooter) {
        this.twFooterDao.update(twFooter);
        return this.queryById(twFooter.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twFooterDao.deleteById(id) > 0;
    }
}
