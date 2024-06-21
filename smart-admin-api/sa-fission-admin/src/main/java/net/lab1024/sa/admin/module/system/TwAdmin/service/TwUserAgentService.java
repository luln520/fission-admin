package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwNews;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserAgent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AgentVo;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.TwNewsVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author 1
* @description 针对表【tw_user_agent(会员代理表)】的数据库操作Service
* @createDate 2024-06-12 17:34:31
*/
public interface TwUserAgentService extends IService<TwUserAgent> {


    IPage<TwUserAgent> listpage(AgentVo agentVo, HttpServletRequest request);
}
