package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 杠杆倍数
* @TableName tw_leverage
*/
@Data
@TableName("tw_leverage")
@ApiModel(value="杠杆倍数", description="")
public class TwLeverage implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer id;
    /**
    * 币种
    */
    @ApiModelProperty("币种")
    private String symbol;
    /**
    * 数值
    */
    @ApiModelProperty("数值")
    private Integer num;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
    * 编辑时间
    */
    @ApiModelProperty("编辑时间")
    private Date updateTime;

}
