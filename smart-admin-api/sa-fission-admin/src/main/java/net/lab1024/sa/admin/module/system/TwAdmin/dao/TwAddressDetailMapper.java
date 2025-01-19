package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddressDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface TwAddressDetailMapper extends BaseMapper<TwAddressDetail> {

    @Select("select td.* from tw_address_detail td left join tw_address ta on ta.id = td.address_id where ta.uid = #{uid} order by td.id desc ")
    List<TwAddressDetail> listAddressDetail(@Param("uid") int uid);

    @Select("SELECT IFNULL(SUM(amount), 0) FROM tw_address_detail where path like CONCAT('%', #{employeeId}, '%')")
    BigDecimal queryAmountVolume(@Param("employeeId") Long employeeId);

    @Update("update tw_address_detail set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%')")
    int updatePath(@Param("sourceId") int sourceId, @Param("destId") int destId);

    @Select("select count(*) from tw_address_detail where company_id = #{companyId} AND create_time BETWEEN FROM_UNIXTIME(#{startTime}) AND FROM_UNIXTIME(#{endTime})")
    int countInTime(@Param("startTime")Long startTime, @Param("endTime")Long endTime, @Param("companyId") int companyId);

    @Select("select IFNULL(SUM(amount), 0) from tw_address_detail where company_id = #{companyId} AND create_time BETWEEN FROM_UNIXTIME(#{startTime}) AND FROM_UNIXTIME(#{endTime})")
    BigDecimal amountInTime(@Param("startTime")Long startTime, @Param("endTime")Long endTime, @Param("companyId") int companyId);

    @Select("SELECT IFNULL(SUM(amount), 0) FROM tw_address_detail where company_id = #{companyId} ")
    BigDecimal queryAllAmount(@Param("companyId") int companyId);

    @Select("select count(*) from (SELECT ta.uid, count(*) FROM tw_address_detail tad left join tw_address ta on tad.address_id = ta.id where tad.company_id = #{companyId} group by ta.uid) s")
    int statisticRechargeCount(@Param("companyId") Integer companyId);

    @Select("select count(*) from (SELECT ta.uid, count(*) FROM tw_address_detail tad left join tw_address ta on tad.address_id = ta.id where tad.company_id = #{companyId} group by ta.uid having count(*) = 1) s")
    int statisticFirstRechargeCount(@Param("companyId") Integer companyId);
}
