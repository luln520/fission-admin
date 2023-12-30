package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserQianbao;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserQianbaoService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户钱包
 */
@RestController
@RequestMapping("/api/admin/userqianbao")
public class UserQianBaoController {

    @Autowired
    private TwUserQianbaoService twUserQianbaoService;
    /**
     * 获取用户钱包列表 表 UserQianbao
     * order by id desc
     * 然后遍历得到的list
     *  查询 用户的名称
     *  参考代码：
     *       $list = M('UserQianbao')->order('id desc')->select();
     *         foreach ($list as $k => $v) {
     *             $list[$k]['username'] = M('User')->where(array('id' => $v['userid']))->getField('username');
     *         }
     * */
    @PostMapping("/list")
    @ApiOperation(value = "获取用户钱包列表")
    @NoNeedLogin
    public ResponseDTO<IPage<TwUserQianbao>> listpage(@Valid @RequestBody TwUserVo twUserVo) {
        return ResponseDTO.ok(twUserQianbaoService.listpage(twUserVo));
    }

    /**
     * 新增或者编辑用户钱包 表 UserQianbao   有id 编辑  无id 新增  对象直接存
     */
    @PostMapping("/addUpdate")
    @ApiOperation(value = "新增或者编辑用户钱包")
    @NoNeedLogin
    public ResponseDTO addUpdate(@Valid @RequestBody TwUserQianbao userQianBao) {
        return ResponseDTO.ok(twUserQianbaoService.addUpdate(userQianBao));
    }

    /**
     * 删除用户钱包 表 UserQianbao   where id=？
     */
    @GetMapping("/del")
    @ApiOperation(value = "删除用户钱包")
    @NoNeedLogin
    public ResponseDTO delete(@RequestParam int id) {
        return ResponseDTO.ok(twUserQianbaoService.del(id));
    }

}

