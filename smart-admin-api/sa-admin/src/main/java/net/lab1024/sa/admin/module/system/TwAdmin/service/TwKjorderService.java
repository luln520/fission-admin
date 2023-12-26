package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 矿机订单表(TwKjorder)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:24:16
 */
public interface TwKjorderService extends IService<TwKjorder> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwKjorder queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twKjorder 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwKjorder> queryByPage(TwKjorder twKjorder, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twKjorder 实例对象
     * @return 实例对象
     */
    TwKjorder insert(TwKjorder twKjorder);

    /**
     * 修改数据
     *
     * @param twKjorder 实例对象
     * @return 实例对象
     */
    TwKjorder update(TwKjorder twKjorder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
