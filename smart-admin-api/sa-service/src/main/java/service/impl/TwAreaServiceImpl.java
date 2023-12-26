package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwArea;
import net.lab1024.sa.admin.module.dao.TwAreaDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwAreaService;

import javax.annotation.Resource;

/**
 * (TwArea)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:18:24
 */
@Service("twAreaService")
public class TwAreaServiceImpl extends ServiceImpl<TwAreaDao, TwArea> implements TwAreaService {
    @Resource
    private TwAreaDao twAreaDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwArea queryById(Integer id) {
        return this.twAreaDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twArea 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwArea> queryByPage(TwArea twArea, PageRequest pageRequest) {
        long total = this.twAreaDao.count(twArea);
        return new PageImpl<>(this.twAreaDao.queryAllByLimit(twArea, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twArea 实例对象
     * @return 实例对象
     */
    @Override
    public TwArea insert(TwArea twArea) {
        this.twAreaDao.insert(twArea);
        return twArea;
    }

    /**
     * 修改数据
     *
     * @param twArea 实例对象
     * @return 实例对象
     */
    @Override
    public TwArea update(TwArea twArea) {
        this.twAreaDao.update(twArea);
        return this.queryById(twArea.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twAreaDao.deleteById(id) > 0;
    }
}
