package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMcdInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface TwMcdInfoMapper extends BaseMapper<TwMcdInfo> {

    @Select("select * from tw_mcd_info where uid = #{uid} AND follow_uid = #{followUid}")
    TwMcdInfo find(@Param("uid")int uid, @Param("followUid")int followUid);

    @Select("select * from tw_mcd_info where follow_uid = #{uid} AND status = 1")
    List<TwMcdInfo> findList(@Param("uid")int uid);

    @Select("select IFNULL(sum(amount), 0) from tw_mcd_info where follow_uid = #{uid}")
    BigDecimal totalAmount(@Param("uid")int uid);

    @Select("select count(*) from tw_mcd_info where follow_uid = #{uid}")
    int followCount(@Param("uid")int uid);

    @Select("select IFNULL(SUM(profit), 0) from tw_mcd_info where  follow_uid = #{uid}")
    BigDecimal totalProfit(@Param("uid")int uid);

    @Select("select * from tw_mcd_info where uid = #{uid} AND status = 1")
    List<TwMcdInfo> findFollowList(@Param("uid")int uid);
}
