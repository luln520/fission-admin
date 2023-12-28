package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员登录日志
 */
@RestController
@RequestMapping("/api/admin/adminlog")
public class UserLogController {

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

}

