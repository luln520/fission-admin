package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName tw_c2c_bank
*/
@TableName(value ="tw_c2c_bank")
@Data
@ApiModel(value="tw_c2c_bank银行表", description="")
public class TwC2cBank implements Serializable {

    @ApiModelProperty("id")
    private Integer id;
    /**
    * 订单编号
    */
    @ApiModelProperty("订单编号")
    private String orderNo;
    /**
    * 用户id
    */
    @ApiModelProperty("用户code")
    private String userCode;
    /**
    * 用户名
    */
    @ApiModelProperty("用户名")
    private String username;
    /**
    * 银行名称
    */
    @ApiModelProperty("银行名称")
    private String bank;
    /**
    * 收款人姓名
    */
    @ApiModelProperty("收款人姓名")
    private String name;
    /**
    * 类型:1.充值.2.提现
    */
    @ApiModelProperty("类型:1.充值.2.提现")
    private Integer type;
    /**
    * 银行卡号
    */
    @ApiModelProperty("银行卡号")
    private String bankNo;
    /**
    * 银行代码
    */
    @ApiModelProperty("银行代码")
    private String bankCode;
    /**
    * 银行地址
    */
    @ApiModelProperty("银行地址")
    private String bankAddress;
    /**
    * 路由号码
    */
    @ApiModelProperty("路由号码")
    private String routeCode;
    /**
    * SWIFT代码
    */
    @ApiModelProperty("SWIFT代码")
    private String swift;
    /**
    * 分行号码
    */
    @ApiModelProperty("分行号码")
    private String branchCode;
    /**
    * 备注
    */
    @ApiModelProperty("备注")
    private String remark;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;

    @TableField(exist = false)
    @ApiModelProperty("语言")
    private String language;

    @TableField(exist = false)
    @ApiModelProperty("支付方式")
    private int bankType;

    @TableField(exist = false)
    @ApiModelProperty("国家id")
    private int countryId;

    @TableField(exist = false)
    @ApiModelProperty("提现金额")
    private BigDecimal num;

}
