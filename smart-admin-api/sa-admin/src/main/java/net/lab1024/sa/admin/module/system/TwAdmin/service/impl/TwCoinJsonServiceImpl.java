package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCoinJsonDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinJson;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinJsonService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwCoinJson)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:21:02
 */
@Service("twCoinJsonService")
public class TwCoinJsonServiceImpl extends ServiceImpl<TwCoinJsonDao, TwCoinJson> implements TwCoinJsonService {
    @Resource
    private TwCoinJsonDao twCoinJsonDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwCoinJson queryById(String id) {
        return this.twCoinJsonDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twCoinJson 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwCoinJson> queryByPage(TwCoinJson twCoinJson, PageRequest pageRequest) {
        long total = this.twCoinJsonDao.count(twCoinJson);
        return new PageImpl<>(this.twCoinJsonDao.queryAllByLimit(twCoinJson, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twCoinJson 实例对象
     * @return 实例对象
     */
    @Override
    public TwCoinJson insert(TwCoinJson twCoinJson) {
        this.twCoinJsonDao.insert(twCoinJson);
        return twCoinJson;
    }

    /**
     * 修改数据
     *
     * @param twCoinJson 实例对象
     * @return 实例对象
     */
    @Override
    public TwCoinJson update(TwCoinJson twCoinJson) {
        this.twCoinJsonDao.update(twCoinJson);
        return this.queryById(twCoinJson.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twCoinJsonDao.deleteById(id) > 0;
    }
}
