package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCoinService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 用户财产
 */
@RestController
@RequestMapping("/api/admin/usercoin")
@Api(tags = {AdminSwaggerTagConst.System.TW_USERCOIN})
public class TwUserCoinController {

    @Autowired
    private TwUserCoinService twUserCoinService;
    /**
     * 获取用户财产列表 表 UserCoin
     * order by id desc
     * 然后遍历得到的list
     *  查询 用户的名称
     *  参考代码：
     *       $list = M('UserCoin')->order('id desc')->select();
     *         foreach ($list as $k => $v) {
     *             $list[$k]['username'] = M('User')->where(array('id' => $v['userid']))->getField('username');
     *         }
     * */

    /**
     * 获取所有公告  表content 全部查询
     */
    @PostMapping("/list")
    @ApiOperation(value = "用户财产列表")
    @NoNeedLogin
    public ResponseDTO<IPage<TwUserCoin>> listpage(@Valid @RequestBody TwUserVo twUserVo) {
        return ResponseDTO.ok(twUserCoinService.listpage(twUserVo));
    }

}

