package net.lab1024.sa.admin.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.TwAdver;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 广告图片表(TwAdver)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:17:46
 */
@Mapper
public interface TwAdverDao extends BaseMapper<TwAdver> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwAdver queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param twAdver 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwAdver> queryAllByLimit(TwAdver twAdver, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twAdver 查询条件
     * @return 总行数
     */
    long count(TwAdver twAdver);

    /**
     * 新增数据
     *
     * @param twAdver 实例对象
     * @return 影响行数
     */
    int insert(TwAdver twAdver);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwAdver> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwAdver> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwAdver> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwAdver> entities);

    /**
     * 修改数据
     *
     * @param twAdver 实例对象
     * @return 影响行数
     */
    int update(TwAdver twAdver);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

