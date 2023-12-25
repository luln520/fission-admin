package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 币种配置表(TwCoin)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:20:37
 */
public interface TwCoinService extends IService<TwCoin> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwCoin queryById(String id);

    /**
     * 分页查询
     *
     * @param twCoin 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwCoin> queryByPage(TwCoin twCoin, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twCoin 实例对象
     * @return 实例对象
     */
    TwCoin insert(TwCoin twCoin);

    /**
     * 修改数据
     *
     * @param twCoin 实例对象
     * @return 实例对象
     */
    TwCoin update(TwCoin twCoin);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
