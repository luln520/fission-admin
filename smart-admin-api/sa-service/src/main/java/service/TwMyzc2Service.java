package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwMyzc2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 提币表(TwMyzc2)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:26:51
 */
public interface TwMyzc2Service extends IService<TwMyzc2> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwMyzc2 queryById(String id);

    /**
     * 分页查询
     *
     * @param twMyzc2 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwMyzc2> queryByPage(TwMyzc2 twMyzc2, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twMyzc2 实例对象
     * @return 实例对象
     */
    TwMyzc2 insert(TwMyzc2 twMyzc2);

    /**
     * 修改数据
     *
     * @param twMyzc2 实例对象
     * @return 实例对象
     */
    TwMyzc2 update(TwMyzc2 twMyzc2);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
