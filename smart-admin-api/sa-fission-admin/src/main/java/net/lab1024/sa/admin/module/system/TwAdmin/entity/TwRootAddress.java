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
@TableName("tw_root_address")
@ApiModel(value="根地址-注意隐私泄露", description="")
public class TwRootAddress {

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("链名称")
    private String name;

    @ApiModelProperty("链标识")
    private Integer chainId;

    @ApiModelProperty("种子")
    private String seed;

    @ApiModelProperty("助记词")
    private String mnemonic;

    @ApiModelProperty("私钥")
    private String privateKey;

    @ApiModelProperty("公钥")
    private String publicKey;

    @ApiModelProperty("生成步骤")
    private Integer step;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
