package service;
import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwAppc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwAppc)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:18:08
 */
public interface TwAppcService extends IService<TwAppc> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwAppc queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twAppc 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwAppc> queryByPage(TwAppc twAppc, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twAppc 实例对象
     * @return 实例对象
     */
    TwAppc insert(TwAppc twAppc);

    /**
     * 修改数据
     *
     * @param twAppc 实例对象
     * @return 实例对象
     */
    TwAppc update(TwAppc twAppc);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
