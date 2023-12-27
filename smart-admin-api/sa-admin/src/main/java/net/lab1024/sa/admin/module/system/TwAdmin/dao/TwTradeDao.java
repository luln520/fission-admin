package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRecharge;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 交易下单表(TwTrade)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:27:48
 */
@Mapper
public interface TwTradeDao extends BaseMapper<TwTrade> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwTrade queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param twTrade 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwTrade> queryAllByLimit(TwTrade twTrade, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twTrade 查询条件
     * @return 总行数
     */
    long count(TwTrade twTrade);

    /**
     * 新增数据
     *
     * @param twTrade 实例对象
     * @return 影响行数
     */
    int insert(TwTrade twTrade);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwTrade> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwTrade> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwTrade> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwTrade> entities);

    /**
     * 修改数据
     *
     * @param twTrade 实例对象
     * @return 影响行数
     */
    int update(TwTrade twTrade);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

