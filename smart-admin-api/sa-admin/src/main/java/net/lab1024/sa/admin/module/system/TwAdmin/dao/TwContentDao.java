package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 公告内容(TwContent)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-23 18:21:31
 */
@Mapper
public interface TwContentDao extends BaseMapper<TwContent> {

    List<TwContent> listpage(@Param("objectPage") Page<TwContent> objectPage, @Param("obj") PageParam pageParam);
    List<TwContent> listPCpage(@Param("objectPage") Page<TwContent> objectPage, @Param("obj") PageParam pageParam);

}

