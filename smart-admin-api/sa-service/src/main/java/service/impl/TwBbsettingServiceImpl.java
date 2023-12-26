package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwBbsetting;
import net.lab1024.sa.admin.module.dao.TwBbsettingDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwBbsettingService;

import javax.annotation.Resource;

/**
 * 币币交易设置(TwBbsetting)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:20:11
 */
@Service("twBbsettingService")
public class TwBbsettingServiceImpl extends ServiceImpl<TwBbsettingDao, TwBbsetting> implements TwBbsettingService {
    @Resource
    private TwBbsettingDao twBbsettingDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwBbsetting queryById(Integer id) {
        return this.twBbsettingDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twBbsetting 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwBbsetting> queryByPage(TwBbsetting twBbsetting, PageRequest pageRequest) {
        long total = this.twBbsettingDao.count(twBbsetting);
        return new PageImpl<>(this.twBbsettingDao.queryAllByLimit(twBbsetting, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twBbsetting 实例对象
     * @return 实例对象
     */
    @Override
    public TwBbsetting insert(TwBbsetting twBbsetting) {
        this.twBbsettingDao.insert(twBbsetting);
        return twBbsetting;
    }

    /**
     * 修改数据
     *
     * @param twBbsetting 实例对象
     * @return 实例对象
     */
    @Override
    public TwBbsetting update(TwBbsetting twBbsetting) {
        this.twBbsettingDao.update(twBbsetting);
        return this.queryById(twBbsetting.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twBbsettingDao.deleteById(id) > 0;
    }
}
