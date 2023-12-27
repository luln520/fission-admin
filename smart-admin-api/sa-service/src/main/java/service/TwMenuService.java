package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwMenu)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:26:23
 */
public interface TwMenuService extends IService<TwMenu> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwMenu queryById(String id);

    /**
     * 分页查询
     *
     * @param twMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwMenu> queryByPage(TwMenu twMenu, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twMenu 实例对象
     * @return 实例对象
     */
    TwMenu insert(TwMenu twMenu);

    /**
     * 修改数据
     *
     * @param twMenu 实例对象
     * @return 实例对象
     */
    TwMenu update(TwMenu twMenu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
