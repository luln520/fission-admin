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
@TableName("tw_address_detail")
@ApiModel(value="钱包交易详细", description="")
public class TwAddressDetail {

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("地址ID")
    private Integer addressId;

    @ApiModelProperty("区块号")
    private Integer blockNumber;

    @ApiModelProperty("tx")
    private String tx;

    @ApiModelProperty("发送方")
    private String fromAddress;

    @ApiModelProperty("接收方")
    private String toAddress;

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;


}
