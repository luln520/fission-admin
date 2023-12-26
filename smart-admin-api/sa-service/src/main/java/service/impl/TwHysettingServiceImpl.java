package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwHysetting;
import net.lab1024.sa.admin.module.dao.TwHysettingDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwHysettingService;

import javax.annotation.Resource;

/**
 * 合约参数(TwHysetting)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:23:33
 */
@Service("twHysettingService")
public class TwHysettingServiceImpl extends ServiceImpl<TwHysettingDao, TwHysetting> implements TwHysettingService {
    @Resource
    private TwHysettingDao twHysettingDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwHysetting queryById(Integer id) {
        return this.twHysettingDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twHysetting 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwHysetting> queryByPage(TwHysetting twHysetting, PageRequest pageRequest) {
        long total = this.twHysettingDao.count(twHysetting);
        return new PageImpl<>(this.twHysettingDao.queryAllByLimit(twHysetting, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twHysetting 实例对象
     * @return 实例对象
     */
    @Override
    public TwHysetting insert(TwHysetting twHysetting) {
        this.twHysettingDao.insert(twHysetting);
        return twHysetting;
    }

    /**
     * 修改数据
     *
     * @param twHysetting 实例对象
     * @return 实例对象
     */
    @Override
    public TwHysetting update(TwHysetting twHysetting) {
        this.twHysettingDao.update(twHysetting);
        return this.queryById(twHysetting.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.twHysettingDao.deleteById(id) > 0;
    }
}
