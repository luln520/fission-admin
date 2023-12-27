package net.lab1024.sa.admin.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.TwBill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 操作日志(TwBill)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:20:24
 */
@Mapper
public interface TwBillDao extends BaseMapper<TwBill> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwBill queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param twBill 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwBill> queryAllByLimit(TwBill twBill, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twBill 查询条件
     * @return 总行数
     */
    long count(TwBill twBill);

    /**
     * 新增数据
     *
     * @param twBill 实例对象
     * @return 影响行数
     */
    int insert(TwBill twBill);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwBill> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwBill> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwBill> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwBill> entities);

    /**
     * 修改数据
     *
     * @param twBill 实例对象
     * @return 影响行数
     */
    int update(TwBill twBill);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

