package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwFooter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwFooter)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:22:59
 */
public interface TwFooterService extends IService<TwFooter> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwFooter queryById(String id);

    /**
     * 分页查询
     *
     * @param twFooter 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwFooter> queryByPage(TwFooter twFooter, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twFooter 实例对象
     * @return 实例对象
     */
    TwFooter insert(TwFooter twFooter);

    /**
     * 修改数据
     *
     * @param twFooter 实例对象
     * @return 实例对象
     */
    TwFooter update(TwFooter twFooter);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
