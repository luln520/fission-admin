package service;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.TwUserLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 用户记录表(TwUserLog)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:29:10
 */
public interface TwUserLogService extends IService<TwUserLog> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwUserLog queryById(String id);

    /**
     * 分页查询
     *
     * @param twUserLog 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TwUserLog> queryByPage(TwUserLog twUserLog, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param twUserLog 实例对象
     * @return 实例对象
     */
    TwUserLog insert(TwUserLog twUserLog);

    /**
     * 修改数据
     *
     * @param twUserLog 实例对象
     * @return 实例对象
     */
    TwUserLog update(TwUserLog twUserLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
