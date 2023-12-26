package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwDjprofit;
import net.lab1024.sa.admin.module.dao.TwDjprofitDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwDjprofitService;

import javax.annotation.Resource;

/**
 * 数字币冻结记录表(TwDjprofit)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:22:15
 */
@Service("twDjprofitService")
public class TwDjprofitServiceImpl extends ServiceImpl<TwDjprofitDao, TwDjprofit> implements TwDjprofitService {
    @Resource
    private TwDjprofitDao twDjprofitDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwDjprofit queryById(Integer id) {
        return this.twDjprofitDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twDjprofit 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwDjprofit> queryByPage(TwDjprofit twDjprofit, PageRequest pageRequest) {
        long total = this.twDjprofitDao.count(twDjprofit);
        return new PageImpl<>(this.twDjprofitDao.queryAllByLimit(twDjprofit, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twDjprofit 实例对象
     * @return 实例对象
     */
    @Override
    public TwDjprofit insert(TwDjprofit twDjprofit) {
        this.twDjprofitDao.insert(twDjprofit);
        return twDjprofit;
    }

    /**
     * 修改数据
     *
     * @param twDjprofit 实例对象
     * @return 实例对象
     */
    @Override
    public TwDjprofit update(TwDjprofit twDjprofit) {
        this.twDjprofitDao.update(twDjprofit);
        return this.queryById(twDjprofit.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twDjprofitDao.deleteById(id) > 0;
    }
}
