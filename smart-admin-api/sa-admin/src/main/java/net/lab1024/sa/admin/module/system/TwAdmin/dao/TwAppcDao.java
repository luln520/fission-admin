package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAppc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (TwAppc)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:18:08
 */
@Mapper
public interface TwAppcDao extends BaseMapper<TwAppc> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwAppc queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param twAppc 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwAppc> queryAllByLimit(TwAppc twAppc, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twAppc 查询条件
     * @return 总行数
     */
    long count(TwAppc twAppc);

    /**
     * 新增数据
     *
     * @param twAppc 实例对象
     * @return 影响行数
     */
    int insert(TwAppc twAppc);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwAppc> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwAppc> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwAppc> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwAppc> entities);

    /**
     * 修改数据
     *
     * @param twAppc 实例对象
     * @return 影响行数
     */
    int update(TwAppc twAppc);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

