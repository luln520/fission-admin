package net.lab1024.sa.admin.module.system.TwAdmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHysetting;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwHyorderVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHyorderService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwHysettingService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 交易中心
 */
@RestController
@RequestMapping("/api/admin/trade")
@Api(tags = {AdminSwaggerTagConst.System.TW_TRADE})
public class TwTradeController {

    @Autowired
    private TwHyorderService twHyorderService;

    @Autowired
    private TwHysettingService twHysettingService;

    /**
     * 合约,平仓订单 查询列表 并返回   表hyorder where status=1  order by id desc
     */
    @PostMapping("/hyorderlist")
    @ApiOperation(value = "合约,平仓订单 查询列表")
    @ResponseBody
    public ResponseDTO<IPage<TwHyorder>> hyorderlist(@Valid @RequestBody TwHyorderVo twHyorderVo, HttpServletRequest request) {
        return ResponseDTO.ok(twHyorderService.listpage(twHyorderVo,request));
    }

    /**
     * 合约订单 查询单个 并返回   表hyorder where id=?
     */
    @GetMapping("/hyorderId")
    @ResponseBody
    @ApiOperation(value = "合约,平仓订单 查询单个")
    public ResponseDTO hyorderId(@RequestParam int id) {
        return ResponseDTO.ok(twHyorderService.hyorderId(id));
    }

    /**
     * 合约设置 获取 并返回    表hysetting where id=1
     */
    @GetMapping("/hysettingId")
    @ResponseBody
    @ApiOperation(value = "合约设置")
    public ResponseDTO hysettingId(@RequestParam int companyId) {
        return ResponseDTO.ok(twHysettingService.hysettingId(companyId));
    }

    /**
     * 合约设置 编辑    表hysetting where id=1  对象直接保存
     */
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "合约设置 编辑")
    public ResponseDTO edit(@RequestBody TwHysetting twHysetting) {
        return ResponseDTO.ok(twHysettingService.edit(twHysetting));
    }

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
    @GetMapping("/editKongyK")
    @ResponseBody
    @ApiOperation(value = "合约单控盈亏 设置")
    public ResponseDTO editKongyK(@RequestParam Integer kongyk,@RequestParam List<Integer> ids) {
        return ResponseDTO.ok(twHyorderService.editKongyK(kongyk,ids));
    }



}

