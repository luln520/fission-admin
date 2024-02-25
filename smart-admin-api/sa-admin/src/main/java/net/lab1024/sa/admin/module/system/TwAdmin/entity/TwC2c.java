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
 * 
 * @TableName tw_c2c
 */
@TableName(value ="tw_c2c")
@Data
@ApiModel(value="tw_c2c表", description="")
public class TwC2c implements Serializable {

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
    @ApiModelProperty("用户id")
    private Integer uid;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;
    /**
     * 国家
     */
    @ApiModelProperty("国家")
    private String country;
    /**
     * 上级代理人
     */
    @ApiModelProperty("上级代理人")
    private String agent;
    /**
     * 充值金额
     */
    @ApiModelProperty("充值金额")
    private BigDecimal czNum;
    /**
     * 到账金额
     */
    @ApiModelProperty("到账金额")
    private Integer dzNum;
    /**
     * 交易截图
     */
    @ApiModelProperty("交易截图")
    private String img;
    /**
     * 1.锁定 2.未锁定
     */
    @ApiModelProperty("1.锁定 2.未锁定")
    private Integer lock;
    /**
     * 类型:1.充值.2.提现
     */
    @ApiModelProperty("类型:1.充值.2.提现")
    private Integer type;
    /**
     * 状态 1.待支付 2.支付成功 3.支付失败
     */
    @ApiModelProperty("状态 1.待支付 2.支付成功 3.支付失败")
    private Integer status;
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

}