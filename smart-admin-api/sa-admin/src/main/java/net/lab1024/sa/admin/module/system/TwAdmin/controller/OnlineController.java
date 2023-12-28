package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在线客服
 */
@RestController
@RequestMapping("/api/admin/online")
public class OnlineController {

    /**
     * 获取所有用户的聊天列表 表online
     *  字段 id,uid,username,count(username)as nor,addtime   where state=0 group by username order by addtime desc
     */

    /**
     * 获取单个用户的对话信息  表online
     * 接收： 用户 id
     *  where uid=？and type =2  order by id asc
     */

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
     *         $data['state'] = 1;
     *         $result = M("online")->add($data);（添加客服回复内容记录）
     *         if ($result) {
     *             M("online")->where(array('uid' => uid))->save(array('state' => 1));（修改被回复人信息的状态）
     *             $this->success("回复成功");
     *         } else {
     *             $this->error("回复失败");
     *         }
     */

}

