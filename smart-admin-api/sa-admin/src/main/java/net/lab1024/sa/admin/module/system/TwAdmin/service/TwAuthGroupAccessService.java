package net.lab1024.sa.admin.module.system.TwAdmin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthGroupAccess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (TwAuthGroupAccess)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:19:21
 */
public interface TwAuthGroupAccessService extends IService<TwAuthGroupAccess> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwAuthGroupAccess queryById(String id);

    /**
     * 分页查询
     *
     * @param twAuthGroupAccess 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwAuthGroupAccess> queryByPage(TwAuthGroupAccess twAuthGroupAccess, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twAuthGroupAccess 实例对象
     * @return 实例对象
     */
    TwAuthGroupAccess insert(TwAuthGroupAccess twAuthGroupAccess);

    /**
     * 修改数据
     *
     * @param twAuthGroupAccess 实例对象
     * @return 实例对象
     */
    TwAuthGroupAccess update(TwAuthGroupAccess twAuthGroupAccess);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
