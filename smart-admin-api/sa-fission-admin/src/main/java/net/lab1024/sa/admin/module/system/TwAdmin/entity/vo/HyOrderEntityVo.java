package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class HyOrderEntityVo {
    /**
     * 会员账号
     */
    @Excel(name = "会员账号", width = 10)
    @ApiModelProperty("会员账号")
    private String username;
    /**
     * 投资金额
     */
    @Excel(name = "委托额度", width = 10)
    @ApiModelProperty("投资金额")
    private BigDecimal num;

    /**
     * 盈亏比例
     */
    @Excel(name = "盈亏比例", width = 10)
    @ApiModelProperty("盈亏比例")
    private BigDecimal hybl;
    /**
     * 合约涨跌1买涨2买跌
     */
    @Excel(name = "方向", width = 10)
    @ApiModelProperty("合约涨跌1买涨2买跌")
    private String hyzd;
    /**
     * 交易对
     */
    @Excel(name = "交易对", width = 10)
    @ApiModelProperty("交易对")
    private String coinname;
    /**
     * 状态：1待结算2已结算3无效结算
     */
    @Excel(name = "状态", width = 10)
    @ApiModelProperty("状态：1待结算2已结算3无效结算4订单取消")
    private String status;
    /**
     * 购买时间
     */
    @Excel(name = "建仓时间", width = 10, format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("购买时间")
    private Date buytime;
    /**
     * 建仓单价
     */
    @Excel(name = "建仓单价", width = 10)
    @ApiModelProperty("建仓单价")
    private BigDecimal buyprice;
    /**
     * 结算单价
     */
    @Excel(name = "平仓单价", width = 10)
    @ApiModelProperty("结算单价")
    private BigDecimal sellprice;

    @Excel(name = "计划时间", width = 10, format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("计划时间")
    private Date plantime;
    /**
     * 盈亏金额
     */
    @Excel(name = "亏盈额度", width = 10)
    @ApiModelProperty("盈亏金额")
    private BigDecimal ploss;
    /**
     * 结算分钟数
     */
    @Excel(name = "周期", width = 10)
    @ApiModelProperty("结算分钟数")
    private String time;
    /**
     * 控制盈亏1盈利2亏损0未指定
     */
    @Excel(name = "亏盈设置", width = 10)
    @ApiModelProperty("控制盈亏1盈利2亏损0未指定")
    private String kongyk;

    @Excel(name = "订单号", width = 10)
    @ApiModelProperty("订单编号")
    private String orderNo;

    @Excel(name = "用户ID", width = 10)
    @ApiModelProperty(value = "用户code")
    private String userCode;

}
