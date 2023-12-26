package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwBborder;
import net.lab1024.sa.admin.module.dao.TwBborderDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwBborderService;

import javax.annotation.Resource;

/**
 * 币币交易记录(TwBborder)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:19:54
 */
@Service("twBborderService")
public class TwBborderServiceImpl extends ServiceImpl<TwBborderDao, TwBborder> implements TwBborderService {
    @Resource
    private TwBborderDao twBborderDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwBborder queryById(Integer id) {
        return this.twBborderDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twBborder 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwBborder> queryByPage(TwBborder twBborder, PageRequest pageRequest) {
        long total = this.twBborderDao.count(twBborder);
        return new PageImpl<>(this.twBborderDao.queryAllByLimit(twBborder, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twBborder 实例对象
     * @return 实例对象
     */
    @Override
    public TwBborder insert(TwBborder twBborder) {
        this.twBborderDao.insert(twBborder);
        return twBborder;
    }

    /**
     * 修改数据
     *
     * @param twBborder 实例对象
     * @return 实例对象
     */
    @Override
    public TwBborder update(TwBborder twBborder) {
        this.twBborderDao.update(twBborder);
        return this.queryById(twBborder.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twBborderDao.deleteById(id) > 0;
    }
}
