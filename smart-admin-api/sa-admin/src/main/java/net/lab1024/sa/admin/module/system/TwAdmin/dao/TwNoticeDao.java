package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMyzc;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 通知表(TwNotice)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:27:04
 */
@Mapper
public interface TwNoticeDao extends BaseMapper<TwNotice> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TwNotice queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param twNotice 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<TwNotice> queryAllByLimit(TwNotice twNotice, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param twNotice 查询条件
     * @return 总行数
     */
    long count(TwNotice twNotice);

    /**
     * 新增数据
     *
     * @param twNotice 实例对象
     * @return 影响行数
     */
    int insert(TwNotice twNotice);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwNotice> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TwNotice> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TwNotice> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TwNotice> entities);

    /**
     * 修改数据
     *
     * @param twNotice 实例对象
     * @return 影响行数
     */
    int update(TwNotice twNotice);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

