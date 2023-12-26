package service;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwArea)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:18:24
 */
public interface TwAreaService extends IService<TwArea> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwArea queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twArea 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwArea> queryByPage(TwArea twArea, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twArea 实例对象
     * @return 实例对象
     */
    TwArea insert(TwArea twArea);

    /**
     * 修改数据
     *
     * @param twArea 实例对象
     * @return 实例对象
     */
    TwArea update(TwArea twArea);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
