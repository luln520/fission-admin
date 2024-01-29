package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverSet;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwLeverage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.LeverVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_lever_set(杠杆止盈止损设置)】的数据库操作Mapper
* @createDate 2024-01-27 12:45:59
* @Entity generator.domain.TwLeverSet
*/
@Mapper
public interface TwLeverSetMapper extends BaseMapper<TwLeverSet> {
    List<TwLeverSet> listpage(@Param("objectPage") Page<TwLeverSet> objectPage, @Param("obj") LeverVo leverVo);
}




