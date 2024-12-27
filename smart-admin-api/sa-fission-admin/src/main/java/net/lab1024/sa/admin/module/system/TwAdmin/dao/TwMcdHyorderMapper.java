package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMcdHyOrder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwHyorderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TwMcdHyorderMapper extends BaseMapper<TwMcdHyOrder> {

    List<TwMcdHyOrder> listpage(@Param("objectPage") Page<TwMcdHyOrder> objectPage, @Param("obj") TwHyorderVo twHyorderVo);
}
