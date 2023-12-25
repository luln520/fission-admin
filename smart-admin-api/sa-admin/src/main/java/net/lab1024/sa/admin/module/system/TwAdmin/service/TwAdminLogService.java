package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 后台管理员操作日志表(TwAdminLog)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:17:08
 */
public interface TwAdminLogService extends IService<TwAdminLog> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwAdminLog queryById(String id);

    /**
     * 分页查询
     *
     * @param twAdminLog 筛选条件
     * @param pageRequest      分页对象
     *
     *
     *
     *
     * @return 查询结果
     */
    Page<TwAdminLog> queryByPage(TwAdminLog twAdminLog, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twAdminLog 实例对象
     * @return 实例对象
     */
    TwAdminLog insert(TwAdminLog twAdminLog);

    /**
     * 修改数据
     *
     * @param twAdminLog 实例对象
     * @return 实例对象
     */
    TwAdminLog update(TwAdminLog twAdminLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
