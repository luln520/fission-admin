package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 用户币种表(TwUserCoin)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:28:58
 */
public interface TwUserCoinService extends IService<TwUserCoin> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwUserCoin queryById(String id);

    /**
     * 分页查询
     *
     * @param twUserCoin 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwUserCoin> queryByPage(TwUserCoin twUserCoin, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twUserCoin 实例对象
     * @return 实例对象
     */
    TwUserCoin insert(TwUserCoin twUserCoin);

    /**
     * 修改数据
     *
     * @param twUserCoin 实例对象
     * @return 实例对象
     */
    TwUserCoin update(TwUserCoin twUserCoin);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
