package net.lab1024.sa.admin.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.TwBbsetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 币币交易设置(TwBbsetting)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:20:11
 */
@Mapper
public interface TwBbsettingDao extends BaseMapper<TwBbsetting> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwBbsetting queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param twBbsetting 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwBbsetting> queryAllByLimit(TwBbsetting twBbsetting, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twBbsetting 查询条件
     * @return 总行数
     */
    long count(TwBbsetting twBbsetting);

    /**
     * 新增数据
     *
     * @param twBbsetting 实例对象
     * @return 影响行数
     */
    int insert(TwBbsetting twBbsetting);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwBbsetting> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwBbsetting> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwBbsetting> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwBbsetting> entities);

    /**
     * 修改数据
     *
     * @param twBbsetting 实例对象
     * @return 影响行数
     */
    int update(TwBbsetting twBbsetting);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

