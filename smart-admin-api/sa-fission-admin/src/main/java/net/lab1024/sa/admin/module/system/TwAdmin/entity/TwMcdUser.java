package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tw_mcd_user")
@ApiModel(value="跟单员属性表", description="")
public class TwMcdUser implements Serializable {

    /**
    * ID
    */
    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("会员ID")
    private Integer uid;

    @ApiModelProperty("30天盈利率")
    private BigDecimal monthProfitRate;

    @ApiModelProperty("30天盈利")
    private BigDecimal monthProfit;

    @ApiModelProperty("带单天数")
    private Integer days;

    @ApiModelProperty("收益")
    private BigDecimal profit;

    @ApiModelProperty("资金规模")
    private BigDecimal amount;

    @ApiModelProperty("最小投资")
    private BigDecimal minInvest;

    @ApiModelProperty("0:申请,1:通过，2:驳回")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @TableField(exist = false)
    private String usercode;
    @TableField(exist = false)
    private String username;

    @ApiModelProperty("跟单总人数")
    private Integer peopleCount;
}
