package net.lab1024.sa.admin.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.TwUserQianbao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户钱包表(TwUserQianbao)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:29:25
 */
@Mapper
public interface TwUserQianbaoDao extends BaseMapper<TwUserQianbao> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwUserQianbao queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param twUserQianbao 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwUserQianbao> queryAllByLimit(TwUserQianbao twUserQianbao, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twUserQianbao 查询条件
     * @return 总行数
     */
    long count(TwUserQianbao twUserQianbao);

    /**
     * 新增数据
     *
     * @param twUserQianbao 实例对象
     * @return 影响行数
     */
    int insert(TwUserQianbao twUserQianbao);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwUserQianbao> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwUserQianbao> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwUserQianbao> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwUserQianbao> entities);

    /**
     * 修改数据
     *
     * @param twUserQianbao 实例对象
     * @return 影响行数
     */
    int update(TwUserQianbao twUserQianbao);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

