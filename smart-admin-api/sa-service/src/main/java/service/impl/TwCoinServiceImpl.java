package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwCoin;
import net.lab1024.sa.admin.module.dao.TwCoinDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwCoinService;

import javax.annotation.Resource;

/**
 * 币种配置表(TwCoin)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:20:37
 */
@Service("twCoinService")
public class TwCoinServiceImpl extends ServiceImpl<TwCoinDao, TwCoin> implements TwCoinService {
    @Resource
    private TwCoinDao twCoinDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwCoin queryById(String id) {
        return this.twCoinDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twCoin 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwCoin> queryByPage(TwCoin twCoin, PageRequest pageRequest) {
        long total = this.twCoinDao.count(twCoin);
        return new PageImpl<>(this.twCoinDao.queryAllByLimit(twCoin, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twCoin 实例对象
     * @return 实例对象
     */
    @Override
    public TwCoin insert(TwCoin twCoin) {
        this.twCoinDao.insert(twCoin);
        return twCoin;
    }

    /**
     * 修改数据
     *
     * @param twCoin 实例对象
     * @return 实例对象
     */
    @Override
    public TwCoin update(TwCoin twCoin) {
        this.twCoinDao.update(twCoin);
        return this.queryById(twCoin.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twCoinDao.deleteById(id) > 0;
    }
}
