package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwDaohang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwDaohang)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:22:02
 */
public interface TwDaohangService extends IService<TwDaohang> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwDaohang queryById(String id);

    /**
     * 分页查询
     *
     * @param twDaohang 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwDaohang> queryByPage(TwDaohang twDaohang, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twDaohang 实例对象
     * @return 实例对象
     */
    TwDaohang insert(TwDaohang twDaohang);

    /**
     * 修改数据
     *
     * @param twDaohang 实例对象
     * @return 实例对象
     */
    TwDaohang update(TwDaohang twDaohang);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
