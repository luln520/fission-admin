package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwAuthGroup)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:19:04
 */
public interface TwAuthGroupService extends IService<TwAuthGroup> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwAuthGroup queryById(String id);

    /**
     * 分页查询
     *
     * @param twAuthGroup 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwAuthGroup> queryByPage(TwAuthGroup twAuthGroup, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twAuthGroup 实例对象
     * @return 实例对象
     */
    TwAuthGroup insert(TwAuthGroup twAuthGroup);

    /**
     * 修改数据
     *
     * @param twAuthGroup 实例对象
     * @return 实例对象
     */
    TwAuthGroup update(TwAuthGroup twAuthGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
