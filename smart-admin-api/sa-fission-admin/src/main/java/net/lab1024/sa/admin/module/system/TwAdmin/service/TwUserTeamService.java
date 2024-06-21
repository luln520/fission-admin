package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserAgent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserTeam;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AgentVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TeamVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author 1
* @description 针对表【tw_user_team(会员团队)】的数据库操作Service
* @createDate 2024-06-12 17:35:11
*/
public interface TwUserTeamService extends IService<TwUserTeam> {

    IPage<TwUserTeam> listpage(TeamVo teamVo, HttpServletRequest request);
}
