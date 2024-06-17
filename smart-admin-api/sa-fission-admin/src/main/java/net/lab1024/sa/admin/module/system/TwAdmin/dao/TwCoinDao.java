package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 币种配置表(TwCoin)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:20:37
 */
@Mapper
public interface TwCoinDao extends BaseMapper<TwCoin> {

    List<TwCoin> listpage(@Param("objectPage") Page<TwCoin> objectPage, @Param("obj") PageParam pageParam);
}

