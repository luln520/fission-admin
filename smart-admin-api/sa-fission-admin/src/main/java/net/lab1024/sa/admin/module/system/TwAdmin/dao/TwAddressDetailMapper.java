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
}
