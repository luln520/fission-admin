package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwIssueLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 认购记录表(TwIssueLog)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:24:00
 */
public interface TwIssueLogService extends IService<TwIssueLog> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwIssueLog queryById(String id);

    /**
     * 分页查询
     *
     * @param twIssueLog 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwIssueLog> queryByPage(TwIssueLog twIssueLog, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twIssueLog 实例对象
     * @return 实例对象
     */
    TwIssueLog insert(TwIssueLog twIssueLog);

    /**
     * 修改数据
     *
     * @param twIssueLog 实例对象
     * @return 实例对象
     */
    TwIssueLog update(TwIssueLog twIssueLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
