package net.lab1024.sa.admin.module.system.TwAdmin.entity;

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
* 贷款订单表
* @TableName tw_pledge_order
*/
@Data
@TableName("tw_pledge_order")
@ApiModel(value="贷款订单表", description="")
public class TwPledgeOrder implements Serializable {


    private Integer id;
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
    * 用户code
    */
    @ApiModelProperty("用户code")
    private String userCode;
    /**
    * 订单编号
    */
    @ApiModelProperty("订单编号")
    private String orderNo;
    /**
    * 贷款类型
    */
    @ApiModelProperty("贷款类型")
    private Integer type;
    /**
    * 申请额度
    */
    @ApiModelProperty("申请额度")
    private BigDecimal num;
    /**
    * 质押额度
    */
    @ApiModelProperty("质押额度")
    private BigDecimal pledgeNum;
    /**
    * 余额百分比（单位%）
    */
    @ApiModelProperty("余额百分比（单位%）")
    private Integer ratio;
    /**
    * 质押百分比（单位%）
    */
    @ApiModelProperty("质押百分比（单位%）")
    private Integer pledgeRatio;
    /**
    * 贷款手续费
    */
    @ApiModelProperty("贷款手续费")
    private Integer premium;
    /**
    * 邀请码
    */
    @ApiModelProperty("邀请码")
    private String code;
    /**
    * 邀请人验证码
    */
    @ApiModelProperty("邀请人验证码")
    private Integer invite;
    /**
    * 状态（1 未申请，2 申请中，3.质押成功，4，质押失败）
    */
    @ApiModelProperty("状态（1 未申请，2 申请中，3.质押成功，4，质押失败）")
    private Integer status;
    /**
    * 公司id
    */
    @ApiModelProperty("公司id")
    private Integer companyId;
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
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @TableField(exist = false)
    @ApiModelProperty("语言")
    private String language;

}
