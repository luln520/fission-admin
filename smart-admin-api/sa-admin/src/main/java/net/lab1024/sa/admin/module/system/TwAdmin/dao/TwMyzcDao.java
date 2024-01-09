package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwMyzc;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwMyzcVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 提币表(TwMyzc)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:26:37
 */
@Mapper
public interface TwMyzcDao extends BaseMapper<TwMyzc> {

    List<TwMyzc> listpage(@Param("objectPage") Page<TwMyzc> objectPage, @Param("obj") TwMyzcVo twMyzcVo);

}

