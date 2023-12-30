package net.lab1024.sa.admin.module.system.TwPC.controller;

import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwContentService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwCtmarketService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.admin.module.system.TwPC.controller.Req.UserReq;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 网站首页
 */
@RestController
@RequestMapping("/api/pc/index")
public class IndexController {

    @Autowired
    private TwCtmarketService twCtmarketService;

    @Autowired
    private TwContentService twContentService;

    /**
     *  获取首页必要数据
     *  表 ctmarket，content
     *  大致逻辑 ：
     *      1.查询  ctmarket where status =1
     *      2.查询 content where status =1 order by id desc
     *      3.返回两个列表
     * */

    /**
     * 获取所有市场信息 表ctmarket order by sort asc
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取所有市场信息")
    @NoNeedLogin
    public ResponseDTO<IPage<TwCtmarket>> listpage(@Valid @RequestBody PageParam pageParam) {
        return ResponseDTO.ok(twCtmarketService.listPCpage(pageParam));
    }


    /**
     * 获取所有公告  表content 全部查询
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取所有公告")
    @NoNeedLogin
    public ResponseDTO<IPage<TwContent>> listContentPage(@Valid @RequestBody PageParam pageParam) {
        return ResponseDTO.ok(twContentService.listPCpage(pageParam));
    }

}

