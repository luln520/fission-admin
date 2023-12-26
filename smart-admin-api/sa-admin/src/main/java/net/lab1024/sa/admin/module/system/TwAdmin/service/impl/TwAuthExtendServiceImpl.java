package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAreaDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAuthExtendDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwArea;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthExtend;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAreaService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAuthExtendService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwAuthExtend)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:18:46
 */
@Service("twAuthExtendService")
public class TwAuthExtendServiceImpl extends ServiceImpl<TwAuthExtendDao, TwAuthExtend> implements TwAuthExtendService {
    @Resource
    private TwAuthExtendDao twAuthExtendDao;

    /**
     * 通过ID查询单条数据
     *
     * @param  主键
     * @return 实例对象
     */
    @Override
    public TwAuthExtend queryById() {
        return this.twAuthExtendDao.queryById();
    }

    /**
     * 分页查询
     *
     * @param twAuthExtend 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwAuthExtend> queryByPage(TwAuthExtend twAuthExtend, PageRequest pageRequest) {
        long total = this.twAuthExtendDao.count(twAuthExtend);
        return new PageImpl<>(this.twAuthExtendDao.queryAllByLimit(twAuthExtend, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twAuthExtend 实例对象
     * @return 实例对象
     */
    @Override
    public TwAuthExtend insert(TwAuthExtend twAuthExtend) {
        this.twAuthExtendDao.insert(twAuthExtend);
        return twAuthExtend;
    }

    @Override
    public TwAuthExtend update(TwAuthExtend twAuthExtend) {
        return null;
    }


    /**
     * 通过主键删除数据
     *
     * @param  主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById( ) {
        return this.twAuthExtendDao.deleteById() > 0;
    }
}
