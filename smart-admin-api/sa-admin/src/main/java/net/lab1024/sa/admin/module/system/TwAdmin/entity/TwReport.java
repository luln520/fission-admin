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
    private Date dayDate;
    /**
    * 注册人数
    */
    @ApiModelProperty("注册人数")
    private Integer registrant;
    /**
    * 活跃人数
    */
    @ApiModelProperty("活跃人数")
    private Integer active;
    /**
    * 下单人数
    */
    @ApiModelProperty("下单人数")
    private Integer order;
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
    /**
    * 充值总额
    */
    @ApiModelProperty("充值总额")
    private BigDecimal rechargeNum;
    /**
    * 提现人数
    */
    @ApiModelProperty("提现人数")
    private Integer payout;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer payoutTotal;
    /**
    * 实际提现总额
    */
    @ApiModelProperty("实际提现总额")
    private BigDecimal payoutNum;
    /**
    * 总客损 (充值总额-提现总额)
    */
    @ApiModelProperty("总客损 (充值总额-提现总额)")
    private BigDecimal customerLoss;
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
