package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBborder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 币币交易记录(TwBborder)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:19:54
 */
public interface TwBborderService extends IService<TwBborder> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwBborder queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twBborder 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwBborder> queryByPage(TwBborder twBborder, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twBborder 实例对象
     * @return 实例对象
     */
    TwBborder insert(TwBborder twBborder);

    /**
     * 修改数据
     *
     * @param twBborder 实例对象
     * @return 实例对象
     */
    TwBborder update(TwBborder twBborder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
