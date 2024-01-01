package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwUserVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 代理管理
 */
@RestController
@RequestMapping("/api/admin/agent")
public class TwAgentController {


    @Autowired
    private TwUserService twUserService;
    /**
     * 查询代理列表  表 User where is_agent=1 order by id desc
     * 然后遍历得到的list
     * 查询 一二三级代理的人数
     * 参考代码：
     *  public function agent()
     *     {
     *         $where['is_agent'] = 1;
     *         $list = M('User')->where($where)->order('id desc')->select();
     *         foreach ($list as $k => $v) {
     *             $uid = $v['id'];
     *             $one = M('User')->where(array('invit_1' => $uid))->count();
     *             if ($one <= 0) {
     *                 $one = 0;
     *             }
     *             $two = M('User')->where(array('invit_2' => $uid))->count();
     *             if ($two <= 0) {
     *                 $two = 0;
     *             }
     *             $three = M('User')->where(array('invit_3' => $uid))->count();
     *             if ($three <= 0) {
     *                 $three = 0;
     *             }
     *
     *             $all = $one + $two + $three;
     *             if ($all <= 0) {
     *                 $all = 0;
     *             }
     *             $list[$k]['all'] = $all;
     *             $list[$k]['one'] = $one;
     *             $list[$k]['two'] = $two;
     *             $list[$k]['three'] = $three;
     *         }
     *     }
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

