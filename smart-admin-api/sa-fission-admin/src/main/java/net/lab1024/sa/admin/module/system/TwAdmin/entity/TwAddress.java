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
@TableName("tw_address")
@ApiModel(value="自动分配的钱包地址", description="")
public class TwAddress {

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户ID")
    private Integer uid;

    @ApiModelProperty("链标识")
    private Integer chainId;

    @ApiModelProperty("主地址ID")
    private Integer coinId;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("私钥")
    private String privateKey;

    @ApiModelProperty("公钥")
    private String publicKey;

    @ApiModelProperty("货币标识")
    private String currency;

    @ApiModelProperty("余额")
    private BigDecimal balance;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("区块号")
    private Integer blockNumber;

    @ApiModelProperty("用户名")
    private String userName;

}
