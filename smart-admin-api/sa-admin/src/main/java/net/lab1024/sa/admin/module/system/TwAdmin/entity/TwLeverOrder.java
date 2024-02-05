package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 杠杆订单表
* @TableName tw_lever_order
*/
@Data
@TableName("tw_lever_order")
@ApiModel(value="杠杆订单表", description="")
public class TwLeverOrder implements Serializable {

    /**
    * ID
    */
    @NotNull(message="[ID]不能为空")
    @ApiModelProperty("ID")
    private Integer id;
    /**
    * 会员ID
    */
    @ApiModelProperty("会员ID")
    private Integer uid;
    /**
    * 会员账号
    */
    @ApiModelProperty("会员账号")
    private String username;
    /**
    * 投资金额
    */
    @ApiModelProperty("投资金额")
    private BigDecimal num;
    /**
    * 买后余额
    */
    @ApiModelProperty("买后余额")
    private BigDecimal buyOrblance;
    /**
    * 盈亏比例
    */
    @ApiModelProperty("盈亏比例")
    private BigDecimal hybl;
    /**
    * 合约涨跌1买涨2买跌
    */
    @ApiModelProperty("合约涨跌1买涨2买跌")
    private Integer hyzd;
    /**
    * 交易对
    */
    @ApiModelProperty("交易对")
    private String coinname;
    /**
    * 止损
    */
    @ApiModelProperty("止损")
    private Integer loss;
    /**
    * 止盈
    */
    @ApiModelProperty("止盈")
    private Integer win;
    /**
    * 倍数
    */
    @ApiModelProperty("倍数")
    private Integer fold;
    /**
    * 状态：1待结算2已结算3无效结算
    */
    @ApiModelProperty("状态：1待结算2已结算3无效结算")
    private Integer status;
    /**
    * 盈亏状态：1盈利2亏损
    */
    @ApiModelProperty("盈亏状态：1盈利2亏损")
    private Integer isWin;
    /**
    * 购买时间
    */
    @ApiModelProperty("购买时间")
    private Date buytime;

    /**
    * 建仓单价
    */
    @ApiModelProperty("建仓单价")
    private BigDecimal buyprice;
    /**
    * 结算单价
    */
    @ApiModelProperty("结算单价")
    private BigDecimal sellprice;
    /**
    * 盈亏金额
    */
    @ApiModelProperty("盈亏金额")
    private BigDecimal ploss;
    /**
    * 手续费
    */
    @ApiModelProperty("手续费")
    private BigDecimal premium;
    /**
    * 控制盈亏1盈利2亏损
    */
    @ApiModelProperty("控制盈亏1盈利2亏损0正常")
    private Integer kongyk;
    /**
    * 邀请码(上级)
    */
    @ApiModelProperty("邀请码(上级)")
    private String invit;
    /**
    * 0未通知，1已通知
    */
    @ApiModelProperty("0未通知，1已通知")
    private Integer tznum;
    /**
    * 团队路径
    */
    @ApiModelProperty("团队路径")
    private String path;
    /**
    * 部门id
    */
    @ApiModelProperty("部门id")
    private Integer department;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty(value = "用户code")
    private String userCode;

}
