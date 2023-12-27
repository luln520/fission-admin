package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (TwAuthGroup)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:19:04
 */
@Mapper
public interface TwAuthGroupDao extends BaseMapper<TwAuthGroup> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwAuthGroup queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param twAuthGroup 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwAuthGroup> queryAllByLimit(TwAuthGroup twAuthGroup, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twAuthGroup 查询条件
     * @return 总行数
     */
    long count(TwAuthGroup twAuthGroup);

    /**
     * 新增数据
     *
     * @param twAuthGroup 实例对象
     * @return 影响行数
     */
    int insert(TwAuthGroup twAuthGroup);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwAuthGroup> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwAuthGroup> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwAuthGroup> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwAuthGroup> entities);

    /**
     * 修改数据
     *
     * @param twAuthGroup 实例对象
     * @return 影响行数
     */
    int update(TwAuthGroup twAuthGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

