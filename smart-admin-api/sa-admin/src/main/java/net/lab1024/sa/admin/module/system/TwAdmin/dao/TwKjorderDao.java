package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwIssueLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 矿机订单表(TwKjorder)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:24:16
 */
@Mapper
public interface TwKjorderDao extends BaseMapper<TwKjorder> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwKjorder queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param twKjorder 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwKjorder> queryAllByLimit(TwKjorder twKjorder, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twKjorder 查询条件
     * @return 总行数
     */
    long count(TwKjorder twKjorder);

    /**
     * 新增数据
     *
     * @param twKjorder 实例对象
     * @return 影响行数
     */
    int insert(TwKjorder twKjorder);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwKjorder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwKjorder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwKjorder> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwKjorder> entities);

    /**
     * 修改数据
     *
     * @param twKjorder 实例对象
     * @return 影响行数
     */
    int update(TwKjorder twKjorder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

