package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwMyzc;
import net.lab1024.sa.admin.module.dao.TwMyzcDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwMyzcService;

import javax.annotation.Resource;

/**
 * 提币表(TwMyzc)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:26:37
 */
@Service("twMyzcService")
public class TwMyzcServiceImpl extends ServiceImpl<TwMyzcDao, TwMyzc> implements TwMyzcService {
    @Resource
    private TwMyzcDao twMyzcDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwMyzc queryById(String id) {
        return this.twMyzcDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twMyzc 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwMyzc> queryByPage(TwMyzc twMyzc, PageRequest pageRequest) {
        long total = this.twMyzcDao.count(twMyzc);
        return new PageImpl<>(this.twMyzcDao.queryAllByLimit(twMyzc, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twMyzc 实例对象
     * @return 实例对象
     */
    @Override
    public TwMyzc insert(TwMyzc twMyzc) {
        this.twMyzcDao.insert(twMyzc);
        return twMyzc;
    }

    /**
     * 修改数据
     *
     * @param twMyzc 实例对象
     * @return 实例对象
     */
    @Override
    public TwMyzc update(TwMyzc twMyzc) {
        this.twMyzcDao.update(twMyzc);
        return this.queryById(twMyzc.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twMyzcDao.deleteById(id) > 0;
    }
}
