package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwBill;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwOnline;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRecharge;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.PerNumVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwRechargeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 充值记录(TwRecharge)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:27:30
 */
@Mapper
public interface TwRechargeDao  extends BaseMapper<TwRecharge> {

    List<TwRecharge> listpage(@Param("objectPage") Page<TwRecharge> objectPage, @Param("obj") TwRechargeVo twRechargeVo);

    List<PerNumVo> statisticPerNum(@Param("days")Integer days, @Param("startTime")Long startTime, @Param("endTime")Long endTime, @Param("companyId")int companyId);

    @Select("SELECT IFNULL(SUM(num), 0) FROM tw_recharge where path like CONCAT('%', #{employeeId}, '%') AND `type`= #{type} AND atype = 1")
    BigDecimal queryAmountVolume(@Param("employeeId") Long employeeId, @Param("type") int type);

    @Update("update tw_recharge set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%')")
    int updatePath(@Param("sourceId") int sourceId, @Param("destId") int destId);

    @Update("update tw_recharge set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%') AND uid = #{uid}")
    int updatePathPer(@Param("sourceId") int sourceId, @Param("destId") int destId, @Param("uid") int uid);
}

