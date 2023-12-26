package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwTradeDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwTyhyorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTrade;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTyhyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwTradeService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwTyhyorderService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 合约订单表(TwTyhyorder)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:27
 */
@Service("twTyhyorderService")
public class TwTyhyorderServiceImpl extends ServiceImpl<TwTyhyorderDao, TwTyhyorder> implements TwTyhyorderService {
    @Resource
    private TwTyhyorderDao twTyhyorderDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwTyhyorder queryById(Integer id) {
        return this.twTyhyorderDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twTyhyorder 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwTyhyorder> queryByPage(TwTyhyorder twTyhyorder, PageRequest pageRequest) {
        long total = this.twTyhyorderDao.count(twTyhyorder);
        return new PageImpl<>(this.twTyhyorderDao.queryAllByLimit(twTyhyorder, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twTyhyorder 实例对象
     * @return 实例对象
     */
    @Override
    public TwTyhyorder insert(TwTyhyorder twTyhyorder) {
        this.twTyhyorderDao.insert(twTyhyorder);
        return twTyhyorder;
    }

    /**
     * 修改数据
     *
     * @param twTyhyorder 实例对象
     * @return 实例对象
     */
    @Override
    public TwTyhyorder update(TwTyhyorder twTyhyorder) {
        this.twTyhyorderDao.update(twTyhyorder);
        return this.queryById(twTyhyorder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twTyhyorderDao.deleteById(id) > 0;
    }
}
