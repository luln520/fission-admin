package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 矿机列表(TwKuangji)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:24:41
 */
@Mapper
public interface TwKuangjiDao extends BaseMapper<TwKuangji> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwKuangji queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param twKuangji 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwKuangji> queryAllByLimit(TwKuangji twKuangji, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twKuangji 查询条件
     * @return 总行数
     */
    long count(TwKuangji twKuangji);

    /**
     * 新增数据
     *
     * @param twKuangji 实例对象
     * @return 影响行数
     */
    int insert(TwKuangji twKuangji);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwKuangji> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwKuangji> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwKuangji> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwKuangji> entities);

    /**
     * 修改数据
     *
     * @param twKuangji 实例对象
     * @return 影响行数
     */
    int update(TwKuangji twKuangji);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

