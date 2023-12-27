package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwMyzc2;
import net.lab1024.sa.admin.module.dao.TwMyzc2Dao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwMyzc2Service;

import javax.annotation.Resource;

/**
 * 提币表(TwMyzc2)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:26:52
 */
@Service("twMyzc2Service")
public class TwMyzc2ServiceImpl extends ServiceImpl<TwMyzc2Dao, TwMyzc2> implements TwMyzc2Service {
    @Resource
    private TwMyzc2Dao twMyzc2Dao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwMyzc2 queryById(String id) {
        return this.twMyzc2Dao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twMyzc2 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwMyzc2> queryByPage(TwMyzc2 twMyzc2, PageRequest pageRequest) {
        long total = this.twMyzc2Dao.count(twMyzc2);
        return new PageImpl<>(this.twMyzc2Dao.queryAllByLimit(twMyzc2, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twMyzc2 实例对象
     * @return 实例对象
     */
    @Override
    public TwMyzc2 insert(TwMyzc2 twMyzc2) {
        this.twMyzc2Dao.insert(twMyzc2);
        return twMyzc2;
    }

    /**
     * 修改数据
     *
     * @param twMyzc2 实例对象
     * @return 实例对象
     */
    @Override
    public TwMyzc2 update(TwMyzc2 twMyzc2) {
        this.twMyzc2Dao.update(twMyzc2);
        return this.queryById(twMyzc2.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twMyzc2Dao.deleteById(id) > 0;
    }
}
