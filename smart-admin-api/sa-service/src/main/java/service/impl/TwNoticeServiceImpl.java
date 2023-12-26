package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwNotice;
import net.lab1024.sa.admin.module.dao.TwNoticeDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwNoticeService;

import javax.annotation.Resource;

/**
 * 通知表(TwNotice)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:27:04
 */
@Service("twNoticeService")
public class TwNoticeServiceImpl extends ServiceImpl<TwNoticeDao, TwNotice> implements TwNoticeService {
    @Resource
    private TwNoticeDao twNoticeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwNotice queryById(Integer id) {
        return this.twNoticeDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twNotice 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwNotice> queryByPage(TwNotice twNotice, PageRequest pageRequest) {
        long total = this.twNoticeDao.count(twNotice);
        return new PageImpl<>(this.twNoticeDao.queryAllByLimit(twNotice, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twNotice 实例对象
     * @return 实例对象
     */
    @Override
    public TwNotice insert(TwNotice twNotice) {
        this.twNoticeDao.insert(twNotice);
        return twNotice;
    }

    /**
     * 修改数据
     *
     * @param twNotice 实例对象
     * @return 实例对象
     */
    @Override
    public TwNotice update(TwNotice twNotice) {
        this.twNoticeDao.update(twNotice);
        return this.queryById(twNotice.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twNoticeDao.deleteById(id) > 0;
    }
}
