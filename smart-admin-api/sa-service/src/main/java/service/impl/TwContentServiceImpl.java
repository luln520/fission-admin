package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwContent;
import net.lab1024.sa.admin.module.dao.TwContentDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwContentService;

import javax.annotation.Resource;

/**
 * 公告内容(TwContent)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:21:31
 */
@Service("twContentService")
public class TwContentServiceImpl extends ServiceImpl<TwContentDao, TwContent> implements TwContentService {
    @Resource
    private TwContentDao twContentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwContent queryById(Integer id) {
        return this.twContentDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twContent 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwContent> queryByPage(TwContent twContent, PageRequest pageRequest) {
        long total = this.twContentDao.count(twContent);
        return new PageImpl<>(this.twContentDao.queryAllByLimit(twContent, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twContent 实例对象
     * @return 实例对象
     */
    @Override
    public TwContent insert(TwContent twContent) {
        this.twContentDao.insert(twContent);
        return twContent;
    }

    /**
     * 修改数据
     *
     * @param twContent 实例对象
     * @return 实例对象
     */
    @Override
    public TwContent update(TwContent twContent) {
        this.twContentDao.update(twContent);
        return this.queryById(twContent.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twContentDao.deleteById(id) > 0;
    }
}
