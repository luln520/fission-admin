package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwKjprofit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 矿机收益表(TwKjprofit)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:24:29
 */
public interface TwKjprofitService extends IService<TwKjprofit> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwKjprofit queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twKjprofit 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwKjprofit> queryByPage(TwKjprofit twKjprofit, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twKjprofit 实例对象
     * @return 实例对象
     */
    TwKjprofit insert(TwKjprofit twKjprofit);

    /**
     * 修改数据
     *
     * @param twKjprofit 实例对象
     * @return 实例对象
     */
    TwKjprofit update(TwKjprofit twKjprofit);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
