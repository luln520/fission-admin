package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMarketDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMenuDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMarket;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMenu;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMarketService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMenuService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:26:23
 */
@Service("twMenuService")
public class TwMenuServiceImpl extends ServiceImpl<TwMenuDao, TwMenu> implements TwMenuService {
    @Resource
    private TwMenuDao twMenuDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwMenu queryById(String id) {
        return this.twMenuDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwMenu> queryByPage(TwMenu twMenu, PageRequest pageRequest) {
        long total = this.twMenuDao.count(twMenu);
        return new PageImpl<>(this.twMenuDao.queryAllByLimit(twMenu, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twMenu 实例对象
     * @return 实例对象
     */
    @Override
    public TwMenu insert(TwMenu twMenu) {
        this.twMenuDao.insert(twMenu);
        return twMenu;
    }

    /**
     * 修改数据
     *
     * @param twMenu 实例对象
     * @return 实例对象
     */
    @Override
    public TwMenu update(TwMenu twMenu) {
        this.twMenuDao.update(twMenu);
        return this.queryById(twMenu.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twMenuDao.deleteById(id) > 0;
    }
}
