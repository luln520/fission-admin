package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwKjorder;
import net.lab1024.sa.admin.module.dao.TwKjorderDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwKjorderService;

import javax.annotation.Resource;

/**
 * 矿机订单表(TwKjorder)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:16
 */
@Service("twKjorderService")
public class TwKjorderServiceImpl extends ServiceImpl<TwKjorderDao, TwKjorder> implements TwKjorderService {
    @Resource
    private TwKjorderDao twKjorderDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwKjorder queryById(Integer id) {
        return this.twKjorderDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twKjorder 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwKjorder> queryByPage(TwKjorder twKjorder, PageRequest pageRequest) {
        long total = this.twKjorderDao.count(twKjorder);
        return new PageImpl<>(this.twKjorderDao.queryAllByLimit(twKjorder, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twKjorder 实例对象
     * @return 实例对象
     */
    @Override
    public TwKjorder insert(TwKjorder twKjorder) {
        this.twKjorderDao.insert(twKjorder);
        return twKjorder;
    }

    /**
     * 修改数据
     *
     * @param twKjorder 实例对象
     * @return 实例对象
     */
    @Override
    public TwKjorder update(TwKjorder twKjorder) {
        this.twKjorderDao.update(twKjorder);
        return this.queryById(twKjorder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twKjorderDao.deleteById(id) > 0;
    }
}
