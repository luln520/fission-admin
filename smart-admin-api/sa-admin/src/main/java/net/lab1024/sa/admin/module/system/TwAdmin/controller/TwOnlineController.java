package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwOnline;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.OnlineVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwOnlineService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.PageParam;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 在线客服
 */
@RestController
@RequestMapping("/api/admin/online")
@Api(tags = {AdminSwaggerTagConst.System.TW_ONLINE})
public class TwOnlineController {


    @Autowired
    private TwOnlineService twOnlineService;

    /**
     * 获取所有用户的聊天列表 表online
     *  字段 id,uid,username,count(username)as nor,addtime   where state=0 group by username order by addtime desc
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取所有用户的聊天列表")
    @ResponseBody
    public ResponseDTO<IPage<TwOnline>> list(@Valid @RequestBody OnlineVo onlineVo, HttpServletRequest request) {
        return ResponseDTO.ok(twOnlineService.listpage(onlineVo,request));
    }


    /**
     * 获取单个用户的对话信息  表online
     * 接收： 用户 id
     *  where uid=？and type =2  order by id asc
     */
    @GetMapping("/getId")
    @ResponseBody
    @ApiOperation(value = "获取单个用户的对话信息")
    public ResponseDTO<List<TwOnline>> getId(@RequestParam int uid) {
        return ResponseDTO.ok(twOnlineService.getId(uid));
    }

    /**
     * 客服回复  表 online
     * 接收：uid (被回复用户的 id)  content（回复内容）
     *
     * 逻辑代码如下：
     *         $info = M("online")->where(array('id' => $uid))->find();（通过 uid 查询 被回复的信息，最后一条）
     *         $uid = $info['uid'];
     *         $data['uid'] = $uid;
     *         $data['username'] = $info['username'];
     *         $data['content'] = $content;（写入客服回复的内容）
     *         $data['type'] = 1;
     *         $data['addtime'] = date("Y-m-d H:i:s", time());
     *         $data['state'] = 2;
     *         $result = M("online")->add($data);（添加客服回复内容记录）
     *         if ($result) {
     *             M("online")->where(array('uid' => uid))->save(array('state' => 1));（修改被回复人信息的状态）
     *             $this->success("回复成功");
     *         } else {
     *             $this->error("回复失败");
     *         }
     */
    @GetMapping("/backOnline")
    @ResponseBody
    @ApiOperation(value = "客服回复")
    public ResponseDTO backOnline(@RequestParam int uid, @RequestParam String content) {
        return twOnlineService.backOnline(uid,content);
    }


    @GetMapping("/del")
    @ResponseBody
    @ApiOperation(value = "客服删除")
    public ResponseDTO del(@RequestParam int id) {
        return twOnlineService.del(id);
    }


}

