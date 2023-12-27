package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMarketJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwMarketJson)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:25:08
 */
public interface TwMarketJsonService extends IService<TwMarketJson> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwMarketJson queryById(String id);

    /**
     * 分页查询
     *
     * @param twMarketJson 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwMarketJson> queryByPage(TwMarketJson twMarketJson, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twMarketJson 实例对象
     * @return 实例对象
     */
    TwMarketJson insert(TwMarketJson twMarketJson);

    /**
     * 修改数据
     *
     * @param twMarketJson 实例对象
     * @return 实例对象
     */
    TwMarketJson update(TwMarketJson twMarketJson);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
