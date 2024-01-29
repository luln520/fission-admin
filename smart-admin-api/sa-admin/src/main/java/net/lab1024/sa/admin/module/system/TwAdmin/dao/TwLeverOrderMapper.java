package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}




