package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTradeJson;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwTradeLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (TwTradeLog)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:28:13
 */
@Mapper
public interface TwTradeLogDao extends BaseMapper<TwTradeLog> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwTradeLog queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param twTradeLog 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwTradeLog> queryAllByLimit(TwTradeLog twTradeLog, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twTradeLog 查询条件
     * @return 总行数
     */
    long count(TwTradeLog twTradeLog);

    /**
     * 新增数据
     *
     * @param twTradeLog 实例对象
     * @return 影响行数
     */
    int insert(TwTradeLog twTradeLog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwTradeLog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwTradeLog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwTradeLog> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwTradeLog> entities);

    /**
     * 修改数据
     *
     * @param twTradeLog 实例对象
     * @return 影响行数
     */
    int update(TwTradeLog twTradeLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

