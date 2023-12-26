package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwBbsettingDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwBillDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBbsetting;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwBbsettingService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwBillService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 操作日志(TwBill)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:20:24
 */
@Service("twBillService")
public class TwBillServiceImpl extends ServiceImpl<TwBillDao, TwBill> implements TwBillService {
    @Resource
    private TwBillDao twBillDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwBill queryById(Integer id) {
        return this.twBillDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twBill 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwBill> queryByPage(TwBill twBill, PageRequest pageRequest) {
        long total = this.twBillDao.count(twBill);
        return new PageImpl<>(this.twBillDao.queryAllByLimit(twBill, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twBill 实例对象
     * @return 实例对象
     */
    @Override
    public TwBill insert(TwBill twBill) {
        this.twBillDao.insert(twBill);
        return twBill;
    }

    /**
     * 修改数据
     *
     * @param twBill 实例对象
     * @return 实例对象
     */
    @Override
    public TwBill update(TwBill twBill) {
        this.twBillDao.update(twBill);
        return this.queryById(twBill.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twBillDao.deleteById(id) > 0;
    }
}
