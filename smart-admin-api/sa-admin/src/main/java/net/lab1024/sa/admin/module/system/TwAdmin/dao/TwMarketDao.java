package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMarket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 行情配置表(TwMarket)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:24:55
 */
@Mapper
public interface TwMarketDao extends BaseMapper<TwMarket> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwMarket queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param twMarket 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwMarket> queryAllByLimit(TwMarket twMarket, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twMarket 查询条件
     * @return 总行数
     */
    long count(TwMarket twMarket);

    /**
     * 新增数据
     *
     * @param twMarket 实例对象
     * @return 影响行数
     */
    int insert(TwMarket twMarket);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwMarket> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwMarket> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwMarket> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwMarket> entities);

    /**
     * 修改数据
     *
     * @param twMarket 实例对象
     * @return 影响行数
     */
    int update(TwMarket twMarket);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

