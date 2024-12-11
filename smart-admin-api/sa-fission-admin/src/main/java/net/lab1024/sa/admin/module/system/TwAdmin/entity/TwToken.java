package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tw_token")
@ApiModel(value="token", description="")
public class TwToken {

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("链标识")
    private Integer chainId;

    @ApiModelProperty("name")
    private String name;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("精度")
    private Integer accuracy;

    @ApiModelProperty("总计")
    private Integer amount;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
