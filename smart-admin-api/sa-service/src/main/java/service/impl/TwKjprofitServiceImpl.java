package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwKjprofit;
import net.lab1024.sa.admin.module.dao.TwKjprofitDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwKjprofitService;

import javax.annotation.Resource;

/**
 * 矿机收益表(TwKjprofit)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:29
 */
@Service("twKjprofitService")
public class TwKjprofitServiceImpl extends ServiceImpl<TwKjprofitDao, TwKjprofit> implements TwKjprofitService {
    @Resource
    private TwKjprofitDao twKjprofitDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwKjprofit queryById(Integer id) {
        return this.twKjprofitDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twKjprofit 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwKjprofit> queryByPage(TwKjprofit twKjprofit, PageRequest pageRequest) {
        long total = this.twKjprofitDao.count(twKjprofit);
        return new PageImpl<>(this.twKjprofitDao.queryAllByLimit(twKjprofit, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twKjprofit 实例对象
     * @return 实例对象
     */
    @Override
    public TwKjprofit insert(TwKjprofit twKjprofit) {
        this.twKjprofitDao.insert(twKjprofit);
        return twKjprofit;
    }

    /**
     * 修改数据
     *
     * @param twKjprofit 实例对象
     * @return 实例对象
     */
    @Override
    public TwKjprofit update(TwKjprofit twKjprofit) {
        this.twKjprofitDao.update(twKjprofit);
        return this.queryById(twKjprofit.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twKjprofitDao.deleteById(id) > 0;
    }
}
