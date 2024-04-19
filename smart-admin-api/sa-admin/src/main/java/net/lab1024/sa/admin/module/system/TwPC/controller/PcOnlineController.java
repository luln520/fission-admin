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
    @ApiOperation(value = "获取聊天列表")
    @NoNeedLogin
    public ResponseDTO<List<TwOnline>> lists(@RequestParam int uid,@RequestParam String  uuid,@RequestParam int companyId) {
        return ResponseDTO.ok(twOnlineService.lists(uid,uuid,companyId));
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
    @ApiOperation(value = "发送聊天")
    @NoNeedLogin
    public ResponseDTO sendMsg(@RequestParam int uid,@RequestParam String content,@RequestParam String uuid,@RequestParam int type,@RequestParam int companyId) {
        return ResponseDTO.ok(twOnlineService.sendMsg(uid,content,uuid,type,companyId));
    }

    @GetMapping("/upStatus")
    @ResponseBody
    @ApiOperation(value = "用户查看修改状态")
    @NoNeedLogin
    public ResponseDTO upStatus(@RequestParam int uid,@RequestParam int companyId) {
        return ResponseDTO.ok(twOnlineService.upStatus(uid,companyId));
    }

    @GetMapping("/userMsg")
    @ResponseBody
    @ApiOperation(value = "用户未读通知消息")
    @NoNeedLogin
    public ResponseDTO userMsg(@RequestParam int uid,@RequestParam int companyId) {
        return ResponseDTO.ok(twOnlineService.userMsg(uid,companyId));
    }

    @GetMapping("/upUuidStatus")
    @ResponseBody
    @ApiOperation(value = "用户查看修改状态")
    @NoNeedLogin
    public ResponseDTO upUuidStatus(@RequestParam String uuid,@RequestParam int companyId) {
        return ResponseDTO.ok(twOnlineService.upUuidStatus(uuid,companyId));
    }

    @GetMapping("/userUuidMsg")
    @ResponseBody
    @ApiOperation(value = "用户未读通知消息")
    @NoNeedLogin
    public ResponseDTO userUuidMsg(@RequestParam String uuid,@RequestParam int companyId) {
        return ResponseDTO.ok(twOnlineService.userUuidMsg(uuid,companyId));
    }

}

