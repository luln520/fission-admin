package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwC2c;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwRecharge;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.C2CVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwRechargeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_c2c】的数据库操作Mapper
* @createDate 2024-02-25 20:52:34
* @Entity generator.domain.TwC2c
*/
@Mapper
public interface TwC2cMapper extends BaseMapper<TwC2c> {

    List<TwC2c> listpage(@Param("objectPage") Page<TwC2c> objectPage, @Param("obj") C2CVo c2CVo);
}




