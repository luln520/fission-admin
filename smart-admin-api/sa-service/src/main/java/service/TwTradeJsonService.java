package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwTradeJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 交易图表表(TwTradeJson)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:28:01
 */
public interface TwTradeJsonService extends IService<TwTradeJson> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwTradeJson queryById(String id);

    /**
     * 分页查询
     *
     * @param twTradeJson 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwTradeJson> queryByPage(TwTradeJson twTradeJson, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twTradeJson 实例对象
     * @return 实例对象
     */
    TwTradeJson insert(TwTradeJson twTradeJson);

    /**
     * 修改数据
     *
     * @param twTradeJson 实例对象
     * @return 实例对象
     */
    TwTradeJson update(TwTradeJson twTradeJson);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
