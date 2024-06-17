package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.TwNewsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_leverage(杠杆倍数)】的数据库操作Mapper
* @createDate 2024-01-27 12:45:43
* @Entity generator.domain.TwLeverage
*/
@Mapper
public interface TwLeverageMapper extends BaseMapper<TwLeverage> {
    List<TwLeverage> listpage(@Param("objectPage") Page<TwLeverage> objectPage, @Param("obj") LeverVo leverVo);
}




