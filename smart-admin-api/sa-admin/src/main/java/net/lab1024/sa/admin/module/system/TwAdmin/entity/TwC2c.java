package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty("转账人姓名")
    private String transferName;

    @ApiModelProperty("货币名称")
    private String currenyName;

    @ApiModelProperty("货币")
    private String curreny;
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
    private BigDecimal dzNum;
    /**
     * 交易截图
     */
    @ApiModelProperty("交易截图")
    private String img;
    /**
     * 1.锁定 2.未锁定
     */
    @ApiModelProperty("1.锁定 2.未锁定")
    private Integer isLock;
    /**
     * 类型:1.充值.2.提现
     */
    @ApiModelProperty("类型:1.充值.2.提现")
    private Integer type;
    /**
     * 状态 1.待支付 2.支付成功 3.支付失败
     */
    @ApiModelProperty("状态 1.待支付 2.审核通过 3.审核拒绝 4.分配卡号 5.取消订单")
    private Integer status;

    @ApiModelProperty("银行卡类型：1.银行卡 2.革命 3.wise")
    private Integer bankType;
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

    @ApiModelProperty("团队路径")
    private String path;

    @ApiModelProperty("部门id")
    private Integer department;

    @ApiModelProperty(value = "用户code")
    private String userCode;

    @ApiModelProperty("公司id")
    private Integer companyId;

    @TableField(exist = false)
    @ApiModelProperty("银行卡详情")
    private TwC2cBank twC2cBank;
}