package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 合约订单表
* @TableName tw_hyorder
*/
@Data
@TableName("tw_hyorder")
@ApiModel(value="合约订单表", description="")
public class TwHyorder implements Serializable {

    /**
    * ID
    */
    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
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
    * 结算时间
    */
    @ApiModelProperty("结算时间")
    private Date selltime;
    /**
    * 结算时间戳
    */
    @ApiModelProperty("结算时间戳")
    private Integer intselltime;
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

    @ApiModelProperty("计划时间")
    private Date plantime;

    @ApiModelProperty("计划时间戳")
    private int intplantime;
    /**
    * 盈亏金额
    */
    @ApiModelProperty("盈亏金额")
    private BigDecimal ploss;
    /**
    * 结算分钟数
    */
    @ApiModelProperty("结算分钟数")
    private String time;
    /**
    * 控制盈亏1盈利2亏损0未指定
    */
    @ApiModelProperty("控制盈亏1盈利2亏损0未指定")
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

    @TableField(exist = false)
    @ApiModelProperty("剩余秒数")
    private int timeResidue;

    @ApiModelProperty("团队路径")
    private String path;

    @ApiModelProperty("部门id")
    private Integer department;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty(value = "用户code")
    private String userCode;

    @ApiModelProperty("公司id")
    private Integer companyId;

    @ApiModelProperty("订单类型 （1.用户订单,2模拟订单）")
    private Integer orderType;
}
