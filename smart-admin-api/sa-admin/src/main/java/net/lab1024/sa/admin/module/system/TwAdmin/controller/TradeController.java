package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易中心
 */
@RestController
@RequestMapping("/api/admin/trade")
public class TradeController {
    /**
     * 合约订单 查询列表 并返回   表hyorder where status=1  order by id desc
     */

    /**
     * 合约订单 查询单个 并返回   表hyorder where id=?
     */

    /**
     * 合约设置 获取 并返回    表hysetting where id=1
     */

    /**
     * 合约设置 编辑    表hysetting where id=1  对象直接保存
     */

    /**
     * 平仓 查询列表 并返回   表hyorder where status=2  order by id desc
     */

    /**
     * 限价委托记录 查询列表 并返回   表bborder where ordertype=1  order by id desc
     */

    /**
     * 市价交易记录 查询列表 并返回   表bborder where ordertype=2  order by id desc
     */


    /**
     * 币币交易设置 获取 并返回    表bbsetting where id=1
     */

    /**
     * 币币交易设置 编辑    表bbsetting where id=1  对象直接保存
     */


    /**
     * 刷单设置 查询列表     表Market order by id desc
     */

    /**
     * 刷单设置 添加/修改     表Market 对象直接存   有id就改  无id就插入数据库
     */

    /**
     * 清理刷单（部分和全部） 查询列表     表Market 表 Trade  type=1（全部） type=2 （部分） 接收 id
     *
     * 参考代码和逻辑：
     *          $market = M('Market')->where(array('id' => $id))->find();
     *         if ($type == 1) {
     *             $allclear = M('Trade')->where(array('market' => $market['name'], 'userid' => 0))->delete();
     *         }
     *         if ($type == 2) {
     *             if (!$market['sdhigh'] or !$market['sdlow']) {
     *                 $this->error('该市场未设置刷单最高价或最低价,无法部分清理');
     *             }
     *             $map['market'] = $market['name'];
     *             $map['userid'] = 0;
     *             $map['price'] = array('notbetween', array($market['sdhigh'], $market['sdlow'])); （ 不在这个范围的）
     *             $allclear = M('Trade')->where($map)->delete();
     *         }
     */


    /**
     * 刷单设置 禁用启用     表Market  update set  status 0或者1 where id=?
     */

    /**
     * 刷单设置 删除     表Market id=?
     */

    /**
     * 合约单控盈亏 设置     表hyorder  update  set kongyk=?    where  id=?
     */
    /**
     * 体验合约订单 查询列表 并返回   表tyhyorder where status=1  order by id desc
     */
    /**
     * 合约体验订单 查询单个 并返回   表tyhyorder where id=?
     */
    /**
     * 体验合约单控盈亏 设置     表tyhyorder  update  set kongyk=?    where  id=?
     */


}

