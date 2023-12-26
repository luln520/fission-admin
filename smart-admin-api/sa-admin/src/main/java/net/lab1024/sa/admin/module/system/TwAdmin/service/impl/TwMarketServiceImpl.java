package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMarketDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMarketJsonDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMarket;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMarketJson;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMarketJsonService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMarketService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 行情配置表(TwMarket)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:55
 */
@Service("twMarketService")
public class TwMarketServiceImpl extends ServiceImpl<TwMarketDao, TwMarket> implements TwMarketService {
    @Resource
    private TwMarketDao twMarketDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwMarket queryById(String id) {
        return this.twMarketDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twMarket 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwMarket> queryByPage(TwMarket twMarket, PageRequest pageRequest) {
        long total = this.twMarketDao.count(twMarket);
        return new PageImpl<>(this.twMarketDao.queryAllByLimit(twMarket, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twMarket 实例对象
     * @return 实例对象
     */
    @Override
    public TwMarket insert(TwMarket twMarket) {
        this.twMarketDao.insert(twMarket);
        return twMarket;
    }

    /**
     * 修改数据
     *
     * @param twMarket 实例对象
     * @return 实例对象
     */
    @Override
    public TwMarket update(TwMarket twMarket) {
        this.twMarketDao.update(twMarket);
        return this.queryById(twMarket.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twMarketDao.deleteById(id) > 0;
    }
}
