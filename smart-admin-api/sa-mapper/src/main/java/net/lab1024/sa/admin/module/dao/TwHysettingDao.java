package net.lab1024.sa.admin.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.TwHysetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 合约参数(TwHysetting)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:23:33
 */
@Mapper
public interface TwHysettingDao extends BaseMapper<TwHysetting> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwHysetting queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param twHysetting 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwHysetting> queryAllByLimit(TwHysetting twHysetting, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twHysetting 查询条件
     * @return 总行数
     */
    long count(TwHysetting twHysetting);

    /**
     * 新增数据
     *
     * @param twHysetting 实例对象
     * @return 影响行数
     */
    int insert(TwHysetting twHysetting);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwHysetting> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwHysetting> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwHysetting> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwHysetting> entities);

    /**
     * 修改数据
     *
     * @param twHysetting 实例对象
     * @return 影响行数
     */
    int update(TwHysetting twHysetting);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

