package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwTradeLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwTradeLog)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:28:13
 */
public interface TwTradeLogService extends IService<TwTradeLog> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwTradeLog queryById(String id);

    /**
     * 分页查询
     *
     * @param twTradeLog 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwTradeLog> queryByPage(TwTradeLog twTradeLog, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twTradeLog 实例对象
     * @return 实例对象
     */
    TwTradeLog insert(TwTradeLog twTradeLog);

    /**
     * 修改数据
     *
     * @param twTradeLog 实例对象
     * @return 实例对象
     */
    TwTradeLog update(TwTradeLog twTradeLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
