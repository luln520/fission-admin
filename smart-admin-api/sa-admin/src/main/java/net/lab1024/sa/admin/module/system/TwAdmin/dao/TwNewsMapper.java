package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.common.common.domain.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_news(新闻)】的数据库操作Mapper
* @createDate 2024-01-12 17:02:15
* @Entity generator.domain.TwNews
*/
@Mapper
public interface TwNewsMapper extends BaseMapper<TwNews> {
    List<TwNews> listpage(@Param("objectPage") Page<TwNews> objectPage, @Param("obj") PageParam pageParam);
}




