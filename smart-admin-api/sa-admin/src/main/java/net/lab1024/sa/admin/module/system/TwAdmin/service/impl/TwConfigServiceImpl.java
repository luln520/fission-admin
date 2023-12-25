package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCoinDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwConfigDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwConfig;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwConfigService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 网站配置表(TwConfig)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:21:16
 */
@Service("twConfigService")
public class TwConfigServiceImpl extends ServiceImpl<TwConfigDao, TwConfig> implements TwConfigService {
    @Resource
    private TwConfigDao twConfigDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwConfig queryById(Integer id) {
        return this.twConfigDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twConfig 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwConfig> queryByPage(TwConfig twConfig, PageRequest pageRequest) {
        long total = this.twConfigDao.count(twConfig);
        return new PageImpl<>(this.twConfigDao.queryAllByLimit(twConfig, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twConfig 实例对象
     * @return 实例对象
     */
    @Override
    public TwConfig insert(TwConfig twConfig) {
        this.twConfigDao.insert(twConfig);
        return twConfig;
    }

    /**
     * 修改数据
     *
     * @param twConfig 实例对象
     * @return 实例对象
     */
    @Override
    public TwConfig update(TwConfig twConfig) {
        this.twConfigDao.update(twConfig);
        return this.queryById(twConfig.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twConfigDao.deleteById(id) > 0;
    }
}
