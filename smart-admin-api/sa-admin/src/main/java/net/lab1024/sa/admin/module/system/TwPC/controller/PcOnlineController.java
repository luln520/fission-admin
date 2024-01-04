package net.lab1024.sa.admin.module.system.TwPC.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwOnline;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwOnlineService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客服聊天
 */
@RestController
@RequestMapping("/api/pc/online")
@Api(tags = {AdminSwaggerTagConst.PC.PC_ONLINE})
public class PcOnlineController {

    @Autowired
    private TwOnlineService twOnlineService;

    /**
     * 获取聊天列表
     * 表 online
     * 参数： id （用户 id）
     * select online  where  uid=？ order by id asc
     * */
    @GetMapping("/list")
    @ResponseBody
    @NoNeedLogin
    @ApiOperation(value = "获取聊天列表")
    public ResponseDTO<List<TwOnline>> lists(@RequestParam int uid) {
        return ResponseDTO.ok(twOnlineService.lists(uid));
    }
    /**
     * 发送聊天
     * 表 online，user
     * 参数： id （用户 id）  content
     * 大致逻辑：
     *      1.查询 user  where id=?
     *      2.拼接参数 并写入 表online
     * 逻辑代码：
     *         $uinfo = M("user")->where(array('id' => $id))->field("id,username")->find();
     *         $data['uid'] = $id;
     *         $data['username'] = $uinfo['username'];
     *         $data['type'] = 2;
     *         $data['content'] = $txt;
     *         $data['addtime'] = date("Y-m-d H:i:s", time());
     *         $result = M("online")->add($data);
     * */
    @GetMapping("/sendMsg")
    @ResponseBody
    @NoNeedLogin
    @ApiOperation(value = "发送聊天")
    public ResponseDTO sendMsg(@RequestParam int uid,@RequestParam String content) {
        return ResponseDTO.ok(twOnlineService.sendMsg(uid,content));
    }

}

