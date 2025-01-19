package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwFooter;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.PerNumVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.PerUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.ProfitLossVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwHyorderVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 合约订单表(TwHyorder)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:23:17
 */
@Mapper
public interface TwHyorderDao extends BaseMapper<TwHyorder> {

    List<TwHyorder> listpage(@Param("objectPage") Page<TwHyorder> objectPage, @Param("obj") TwHyorderVo twHyorderVo);

    Integer countUnClosedOrders(@Param("companyId") Integer companyId);
    Integer countMockOrders(@Param("companyId") Integer companyId);
    Integer countHyOrdersDay(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("companyId") int companyId);
    Integer countHymockOrdersDay(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("companyId") int companyId);

    @Select("SELECT is_win as status, sum(ploss) as profitLoss FROM tw_hyorder where status = 2 AND company_id = #{companyId} group by is_win")
    List<ProfitLossVo> statisticProfitLoss(@Param("companyId") Integer companyId);

    @Select("SELECT IFNULL(SUM(num), 0) FROM tw_hyorder where uid = #{uId} and order_type = 1")
    BigDecimal queryUserAmountVolume(@Param("uId") Integer uId);

    @Select("SELECT sum(num) FROM tw_hyorder where company_id = #{companyId}")
    BigDecimal statisticAmountVolume(@Param("companyId") Integer companyId);

    List<PerNumVo> statisticPerNum(@Param("days")Integer days, @Param("startTime")Long startTime, @Param("endTime")Long endTime, @Param("companyId")int companyId);

    @Update("update tw_hyorder set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%')")
    int updatePath(@Param("sourceId") int sourceId, @Param("destId") int destId);

    @Update("update tw_hyorder set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%') AND uid = #{uid}")
    int updatePathPer(@Param("sourceId") int sourceId, @Param("destId") int destId, @Param("uid") int uid);

    @Select("select count(*) from (SELECT uid, count(*) FROM tw_hyorder where company_id = #{companyId} AND status = 2 AND order_type = 1 group by uid) s")
    int statisticDealCount(@Param("companyId") Integer companyId);
}

