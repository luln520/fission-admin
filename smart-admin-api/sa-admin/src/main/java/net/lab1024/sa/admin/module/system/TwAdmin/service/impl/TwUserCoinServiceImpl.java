package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwTyhyorderDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwUserCoinDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTyhyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwTradeService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCoinService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 用户币种表(TwUserCoin)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:58
 */
@Service("twUserCoinService")
public class TwUserCoinServiceImpl extends ServiceImpl<TwUserCoinDao, TwUserCoin> implements TwUserCoinService {
    @Resource
    private TwUserCoinDao twUserCoinDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwUserCoin queryById(String id) {
        return this.twUserCoinDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twUserCoin 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwUserCoin> queryByPage(TwUserCoin twUserCoin, PageRequest pageRequest) {
        long total = this.twUserCoinDao.count(twUserCoin);
        return new PageImpl<>(this.twUserCoinDao.queryAllByLimit(twUserCoin, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twUserCoin 实例对象
     * @return 实例对象
     */
    @Override
    public TwUserCoin insert(TwUserCoin twUserCoin) {
        this.twUserCoinDao.insert(twUserCoin);
        return twUserCoin;
    }

    /**
     * 修改数据
     *
     * @param twUserCoin 实例对象
     * @return 实例对象
     */
    @Override
    public TwUserCoin update(TwUserCoin twUserCoin) {
        this.twUserCoinDao.update(twUserCoin);
        return this.queryById(twUserCoin.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twUserCoinDao.deleteById(id) > 0;
    }
}
