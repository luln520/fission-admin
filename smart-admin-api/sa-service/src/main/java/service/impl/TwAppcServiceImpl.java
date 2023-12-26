package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwAppc;
import net.lab1024.sa.admin.module.dao.TwAppcDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwAppcService;

import javax.annotation.Resource;

/**
 * (TwAppc)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:18:08
 */
@Service("twAppcService")
public class TwAppcServiceImpl extends ServiceImpl<TwAppcDao, TwAppc> implements TwAppcService {
    @Resource
    private TwAppcDao twAppcDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwAppc queryById(Integer id) {
        return this.twAppcDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twAppc 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwAppc> queryByPage(TwAppc twAppc, PageRequest pageRequest) {
        long total = this.twAppcDao.count(twAppc);
        return new PageImpl<>(this.twAppcDao.queryAllByLimit(twAppc, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twAppc 实例对象
     * @return 实例对象
     */
    @Override
    public TwAppc insert(TwAppc twAppc) {
        this.twAppcDao.insert(twAppc);
        return twAppc;
    }

    /**
     * 修改数据
     *
     * @param twAppc 实例对象
     * @return 实例对象
     */
    @Override
    public TwAppc update(TwAppc twAppc) {
        this.twAppcDao.update(twAppc);
        return this.queryById(twAppc.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twAppcDao.deleteById(id) > 0;
    }
}
