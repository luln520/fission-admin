package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwCtmarket;
import net.lab1024.sa.admin.module.dao.TwCtmarketDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwCtmarketService;

import javax.annotation.Resource;

/**
 * 合约交易对配置(TwCtmarket)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:21:46
 */
@Service("twCtmarketService")
public class TwCtmarketServiceImpl extends ServiceImpl<TwCtmarketDao, TwCtmarket> implements TwCtmarketService {
    @Resource
    private TwCtmarketDao twCtmarketDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwCtmarket queryById(String id) {
        return this.twCtmarketDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twCtmarket 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwCtmarket> queryByPage(TwCtmarket twCtmarket, PageRequest pageRequest) {
        long total = this.twCtmarketDao.count(twCtmarket);
        return new PageImpl<>(this.twCtmarketDao.queryAllByLimit(twCtmarket, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twCtmarket 实例对象
     * @return 实例对象
     */
    @Override
    public TwCtmarket insert(TwCtmarket twCtmarket) {
        this.twCtmarketDao.insert(twCtmarket);
        return twCtmarket;
    }

    /**
     * 修改数据
     *
     * @param twCtmarket 实例对象
     * @return 实例对象
     */
    @Override
    public TwCtmarket update(TwCtmarket twCtmarket) {
        this.twCtmarketDao.update(twCtmarket);
        return this.queryById(twCtmarket.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twCtmarketDao.deleteById(id) > 0;
    }
}
