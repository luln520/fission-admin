package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserLogService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 会员登录日志
 */
@RestController
@RequestMapping("/api/admin/userlog")
@Api(tags = {AdminSwaggerTagConst.System.TW_USERLOG})
public class TwUserLogController {


    @Autowired
    private TwUserLogService twUserLogService;
    /**
     * 查询操作日志列表 表 UserLog
     * 然后遍历得到的list
     * 查询 用户的名称
     * 参考代码：
     *       $list = M('UserLog')->order('id desc')->select();
     *         foreach ($list as $k => $v) {
     *             $list[$k]['username'] = M('User')->where(array('id' => $v['userid']))->getField('username');
     *         }
     * */
    @PostMapping("/list")
    @ApiOperation(value = "获取所有用户")
    @NoNeedLogin
    public ResponseDTO<IPage<TwUserLog>> listpage(@Valid @RequestBody TwUserVo twUserVo, HttpServletRequest request) {
        return ResponseDTO.ok(twUserLogService.listpage(twUserVo,request));
    }


}

