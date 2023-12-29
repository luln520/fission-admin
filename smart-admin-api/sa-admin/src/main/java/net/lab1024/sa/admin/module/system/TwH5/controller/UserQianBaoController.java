package net.lab1024.sa.admin.module.system.TwH5.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户钱包
 */
@RestController
@RequestMapping("/api/h5/userqianbao")
public class UserQianBaoController {

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

    /**
     * 新增或者编辑用户钱包 表 UserQianbao   有id 编辑  无id 新增  对象直接存
     */

    /**
     * 删除用户钱包 表 UserQianbao   where id=？
     */

}

