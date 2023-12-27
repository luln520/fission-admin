package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwOnline;
import net.lab1024.sa.admin.module.dao.TwOnlineDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwOnlineService;

import javax.annotation.Resource;

/**
 * (TwOnline)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:27:16
 */
@Service("twOnlineService")
public class TwOnlineServiceImpl extends ServiceImpl<TwOnlineDao, TwOnline> implements TwOnlineService {
    @Resource
    private TwOnlineDao twOnlineDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwOnline queryById(Integer id) {
        return this.twOnlineDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twOnline 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwOnline> queryByPage(TwOnline twOnline, PageRequest pageRequest) {
        long total = this.twOnlineDao.count(twOnline);
        return new PageImpl<>(this.twOnlineDao.queryAllByLimit(twOnline, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twOnline 实例对象
     * @return 实例对象
     */
    @Override
    public TwOnline insert(TwOnline twOnline) {
        this.twOnlineDao.insert(twOnline);
        return twOnline;
    }

    /**
     * 修改数据
     *
     * @param twOnline 实例对象
     * @return 实例对象
     */
    @Override
    public TwOnline update(TwOnline twOnline) {
        this.twOnlineDao.update(twOnline);
        return this.queryById(twOnline.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twOnlineDao.deleteById(id) > 0;
    }
}
