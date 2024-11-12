package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tw_receipt")
@ApiModel(value="票据", description="")
public class TwReceipt {

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户ID")
    private Integer uid;

    @ApiModelProperty("transaction")
    private String tx;

    @ApiModelProperty("转出地址")
    private String fromAddress;

    @ApiModelProperty("转入地址")
    private String toAddress;

    @ApiModelProperty("链")
    private Integer chainId;

    @ApiModelProperty("gas消耗")
    private BigDecimal fee;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("caused")
    private String caused;

    @ApiModelProperty("bizStatus")
    private Integer bizStatus;

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @ApiModelProperty("用户名")
    private String userName;
}
