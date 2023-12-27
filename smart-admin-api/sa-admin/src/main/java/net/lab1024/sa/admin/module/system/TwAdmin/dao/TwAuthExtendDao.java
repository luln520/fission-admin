package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAuthExtend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (TwAuthExtend)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:18:46
 */
@Mapper
public interface TwAuthExtendDao extends BaseMapper<TwAuthExtend> {

    /**
     * 通过ID查询单条数据
     *
     * @param  主键
     * @return 实例对象
     */
    TwAuthExtend queryById( );

    /**
     * 查询指定行数据
     *
     * @param twAuthExtend 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwAuthExtend> queryAllByLimit(TwAuthExtend twAuthExtend, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twAuthExtend 查询条件
     * @return 总行数
     */
    long count(TwAuthExtend twAuthExtend);

    /**
     * 新增数据
     *
     * @param twAuthExtend 实例对象
     * @return 影响行数
     */
    int insert(TwAuthExtend twAuthExtend);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwAuthExtend> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwAuthExtend> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwAuthExtend> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwAuthExtend> entities);

    /**
     * 修改数据
     *
     * @param twAuthExtend 实例对象
     * @return 影响行数
     */
    int update(TwAuthExtend twAuthExtend);

    /**
     * 通过主键删除数据
     *
     * @param  主键
     * @return 影响行数
     */
    int deleteById( );

}

