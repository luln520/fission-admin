package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwKuangjiDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwKuangjiService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 矿机列表(TwKuangji)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:24:41
 */
@Service("twKuangjiService")
public class TwKuangjiServiceImpl extends ServiceImpl<TwKuangjiDao, TwKuangji> implements TwKuangjiService {
    @Resource
    private TwKuangjiDao twKuangjiDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwKuangji queryById(Integer id) {
        return this.twKuangjiDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twKuangji 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwKuangji> queryByPage(TwKuangji twKuangji, PageRequest pageRequest) {
        long total = this.twKuangjiDao.count(twKuangji);
        return new PageImpl<>(this.twKuangjiDao.queryAllByLimit(twKuangji, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twKuangji 实例对象
     * @return 实例对象
     */
    @Override
    public TwKuangji insert(TwKuangji twKuangji) {
        this.twKuangjiDao.insert(twKuangji);
        return twKuangji;
    }

    /**
     * 修改数据
     *
     * @param twKuangji 实例对象
     * @return 实例对象
     */
    @Override
    public TwKuangji update(TwKuangji twKuangji) {
        this.twKuangjiDao.update(twKuangji);
        return this.queryById(twKuangji.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twKuangjiDao.deleteById(id) > 0;
    }
}
