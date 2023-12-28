package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户财产
 */
@RestController
@RequestMapping("/api/admin/usercoin")
public class UserCoinController {

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

}

