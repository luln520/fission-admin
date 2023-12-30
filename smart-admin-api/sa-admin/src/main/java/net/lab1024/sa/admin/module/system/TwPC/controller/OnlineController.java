package net.lab1024.sa.admin.module.system.TwPC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客服聊天
 */
@RestController
@RequestMapping("/api/pc/online")
public class OnlineController {


    /**
     * 获取聊天列表
     * 表 online
     * 参数： id （用户 id）
     * select online  where  uid=？ order by id asc
     * */

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

}

