package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwCoinCommentDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinComment;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCoinCommentService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwCoinComment)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:20:50
 */
@Service("twCoinCommentService")
public class TwCoinCommentServiceImpl extends ServiceImpl<TwCoinCommentDao,TwCoinComment> implements TwCoinCommentService {
    @Resource
    private TwCoinCommentDao twCoinCommentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwCoinComment queryById(String id) {
        return this.twCoinCommentDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twCoinComment 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwCoinComment> queryByPage(TwCoinComment twCoinComment, PageRequest pageRequest) {
        long total = this.twCoinCommentDao.count(twCoinComment);
        return new PageImpl<>(this.twCoinCommentDao.queryAllByLimit(twCoinComment, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twCoinComment 实例对象
     * @return 实例对象
     */
    @Override
    public TwCoinComment insert(TwCoinComment twCoinComment) {
        this.twCoinCommentDao.insert(twCoinComment);
        return twCoinComment;
    }

    /**
     * 修改数据
     *
     * @param twCoinComment 实例对象
     * @return 实例对象
     */
    @Override
    public TwCoinComment update(TwCoinComment twCoinComment) {
        this.twCoinCommentDao.update(twCoinComment);
        return this.queryById(twCoinComment.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twCoinCommentDao.deleteById(id) > 0;
    }
}
