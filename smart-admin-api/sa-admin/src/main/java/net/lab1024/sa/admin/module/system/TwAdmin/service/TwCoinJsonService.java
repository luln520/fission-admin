package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinComment;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwCoinJson)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:21:02
 */
public interface TwCoinJsonService extends IService<TwCoinJson> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwCoinJson queryById(String id);

    /**
     * 分页查询
     *
     * @param twCoinJson 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwCoinJson> queryByPage(TwCoinJson twCoinJson, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twCoinJson 实例对象
     * @return 实例对象
     */
    TwCoinJson insert(TwCoinJson twCoinJson);

    /**
     * 修改数据
     *
     * @param twCoinJson 实例对象
     * @return 实例对象
     */
    TwCoinJson update(TwCoinJson twCoinJson);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
