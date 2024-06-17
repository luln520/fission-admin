package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjorderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 矿机订单表(TwKjorder)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:24:16
 */
@Mapper
public interface TwKjorderDao extends BaseMapper<TwKjorder> {

    List<TwKjorder> listpage(@Param("objectPage") Page<TwKjorder> objectPage, @Param("obj") TwKjorderVo twKjorderVo);
}

