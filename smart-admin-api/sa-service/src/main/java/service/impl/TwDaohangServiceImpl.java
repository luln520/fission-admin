package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwDaohang;
import net.lab1024.sa.admin.module.dao.TwDaohangDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwDaohangService;

import javax.annotation.Resource;

/**
 * (TwDaohang)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:22:02
 */
@Service("twDaohangService")
public class TwDaohangServiceImpl extends ServiceImpl<TwDaohangDao, TwDaohang> implements TwDaohangService {
    @Resource
    private TwDaohangDao twDaohangDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwDaohang queryById(String id) {
        return this.twDaohangDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twDaohang 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwDaohang> queryByPage(TwDaohang twDaohang, PageRequest pageRequest) {
        long total = this.twDaohangDao.count(twDaohang);
        return new PageImpl<>(this.twDaohangDao.queryAllByLimit(twDaohang, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twDaohang 实例对象
     * @return 实例对象
     */
    @Override
    public TwDaohang insert(TwDaohang twDaohang) {
        this.twDaohangDao.insert(twDaohang);
        return twDaohang;
    }

    /**
     * 修改数据
     *
     * @param twDaohang 实例对象
     * @return 实例对象
     */
    @Override
    public TwDaohang update(TwDaohang twDaohang) {
        this.twDaohangDao.update(twDaohang);
        return this.queryById(twDaohang.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twDaohangDao.deleteById(id) > 0;
    }
}
