package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMcdHyOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.PerNumVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.ProfitVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwHyorderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface TwMcdHyorderMapper extends BaseMapper<TwMcdHyOrder> {

    List<TwMcdHyOrder> listpage(@Param("objectPage") Page<TwMcdHyOrder> objectPage, @Param("obj") TwHyorderVo twHyorderVo);

    List<ProfitVo> statisticProfit(@Param("companyId")int companyId, @Param("uid") int uid);

    @Select("select IFNULL(sum(num), 0) from tw_mcd_hyorder where uid = #{uid}")
    BigDecimal totalAmount(@Param("uid")int uid);

    @Select("select IFNULL(sum(ploss), 0) from tw_mcd_hyorder where uid = #{uid} AND status = 2")
    BigDecimal totalPloss(@Param("uid")int uid);
}
