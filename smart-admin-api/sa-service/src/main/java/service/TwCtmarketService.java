package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwCtmarket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 合约交易对配置(TwCtmarket)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:21:46
 */
public interface TwCtmarketService extends IService<TwCtmarket> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwCtmarket queryById(String id);

    /**
     * 分页查询
     *
     * @param twCtmarket 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwCtmarket> queryByPage(TwCtmarket twCtmarket, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twCtmarket 实例对象
     * @return 实例对象
     */
    TwCtmarket insert(TwCtmarket twCtmarket);

    /**
     * 修改数据
     *
     * @param twCtmarket 实例对象
     * @return 实例对象
     */
    TwCtmarket update(TwCtmarket twCtmarket);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
