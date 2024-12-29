package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.PerNumVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.ProfitLossVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 1
* @description 针对表【tw_lever_order(杠杆订单表)】的数据库操作Mapper
* @createDate 2024-01-27 12:46:05
* @Entity generator.domain.TwLeverOrder
*/
@Mapper
public interface TwLeverOrderMapper extends BaseMapper<TwLeverOrder> {
    List<TwLeverOrder> listpage(@Param("objectPage") Page<TwLeverOrder> objectPage, @Param("obj") LeverVo leverVo);

    @Select("SELECT is_win as status, sum(ploss) as profitLoss FROM tw_lever_order where status = 2 AND company_id = #{companyId} group by is_win")
    List<ProfitLossVo> statisticProfitLoss(@Param("companyId") Integer companyId);

    @Select("SELECT sum(num) FROM tw_lever_order where company_id = #{companyId}")
    BigDecimal statisticAmountVolume(@Param("companyId") Integer companyId);

    @Select("SELECT IFNULL(SUM(num), 0) FROM tw_lever_order where uid = #{uId} and order_type = 1")
    BigDecimal queryUserAmountVolume(@Param("uId") Integer uId);

    List<PerNumVo> statisticPerNum(@Param("days")Integer days, @Param("startTime")Long startTime, @Param("endTime")Long endTime, @Param("companyId")int companyId);

    @Update("update tw_lever_order set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%')")
    int updatePath(@Param("sourceId") int sourceId, @Param("destId") int destId);

    @Update("update tw_lever_order set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%') AND uid = #{uid}")
    int updatePathPer(@Param("sourceId") int sourceId, @Param("destId") int destId, @Param("uid") int uid);
}




