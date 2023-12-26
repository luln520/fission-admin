package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwFooterDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwHyorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwFooter;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwFooterService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHyorderService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 合约订单表(TwHyorder)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:23:17
 */
@Service("twHyorderService")
public class TwHyorderServiceImpl extends ServiceImpl<TwHyorderDao, TwHyorder> implements TwHyorderService {
    @Resource
    private TwHyorderDao twHyorderDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwHyorder queryById(Integer id) {
        return this.twHyorderDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twHyorder 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwHyorder> queryByPage(TwHyorder twHyorder, PageRequest pageRequest) {
        long total = this.twHyorderDao.count(twHyorder);
        return new PageImpl<>(this.twHyorderDao.queryAllByLimit(twHyorder, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twHyorder 实例对象
     * @return 实例对象
     */
    @Override
    public TwHyorder insert(TwHyorder twHyorder) {
        this.twHyorderDao.insert(twHyorder);
        return twHyorder;
    }

    /**
     * 修改数据
     *
     * @param twHyorder 实例对象
     * @return 实例对象
     */
    @Override
    public TwHyorder update(TwHyorder twHyorder) {
        this.twHyorderDao.update(twHyorder);
        return this.queryById(twHyorder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twHyorderDao.deleteById(id) > 0;
    }
}
