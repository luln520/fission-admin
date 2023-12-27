package service;


import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 网站配置表(TwConfig)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:21:16
 */
public interface TwConfigService extends IService<TwConfig> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwConfig queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twConfig 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwConfig> queryByPage(TwConfig twConfig, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twConfig 实例对象
     * @return 实例对象
     */
    TwConfig insert(TwConfig twConfig);

    /**
     * 修改数据
     *
     * @param twConfig 实例对象
     * @return 实例对象
     */
    TwConfig update(TwConfig twConfig);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
