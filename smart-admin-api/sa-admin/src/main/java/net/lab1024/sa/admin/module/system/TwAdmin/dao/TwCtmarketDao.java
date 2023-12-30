package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 合约交易对配置(TwCtmarket)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:21:46
 */
@Mapper
public interface TwCtmarketDao extends BaseMapper<TwCtmarket> {

    List<TwCtmarket> listpage(@Param("objectPage") Page<TwCtmarket> objectPage, @Param("obj") PageParam pageParam);
    List<TwCtmarket> listPCpage(@Param("objectPage") Page<TwCtmarket> objectPage, @Param("obj") PageParam pageParam);

}

