package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwOnlineDao;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.TwRechargeDao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwOnline;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRecharge;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwOnlineService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwRechargeService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 充值记录(TwRecharge)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:27:30
 */
@Service("twRechargeService")
public class TwRechargeServiceImpl extends ServiceImpl<TwRechargeDao, TwRecharge> implements TwRechargeService {
    @Resource
    private TwRechargeDao twRechargeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwRecharge queryById(Integer id) {
        return this.twRechargeDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twRecharge 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwRecharge> queryByPage(TwRecharge twRecharge, PageRequest pageRequest) {
        long total = this.twRechargeDao.count(twRecharge);
        return new PageImpl<>(this.twRechargeDao.queryAllByLimit(twRecharge, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twRecharge 实例对象
     * @return 实例对象
     */
    @Override
    public TwRecharge insert(TwRecharge twRecharge) {
        this.twRechargeDao.insert(twRecharge);
        return twRecharge;
    }

    /**
     * 修改数据
     *
     * @param twRecharge 实例对象
     * @return 实例对象
     */
    @Override
    public TwRecharge update(TwRecharge twRecharge) {
        this.twRechargeDao.update(twRecharge);
        return this.queryById(twRecharge.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twRechargeDao.deleteById(id) > 0;
    }
}
