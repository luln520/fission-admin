package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTyhyorder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 合约订单表(TwTyhyorder)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:28:27
 */
public interface TwTyhyorderService extends IService<TwTyhyorder> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwTyhyorder queryById(Integer id);

    /**
     * 分页查询
     *
     * @param twTyhyorder 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwTyhyorder> queryByPage(TwTyhyorder twTyhyorder, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twTyhyorder 实例对象
     * @return 实例对象
     */
    TwTyhyorder insert(TwTyhyorder twTyhyorder);

    /**
     * 修改数据
     *
     * @param twTyhyorder 实例对象
     * @return 实例对象
     */
    TwTyhyorder update(TwTyhyorder twTyhyorder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
