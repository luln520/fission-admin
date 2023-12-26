package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.TwUser;
import net.lab1024.sa.admin.module.dao.TwUserDao;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import service.TwUserService;

import javax.annotation.Resource;

/**
 * 用户信息表(TwUser)表服务实现类
 *
 * @author makejava
 * @since 2023-12-23 18:28:46
 */




@Service("twUserService")
public class TwUserServiceImpl extends ServiceImpl<TwUserDao, TwUser> implements TwUserService {
    @Resource
    private TwUserDao twUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TwUser queryById(String id) {
        return this.twUserDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param twUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TwUser> queryByPage(TwUser twUser, PageRequest pageRequest) {
        long total = this.twUserDao.count(twUser);
        return new PageImpl<>(this.twUserDao.queryAllByLimit(twUser, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param twUser 实例对象
     * @return 实例对象
     */
    @Override
    public TwUser insert(TwUser twUser) {
        this.twUserDao.insert(twUser);
        return twUser;
    }

    /**
     * 修改数据
     *
     * @param twUser 实例对象
     * @return 实例对象
     */
    @Override
    public TwUser update(TwUser twUser) {
        this.twUserDao.update(twUser);
        return this.queryById(twUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.twUserDao.deleteById(id) > 0;
    }
}
