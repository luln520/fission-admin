package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwTrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 交易下单表(TwTrade)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:27:48
 */
public interface TwTradeService extends IService<TwTrade> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwTrade queryById(String id);

    /**
     * 分页查询
     *
     * @param twTrade 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwTrade> queryByPage(TwTrade twTrade, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twTrade 实例对象
     * @return 实例对象
     */
    TwTrade insert(TwTrade twTrade);

    /**
     * 修改数据
     *
     * @param twTrade 实例对象
     * @return 实例对象
     */
    TwTrade update(TwTrade twTrade);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
