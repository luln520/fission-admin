package net.lab1024.sa.admin.module.system.TwPC.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 币种
 */
@RestController
@RequestMapping("/api/pc/qianbao")
public class QianBaoController {


    /**
     * 提币地址管理
     * 表 user_qianbao
     * 参数： id （用户 id）
     * select user_qianbao  where  userid=？
     * */

    /**
     * 删除提币地址
     * 表 user_qianbao
     * 参数： id （user_qianbao表的id）
     * delete user_qianbao  where  id=？
     * */

    /**
     * 添加提币地址
     * 表 user，coin，user_qianbao
     * 参数： id （用户 id） oid （币种 id） address 地址 remark 备注 czline（充值网络）
     * 大致逻辑：
     *          1.查询user   where id=？
     *          2.查询 coin where id=? oid
     *          3.拼接user_qianbao 需要的参数  并写入
     * 逻辑代码：
     *         $uinfo = M("user")->where(array('id' => $id))->field("id,username")->find();
     *         $cinfo = M("coin")->where(array('id' => $oid))->find();
     *         $data['userid'] = $id;
     *         $data['coinname'] = $uinfo['username'];
     *         $data['czline'] = $czline;
     *         $data['name'] = $cinfo['name'];
     *         $data['remark'] = $remark;
     *         $data['addr'] = $address;
     *         $data['sort'] = 1;
     *         $data['addtime'] = date("Y-m-d H:i:s", time());
     *         $data['status'] = 1;
     *         $result = M('user_qianbao')->add($data);
     *         if ($result) {
     *             $this->ajaxReturn(['code' => 1, 'msg' => L('添加成功')]);
     *         } else {
     *             $this->ajaxReturn(['code' => 0, 'msg' => L('添加失败')]);
     *         }
     * */


}

