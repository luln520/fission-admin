package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwTrade;
import net.lab1024.sa.admin.module.dao.TwTradeDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwTradeService;

import javax.annotation.Resource;

/**
 * 交易下单表(TwTrade)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:27:48
 */
@Service("twTradeService")
public class TwTradeServiceImpl extends ServiceImpl<TwTradeDao, TwTrade> implements TwTradeService {
    @Resource
    private TwTradeDao twTradeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwTrade queryById(String id) {
        return this.twTradeDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twTrade 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwTrade> queryByPage(TwTrade twTrade, PageRequest pageRequest) {
        long total = this.twTradeDao.count(twTrade);
        return new PageImpl<>(this.twTradeDao.queryAllByLimit(twTrade, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twTrade 实例对象
     * @return 实例对象
     */
    @Override
    public TwTrade insert(TwTrade twTrade) {
        this.twTradeDao.insert(twTrade);
        return twTrade;
    }

    /**
     * 修改数据
     *
     * @param twTrade 实例对象
     * @return 实例对象
     */
    @Override
    public TwTrade update(TwTrade twTrade) {
        this.twTradeDao.update(twTrade);
        return this.queryById(twTrade.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twTradeDao.deleteById(id) > 0;
    }
}
