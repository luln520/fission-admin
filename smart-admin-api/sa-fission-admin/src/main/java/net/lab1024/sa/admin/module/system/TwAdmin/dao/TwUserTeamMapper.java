package net.lab1024.sa.admin.module.system.TwAdmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserAgent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserTeam;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AgentVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TeamVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 1
* @description 针对表【tw_user_team(会员团队)】的数据库操作Mapper
* @createDate 2024-06-12 17:35:11
* @Entity generator.domain.TwUserTeam
*/
@Mapper
public interface TwUserTeamMapper extends BaseMapper<TwUserTeam> {

    List<TwUserTeam> listpage(@Param("objectPage") Page<TwUserTeam> objectPage, @Param("obj") TeamVo teamVo);
    List<TwUser> teamlist(@Param("objectPage") Page<TwUser> objectPage, @Param("obj") TeamVo teamVo);

    @Update("update tw_user_team set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%')")
    int updatePath(@Param("sourceId") int sourceId, @Param("destId") int destId);

    @Update("update tw_user_team set path = REPLACE(path, #{sourceId}, #{destId}) WHERE path LIKE CONCAT('%', #{sourceId}, '%') AND uid = #{uid}")
    int updatePathPer(@Param("sourceId") int sourceId, @Param("destId") int destId, @Param("uid") int uid);
}




