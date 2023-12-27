package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwRechargeDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwTradeJsonDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRecharge;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTradeJson;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwRechargeService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwTradeJsonService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 交易图表表(TwTradeJson)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:01
 */
@Service("twTradeJsonService")
public class TwTradeJsonServiceImpl extends ServiceImpl<TwTradeJsonDao, TwTradeJson> implements TwTradeJsonService {
    @Resource
    private TwTradeJsonDao twTradeJsonDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwTradeJson queryById(String id) {
        return this.twTradeJsonDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twTradeJson 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwTradeJson> queryByPage(TwTradeJson twTradeJson, PageRequest pageRequest) {
        long total = this.twTradeJsonDao.count(twTradeJson);
        return new PageImpl<>(this.twTradeJsonDao.queryAllByLimit(twTradeJson, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twTradeJson 实例对象
     * @return 实例对象
     */
    @Override
    public TwTradeJson insert(TwTradeJson twTradeJson) {
        this.twTradeJsonDao.insert(twTradeJson);
        return twTradeJson;
    }

    /**
     * 修改数据
     *
     * @param twTradeJson 实例对象
     * @return 实例对象
     */
    @Override
    public TwTradeJson update(TwTradeJson twTradeJson) {
        this.twTradeJsonDao.update(twTradeJson);
        return this.queryById(twTradeJson.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twTradeJsonDao.deleteById(id) > 0;
    }
}
