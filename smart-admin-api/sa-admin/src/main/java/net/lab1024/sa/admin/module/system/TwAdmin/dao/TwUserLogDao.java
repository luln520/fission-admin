package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户记录表(TwUserLog)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:29:10
 */
@Mapper
public interface TwUserLogDao extends BaseMapper<TwUserLog> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwUserLog queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param twUserLog 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwUserLog> queryAllByLimit(TwUserLog twUserLog, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twUserLog 查询条件
     * @return 总行数
     */
    long count(TwUserLog twUserLog);

    /**
     * 新增数据
     *
     * @param twUserLog 实例对象
     * @return 影响行数
     */
    int insert(TwUserLog twUserLog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwUserLog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwUserLog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwUserLog> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwUserLog> entities);

    /**
     * 修改数据
     *
     * @param twUserLog 实例对象
     * @return 影响行数
     */
    int update(TwUserLog twUserLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

