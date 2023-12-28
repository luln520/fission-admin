package net.lab1024.sa.admin.module.system.TwAdmin.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwContentService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户
 *
 */
@RestController
@RequestMapping("/api/admin/user")
public class UserController {

    /**
     * 获取所有用户  表User 全部查询 order by id desc
     * 1.遍历user  通过 id=invit_1 id=invit_2 id=invit_3 查询表User到对应的username  当前用户的三个上级
     * 2.遍历user  通过 userid=？ 和 type=login 查询 user_log到上次登陆的信息
     */

}

