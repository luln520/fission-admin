package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwMyzc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 提币表(TwMyzc)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:26:37
 */
public interface TwMyzcService extends IService<TwMyzc> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwMyzc queryById(String id);

    /**
     * 分页查询
     *
     * @param twMyzc 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwMyzc> queryByPage(TwMyzc twMyzc, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twMyzc 实例对象
     * @return 实例对象
     */
    TwMyzc insert(TwMyzc twMyzc);

    /**
     * 修改数据
     *
     * @param twMyzc 实例对象
     * @return 实例对象
     */
    TwMyzc update(TwMyzc twMyzc);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
