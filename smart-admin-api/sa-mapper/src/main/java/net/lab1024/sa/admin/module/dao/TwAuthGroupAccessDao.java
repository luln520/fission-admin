package net.lab1024.sa.admin.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.TwAuthGroupAccess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (TwAuthGroupAccess)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:19:21
 */
@Mapper
public interface TwAuthGroupAccessDao extends BaseMapper<TwAuthGroupAccess> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwAuthGroupAccess queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param twAuthGroupAccess 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwAuthGroupAccess> queryAllByLimit(TwAuthGroupAccess twAuthGroupAccess, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twAuthGroupAccess 查询条件
     * @return 总行数
     */
    long count(TwAuthGroupAccess twAuthGroupAccess);

    /**
     * 新增数据
     *
     * @param twAuthGroupAccess 实例对象
     * @return 影响行数
     */
    int insert(TwAuthGroupAccess twAuthGroupAccess);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwAuthGroupAccess> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwAuthGroupAccess> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwAuthGroupAccess> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwAuthGroupAccess> entities);

    /**
     * 修改数据
     *
     * @param twAuthGroupAccess 实例对象
     * @return 影响行数
     */
    int update(TwAuthGroupAccess twAuthGroupAccess);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

