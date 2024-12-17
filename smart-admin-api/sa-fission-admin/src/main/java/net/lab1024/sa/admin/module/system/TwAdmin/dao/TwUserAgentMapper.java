package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserAgent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AgentVo;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.TwNewsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_user_agent(会员代理表)】的数据库操作Mapper
* @createDate 2024-06-12 17:34:31
* @Entity generator.domain.TwUserAgent
*/
@Mapper
public interface TwUserAgentMapper extends BaseMapper<TwUserAgent> {

    List<TwUserAgent> listpage(@Param("objectPage") Page<TwUserAgent> objectPage, @Param("obj") AgentVo agentVo);

    @Update("update tw_user_agent set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%')")
    int updatePath(@Param("sourceId") int sourceId, @Param("destId") int destId);
}




