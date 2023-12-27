package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwFooter;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 合约订单表(TwHyorder)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:23:17
 */
@Mapper
public interface TwHyorderDao extends BaseMapper<TwHyorder> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwHyorder queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param twHyorder 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwHyorder> queryAllByLimit(TwHyorder twHyorder, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twHyorder 查询条件
     * @return 总行数
     */
    long count(TwHyorder twHyorder);

    /**
     * 新增数据
     *
     * @param twHyorder 实例对象
     * @return 影响行数
     */
    int insert(TwHyorder twHyorder);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwHyorder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwHyorder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwHyorder> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwHyorder> entities);

    /**
     * 修改数据
     *
     * @param twHyorder 实例对象
     * @return 影响行数
     */
    int update(TwHyorder twHyorder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

