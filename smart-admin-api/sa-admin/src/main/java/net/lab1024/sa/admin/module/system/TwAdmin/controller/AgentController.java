package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代理管理
 */
@RestController
@RequestMapping("/api/admin/adminlog")
public class AgentController {

    /**
     * 查询代理列表  表 User where is_agent=1 order by id desc
     * 然后遍历得到的list
     * 查询 一二三级代理的人数
     * 参考代码：
     *  $where['is_agent'] = 1;
     *         $list = M('User')->where($where)->order('id desc')->select(); （代理列表）
     *         foreach ($list as $k => $v) {
     *             $uid = $v['id'];（ 当前对象的user 的 id）
     *             $one = M('User')->where(array('invit_1' => $uid))->count(); （一级代理人数 ）
     *             if ($one <= 0) {
     *                 $one = 0;
     *             }
     *             $two = M('User')->where(array('invit_2' => $uid))->count();（二级代理人数 ）
     *             if ($two <= 0) {
     *                 $two = 0;
     *             }
     *             $three = M('User')->where(array('invit_3' => $uid))->count();（三级代理人数 ）
     *             if ($three <= 0) {
     *                 $three = 0;
     *             }
     *             $all = $one + $two + $three;（代理人数总数）
     *             if ($all <= 0) {
     *                 $all = 0;
     *             }
     *             $list[$k]['all'] = $all;（放入对象）
     *             $list[$k]['one'] = $one;（放入对象）
     *             $list[$k]['two'] = $two;（放入对象）
     *             $list[$k]['three'] = $three;（放入对象）
     *         }
     * */

    /**
     * 设置为代理或者取消代理   表 User  update set is_agent=？ where id=?
     * 传入：is_agent  id
     */
}

