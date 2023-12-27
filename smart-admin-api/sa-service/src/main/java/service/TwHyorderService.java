package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwHyorder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 合约订单表(TwHyorder)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:23:17
 */
public interface TwHyorderService extends IService<TwHyorder> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwHyorder queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twHyorder 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwHyorder> queryByPage(TwHyorder twHyorder, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twHyorder 实例对象
     * @return 实例对象
     */
    TwHyorder insert(TwHyorder twHyorder);

    /**
     * 修改数据
     *
     * @param twHyorder 实例对象
     * @return 实例对象
     */
    TwHyorder update(TwHyorder twHyorder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
