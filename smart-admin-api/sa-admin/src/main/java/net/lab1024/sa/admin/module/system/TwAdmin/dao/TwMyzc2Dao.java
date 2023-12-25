package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMenu;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMyzc2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 提币表(TwMyzc2)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:26:51
 */
@Mapper
public interface TwMyzc2Dao extends BaseMapper<TwMyzc2> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwMyzc2 queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param twMyzc2 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwMyzc2> queryAllByLimit(TwMyzc2 twMyzc2, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twMyzc2 查询条件
     * @return 总行数
     */
    long count(TwMyzc2 twMyzc2);

    /**
     * 新增数据
     *
     * @param twMyzc2 实例对象
     * @return 影响行数
     */
    int insert(TwMyzc2 twMyzc2);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwMyzc2> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwMyzc2> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwMyzc2> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwMyzc2> entities);

    /**
     * 修改数据
     *
     * @param twMyzc2 实例对象
     * @return 影响行数
     */
    int update(TwMyzc2 twMyzc2);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

