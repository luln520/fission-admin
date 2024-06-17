package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjorderVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjprofitVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 矿机收益表(TwKjprofit)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:24:29
 */
@Mapper
public interface TwKjprofitDao extends BaseMapper<TwKjprofit> {

    List<TwKjprofit> listpage(@Param("objectPage") Page<TwKjprofit> objectPage, @Param("obj") TwKjprofitVo twKjprofitVo);
}

