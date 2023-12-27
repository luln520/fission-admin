package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwAdver;
import net.lab1024.sa.admin.module.dao.TwAdverDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwAdverService;

import javax.annotation.Resource;

/**
 * 广告图片表(TwAdver)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:17:46
 */
@Service("twAdverService")
public class TwAdverServiceImpl extends ServiceImpl<TwAdverDao, TwAdver> implements TwAdverService {
    @Resource
    private TwAdverDao twAdverDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwAdver queryById(String id) {
        return this.twAdverDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twAdver 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwAdver> queryByPage(TwAdver twAdver, PageRequest pageRequest) {
        long total = this.twAdverDao.count(twAdver);
        return new PageImpl<>(this.twAdverDao.queryAllByLimit(twAdver, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twAdver 实例对象
     * @return 实例对象
     */
    @Override
    public TwAdver insert(TwAdver twAdver) {
        this.twAdverDao.insert(twAdver);
        return twAdver;
    }

    /**
     * 修改数据
     *
     * @param twAdver 实例对象
     * @return 实例对象
     */
    @Override
    public TwAdver update(TwAdver twAdver) {
        this.twAdverDao.update(twAdver);
        return this.queryById(twAdver.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twAdverDao.deleteById(id) > 0;
    }
}
