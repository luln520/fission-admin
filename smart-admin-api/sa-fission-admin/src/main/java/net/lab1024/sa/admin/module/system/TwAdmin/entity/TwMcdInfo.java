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
@TableName("tw_mcd_info")
@ApiModel(value="跟单信息表", description="")
public class TwMcdInfo implements Serializable {

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

    @ApiModelProperty("跟单员ID")
    private Integer followUid;

    @ApiModelProperty("投入比例")
    private BigDecimal investProp;

    @ApiModelProperty("状态：0取消跟单、1加入跟单")
    private Integer status;

    @ApiModelProperty("收益")
    private BigDecimal profit;

    @ApiModelProperty("投资金额")
    private BigDecimal amount;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
