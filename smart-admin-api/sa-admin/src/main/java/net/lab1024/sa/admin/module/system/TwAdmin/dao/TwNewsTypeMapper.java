package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNewsType;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_news_type(新闻分类)】的数据库操作Mapper
* @createDate 2024-01-12 17:03:04
* @Entity generator.domain.TwNewsType
*/
@Mapper
public interface TwNewsTypeMapper extends BaseMapper<TwNewsType> {
    List<TwNewsType> listpage(@Param("objectPage") Page<TwNewsType> objectPage, @Param("obj") PageParam pageParam);
}




