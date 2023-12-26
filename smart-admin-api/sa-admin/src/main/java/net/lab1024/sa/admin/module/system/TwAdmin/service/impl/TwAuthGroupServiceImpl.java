package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAuthGroupAccessDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwAuthGroupDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthGroup;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthGroupAccess;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAuthGroupAccessService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAuthGroupService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (TwAuthGroup)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:19:04
 */
@Service("twAuthGroupService")
public class TwAuthGroupServiceImpl extends ServiceImpl<TwAuthGroupDao, TwAuthGroup> implements TwAuthGroupService {
    @Resource
    private TwAuthGroupDao twAuthGroupDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwAuthGroup queryById(String id) {
        return this.twAuthGroupDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twAuthGroup 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwAuthGroup> queryByPage(TwAuthGroup twAuthGroup, PageRequest pageRequest) {
        long total = this.twAuthGroupDao.count(twAuthGroup);
        return new PageImpl<>(this.twAuthGroupDao.queryAllByLimit(twAuthGroup, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twAuthGroup 实例对象
     * @return 实例对象
     */
    @Override
    public TwAuthGroup insert(TwAuthGroup twAuthGroup) {
        this.twAuthGroupDao.insert(twAuthGroup);
        return twAuthGroup;
    }

    /**
     * 修改数据
     *
     * @param twAuthGroup 实例对象
     * @return 实例对象
     */
    @Override
    public TwAuthGroup update(TwAuthGroup twAuthGroup) {
        this.twAuthGroupDao.update(twAuthGroup);
        return this.queryById(twAuthGroup.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twAuthGroupDao.deleteById(id) > 0;
    }
}
