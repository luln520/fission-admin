package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 用户信息表(TwUser)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:28:46
 */
public interface TwUserService extends IService<TwUser> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwUser queryById(String id);

    /**
     * 分页查询
     *
     * @param twUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwUser> queryByPage(TwUser twUser, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twUser 实例对象
     * @return 实例对象
     */
    TwUser insert(TwUser twUser);

    /**
     * 修改数据
     *
     * @param twUser 实例对象
     * @return 实例对象
     */
    TwUser update(TwUser twUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
