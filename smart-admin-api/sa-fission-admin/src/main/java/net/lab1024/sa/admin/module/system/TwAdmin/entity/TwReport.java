package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import java.io.Serializable;

import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 报表
* @TableName tw_report
*/

@Data
@TableName("tw_report")
@ApiModel(value="报表", description="")
public class TwReport implements Serializable {


    private Integer id;
    /**
    * 日期
    */
    @ApiModelProperty("日期")
    private String dayDate;
    /**
    * 注册人数
    */
    @ApiModelProperty("日注册人数")
    private Integer registrant;

    @ApiModelProperty("总注册人数")
    private Integer registrantTotal;
    /**
    * 活跃人数
    */
    @ApiModelProperty("活跃人数")
    private Integer active;
    /**
    * 下单人数
    */
    @ApiModelProperty("下单人数")
    private Integer orders;
    /**
    * 下单人数
    */
    @ApiModelProperty("下单总人数")
    private Integer orderTotal;
    /**
    * 下单总额
    */
    @ApiModelProperty("下单总额")
    private BigDecimal orderNum;
    /**
    * 盈亏总额
    */
    @ApiModelProperty("盈亏总额")
    private BigDecimal profitNum;
    /**
    * 充值人数
    */
    @ApiModelProperty("充值人数")
    private Integer recharge;
    @ApiModelProperty("充值总人数")
    private Integer rechargeTotal;
    /**
    * 充值总额
    */
    @ApiModelProperty("日充值额")
    private BigDecimal rechargeNum;
    /**
    * 充值总额
    */
    @ApiModelProperty("充值总额")
    private BigDecimal rechargeSum;
    /**
    * 提现人数
    */
    @ApiModelProperty("日提现人数")
    private Integer payout;
    /**
    * 
    */
    @ApiModelProperty("总提现人数")
    private Integer payoutTotal;
    /**
    * 实际提现总额
    */
    @ApiModelProperty("日提现总额")
    private BigDecimal payoutNum;
    @ApiModelProperty("总提现总额")
    private BigDecimal payoutSum;
    /**
    * 总客损 (充值总额-提现总额)
    */

    @ApiModelProperty("今日客损 (今日充值-今日提现)")
    private BigDecimal dayCustomerLoss;

    @ApiModelProperty("总客损 (充值总额-提现总额)")
    private BigDecimal customerLoss;

    @ApiModelProperty("会员日余额")
    private BigDecimal dayAmount;
    /**
    * 会员总余额
    */
    @ApiModelProperty("会员总余额")
    private BigDecimal totalAmount;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer companyId;
    /**
    * 
    */
    @ApiModelProperty("")
    private Date createTime;
    /**
    * 
    */
    @ApiModelProperty("")
    private Date updateTime;


}
