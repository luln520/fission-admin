package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKuangjiDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwMarketJsonDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMarketJson;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKuangjiService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwMarketJsonService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwMarketJson)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:25:08
 */
@Service("twMarketJsonService")
public class TwMarketJsonServiceImpl extends ServiceImpl<TwMarketJsonDao, TwMarketJson> implements TwMarketJsonService {
    @Resource
    private TwMarketJsonDao twMarketJsonDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwMarketJson queryById(String id) {
        return this.twMarketJsonDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twMarketJson 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwMarketJson> queryByPage(TwMarketJson twMarketJson, PageRequest pageRequest) {
        long total = this.twMarketJsonDao.count(twMarketJson);
        return new PageImpl<>(this.twMarketJsonDao.queryAllByLimit(twMarketJson, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twMarketJson 实例对象
     * @return 实例对象
     */
    @Override
    public TwMarketJson insert(TwMarketJson twMarketJson) {
        this.twMarketJsonDao.insert(twMarketJson);
        return twMarketJson;
    }

    /**
     * 修改数据
     *
     * @param twMarketJson 实例对象
     * @return 实例对象
     */
    @Override
    public TwMarketJson update(TwMarketJson twMarketJson) {
        this.twMarketJsonDao.update(twMarketJson);
        return this.queryById(twMarketJson.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twMarketJsonDao.deleteById(id) > 0;
    }
}
