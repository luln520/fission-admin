package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwIssue;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwIssueLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 认购记录表(TwIssueLog)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:24:00
 */
@Mapper
public interface TwIssueLogDao extends BaseMapper<TwIssueLog> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwIssueLog queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param twIssueLog 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwIssueLog> queryAllByLimit(TwIssueLog twIssueLog, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twIssueLog 查询条件
     * @return 总行数
     */
    long count(TwIssueLog twIssueLog);

    /**
     * 新增数据
     *
     * @param twIssueLog 实例对象
     * @return 影响行数
     */
    int insert(TwIssueLog twIssueLog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwIssueLog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwIssueLog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwIssueLog> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwIssueLog> entities);

    /**
     * 修改数据
     *
     * @param twIssueLog 实例对象
     * @return 影响行数
     */
    int update(TwIssueLog twIssueLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

