package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwTradeLog;
import net.lab1024.sa.admin.module.dao.TwTradeLogDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwTradeLogService;

import javax.annotation.Resource;

/**
 * (TwTradeLog)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:13
 */
@Service("twTradeLogService")
public class TwTradeLogServiceImpl extends ServiceImpl<TwTradeLogDao, TwTradeLog> implements TwTradeLogService {
    @Resource
    private TwTradeLogDao twTradeLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwTradeLog queryById(String id) {
        return this.twTradeLogDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twTradeLog 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwTradeLog> queryByPage(TwTradeLog twTradeLog, PageRequest pageRequest) {
        long total = this.twTradeLogDao.count(twTradeLog);
        return new PageImpl<>(this.twTradeLogDao.queryAllByLimit(twTradeLog, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twTradeLog 实例对象
     * @return 实例对象
     */
    @Override
    public TwTradeLog insert(TwTradeLog twTradeLog) {
        this.twTradeLogDao.insert(twTradeLog);
        return twTradeLog;
    }

    /**
     * 修改数据
     *
     * @param twTradeLog 实例对象
     * @return 实例对象
     */
    @Override
    public TwTradeLog update(TwTradeLog twTradeLog) {
        this.twTradeLogDao.update(twTradeLog);
        return this.queryById(twTradeLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twTradeLogDao.deleteById(id) > 0;
    }
}
