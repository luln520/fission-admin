package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwUserQianbao;
import net.lab1024.sa.admin.module.dao.TwUserQianbaoDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwUserQianbaoService;

import javax.annotation.Resource;

/**
 * 用户钱包表(TwUserQianbao)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:29:25
 */
@Service("twUserQianbaoService")
public class TwUserQianbaoServiceImpl extends ServiceImpl<TwUserQianbaoDao, TwUserQianbao> implements TwUserQianbaoService {
    @Resource
    private TwUserQianbaoDao twUserQianbaoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwUserQianbao queryById(String id) {
        return this.twUserQianbaoDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twUserQianbao 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwUserQianbao> queryByPage(TwUserQianbao twUserQianbao, PageRequest pageRequest) {
        long total = this.twUserQianbaoDao.count(twUserQianbao);
        return new PageImpl<>(this.twUserQianbaoDao.queryAllByLimit(twUserQianbao, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twUserQianbao 实例对象
     * @return 实例对象
     */
    @Override
    public TwUserQianbao insert(TwUserQianbao twUserQianbao) {
        this.twUserQianbaoDao.insert(twUserQianbao);
        return twUserQianbao;
    }

    /**
     * 修改数据
     *
     * @param twUserQianbao 实例对象
     * @return 实例对象
     */
    @Override
    public TwUserQianbao update(TwUserQianbao twUserQianbao) {
        this.twUserQianbaoDao.update(twUserQianbao);
        return this.queryById(twUserQianbao.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twUserQianbaoDao.deleteById(id) > 0;
    }
}
