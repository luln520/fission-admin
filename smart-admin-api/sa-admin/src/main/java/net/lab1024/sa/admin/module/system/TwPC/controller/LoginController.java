package net.lab1024.sa.admin.module.system.TwPC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登陆/注册
 */
@RestController
@RequestMapping("/api/pc/userLoginOrRegister")
public class LoginController {

    /**
     * 用户登陆 表 user，user_log
     * 参数： username  password
     * 大致逻辑：
     *      1.通过用户名查询用户，先判断该用户是否存在
     *      2.判断用户密码是否正确 password MD5加密对比
     *      3.判断账号是否正常    不正常 status!=1
     *      4.增加该用户增加登陆次数
     *      5.新增该用户登陆记录 （表 user_log）
     *      6.生成token 并返回
     *
     * 参考代码：
     *      $user = M('User')->where(array('username' => $username))->find(); （逻辑 1）
     *         if (empty($user)) {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('用户不存在')]);
     *         }
     *         if (md5($username) != $user['password']) {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('登录密码错误')]);  （逻辑 2）
     *         }
     *
     *         if (isset($user['status']) && $user['status'] != 1) { （逻辑 3）
     *             $this->ajaxReturn(['code' => 0, 'info' => L('你的账号已冻结请联系管理员')]);
     *         }
     *         //增加登陆次数
     *         $incre = M("user")->where(array('id' => $user['id']))->setInc('logins', 1); （逻辑 4）
     *         //新增登陆记录
     *         $data['userid'] = $user['id'];
     *         $data['type'] = L('登录');
     *         $data['remark'] = L('邮箱登录');
     *         $data['addtime'] = time();
     *         $data['addip'] = get_client_ip();
     *         $data['addr'] = get_city_ip();
     *         $data['status'] = 1;
     *         $dre = M("user_log")->add($data); （逻辑 5）
     *         if ($incre && $dre) {
     *             $lgdata['lgtime'] = date("Y-m-d H:i:s");
     *             $lgdata['loginip'] = get_client_ip();
     *             $lgdata['loginaddr'] = get_city_ip();
     *             $lgdata['logintime'] = date("Y-m-d H:i:s"); （逻辑 1）
     *             token=?  生成并返回token 存入redis
     *             $this->ajaxReturn(['code' => 1, 'info' => L('登录成功')]);
     *         } else {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('登录失败')]);
     *         }
     * */

    /**
     * 用户注册 表 user，config，tw_user_coin
     * 需要开启事务
     * 参数： username  password regcode(验证码) invit（邀请码） type（注册类型 1 手机  2 邮箱）
     * 大致逻辑：
     *      1.通过用户名查询用户，先判断该用户是否存在
     *      2.判断验证码
     *      3.判断邀请码
     *      4.通过邀请码查找并填充三个上级信息和团队路径
     *      5.生成自己的邀请码并填充 （随机 qwertyuiopasdfghjklzxcvbnmABCDEFGHIJKLMNPQRSTUVWXYZ 5位置）
     *      6.查询 系统配置 config （体验额度 tymoney） config where id =1
     *      7.写入 用户表 user  字段看注释
     *      8.创建用户数字资产档案
     *      9.返回成功 （前面任何失败 都 回滚事务 返回失败）
     *
     * 参考代码：
     *         $area_code = '';
     *         $checkus = M('User')->where(array('username' => $username))->find(); (逻辑 1)
     *         if (!empty($checkus)) {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('用户名已存在')]);
     *         }
     *         $secode = session('regcode');
     *         if ($secode != $regcode) {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('验证码错误')]);  (逻辑 2)
     *         }
     *         if ($lpwd == '') {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('请输入密码')]);
     *         }
     *         if ($invit == '') {
     *             $this->ajaxReturn(['code' => 0, 'info' => L('请输入邀请码')]); (逻辑 3)
     *         }
     *
     *         $config = M("config")->where(array('id' => 1))->field("tymoney")->find();  (逻辑 5)
     *         if ($invit != 0 || $invit != '') {
     *             $inv_user = M('User')->where(array('invit' => $invit))->field("id,username,invit_1,invit_2,path")->find(); (逻辑 4 括号内都是)
     *             if (empty($inv_user)) {
     *                 $this->ajaxReturn(['code' => 0, 'info' => L('推荐人不存在')]);
     *             }
     *             $invit_1 = $inv_user['id'];
     *             $invit_2 = $inv_user['invit_1'];
     *             $invit_3 = $inv_user['invit_2'];
     *             if (!empty($inv_user['path'])) {
     *                 $add['path'] = $path = $inv_user['path'] . ',' . $inv_user['id'];
     *             } else {
     *                 $add['path'] = $path = $inv_user['id'];
     *             }
     *         } else {
     *             $invit_1 = 0;
     *             $invit_2 = 0;
     *             $invit_3 = 0;
     *             $path = '';
     *         }
     *
     *         for (; true;) { (这个就是生成验证码)
     *             $myinvit = tradenoa();
     *             if (!M('User')->where(array('invit' => $myinvit))->find()) {
     *                 break;
     *             }
     *         }
     *
     *         $mo = M();
     *         $mo->execute('set autocommit=0'); (开启事务)
     *         $mo->execute('lock tables tw_user write , tw_user_coin write ');
     *         $rs = array();
     *         $rs[] = $mo->table('tw_user')->add(  (逻辑 7)
     *             array(
     *                 'username' => $username,
     *                 'password' => md5($lpwd),
     *                 'money' => $config['tymoney'],
     *                 'invit' => $myinvit,
     *                 'invit_1' => $invit_1,
     *                 'invit_2' => $invit_2,
     *                 'invit_3' => $invit_3,
     *                 'type' => $type,
     *                 'area_code' => $area_code,
     *                 'path' => $path,
     *                 'addip' => get_client_ip(),
     *                 'addr' => get_city_ip(),
     *                 'addtime' => time(),
     *                 'status' => 1,
     *                 'txstate' => 1,
     *                 'rzstatus' => 2,
     *             ));
     *
     *         $user_coin = array('userid' => $rs[0], 'usdt' => $config['tymoney']);
     *         // 创建用户数字资产档案
     *         $rs[] = $mo->table('tw_user_coin')->add($user_coin); (逻辑 8)
     *         if (check_arr($rs)) {
     *             $mo->execute('commit');
     *             $mo->execute('unlock tables');
     *             session('regcode', null); //初始化动态验证码
     *             $this->ajaxReturn(['code' => 1, 'info' => L('注册成功')]); (逻辑 9)
     *         } else {
     *             $mo->execute('rollback');
     *             $this->ajaxReturn(['code' => 0, 'info' => L('注册失败')]);
     *         }
     * */


    /**
     * 用户发送验证码 表 user
     * 参数： username（可能是手机号 也可能是邮箱） code(验证码)  password  type（注册类型 1 手机  2 邮箱）
     * 大致逻辑：
     *      1.验证用户是否存在
     *      2.验证 code 是否存在且和属于该用户的
     *      3.都通过  修改对应用户
     * */


    /**
     * 用户修改密码
     * 参数： username（可能是手机号 也可能是邮箱）   type（注册类型 1 手机  2 邮箱）
     * 大致逻辑：
     *      1.随机生产 10000-99999内的数字
     *      2.判断type 调取 发送手机 或者 发送邮箱
     * */

}

