package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMarket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 行情配置表(TwMarket)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:24:55
 */
public interface TwMarketService extends IService<TwMarket> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwMarket queryById(String id);

    /**
     * 分页查询
     *
     * @param twMarket 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwMarket> queryByPage(TwMarket twMarket, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twMarket 实例对象
     * @return 实例对象
     */
    TwMarket insert(TwMarket twMarket);

    /**
     * 修改数据
     *
     * @param twMarket 实例对象
     * @return 实例对象
     */
    TwMarket update(TwMarket twMarket);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
