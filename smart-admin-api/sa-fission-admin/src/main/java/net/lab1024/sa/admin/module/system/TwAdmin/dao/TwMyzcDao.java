package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMyzc;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.PerNumVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwMyzcVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

/**
 * 提币表(TwMyzc)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:26:37
 */
@Mapper
public interface TwMyzcDao extends BaseMapper<TwMyzc> {

    List<TwMyzc> listpage(@Param("objectPage") Page<TwMyzc> objectPage, @Param("obj") TwMyzcVo twMyzcVo);

    List<PerNumVo> statisticPerNum(@Param("days")Integer days, @Param("startTime")Long startTime, @Param("endTime")Long endTime, @Param("companyId")int companyId);

    @Select("SELECT IFNULL(SUM(num), 0) FROM tw_myzc where path like CONCAT('%', #{employeeId}, '%') AND status = 2")
    BigDecimal queryAmountVolume(@Param("employeeId") Long employeeId);

    @Update("update tw_myzc set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%')")
    int updatePath(@Param("sourceId") int sourceId, @Param("destId") int destId);

    @Update("update tw_myzc set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%') AND userid = #{uid}")
    int updatePathPer(@Param("sourceId") int sourceId, @Param("destId") int destId, @Param("uid") int uid);
}

