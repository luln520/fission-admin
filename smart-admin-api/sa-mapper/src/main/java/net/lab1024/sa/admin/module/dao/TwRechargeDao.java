package net.lab1024.sa.admin.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.TwRecharge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 充值记录(TwRecharge)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:27:30
 */
@Mapper
public interface TwRechargeDao  extends BaseMapper<TwRecharge> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwRecharge queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param twRecharge 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwRecharge> queryAllByLimit(TwRecharge twRecharge, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twRecharge 查询条件
     * @return 总行数
     */
    long count(TwRecharge twRecharge);

    /**
     * 新增数据
     *
     * @param twRecharge 实例对象
     * @return 影响行数
     */
    int insert(TwRecharge twRecharge);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwRecharge> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwRecharge> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwRecharge> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwRecharge> entities);

    /**
     * 修改数据
     *
     * @param twRecharge 实例对象
     * @return 影响行数
     */
    int update(TwRecharge twRecharge);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

