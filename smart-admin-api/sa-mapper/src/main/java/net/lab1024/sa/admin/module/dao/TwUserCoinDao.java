package net.lab1024.sa.admin.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.TwUserCoin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户币种表(TwUserCoin)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:28:58
 */
@Mapper
public interface TwUserCoinDao extends BaseMapper<TwUserCoin> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwUserCoin queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param twUserCoin 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwUserCoin> queryAllByLimit(TwUserCoin twUserCoin, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twUserCoin 查询条件
     * @return 总行数
     */
    long count(TwUserCoin twUserCoin);

    /**
     * 新增数据
     *
     * @param twUserCoin 实例对象
     * @return 影响行数
     */
    int insert(TwUserCoin twUserCoin);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwUserCoin> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwUserCoin> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwUserCoin> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwUserCoin> entities);

    /**
     * 修改数据
     *
     * @param twUserCoin 实例对象
     * @return 影响行数
     */
    int update(TwUserCoin twUserCoin);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

