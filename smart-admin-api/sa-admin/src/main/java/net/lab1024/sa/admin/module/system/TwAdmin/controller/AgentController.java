package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAdminLog;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwBillVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 代理管理
 */
@RestController
@RequestMapping("/api/admin/adminlog")
public class AgentController {


    @Autowired
    private TwUserService twUserService;
    /**
     * 查询代理列表  表 User where is_agent=1 order by id desc
     * 然后遍历得到的list
     * 查询 一二三级代理的人数
     * 参考代码：
     *  $where['is_agent'] = 1;
     *         $list = M('User')->where($where)->order('id desc')->select(); （代理列表）
     * */
    @PostMapping("/list")
    @ApiOperation(value = "管理员操作日志列表")
    @NoNeedLogin
    public ResponseDTO<IPage<TwUser>> listpage(@Valid @RequestBody TwUserVo twUserVo) {
        return ResponseDTO.ok(twUserService.listpage(twUserVo));
    }



    /**
     * 设置为代理或者取消代理   表 User  update set is_agent=？ where id=?
     * 传入：is_agent  id
     */

    @GetMapping("/setAgent")
    @ApiOperation(value = "设置为代理或者取消代理")
    @NoNeedLogin
    public ResponseDTO setAgent(@RequestParam int isAgent,@RequestParam int id) {
        return ResponseDTO.ok(twUserService.setAgent(isAgent,id));
    }

}

