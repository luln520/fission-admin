package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 杠杆止盈止损设置
* @TableName tw_lever_set
*/
@Data
@TableName("tw_lever_set")
@ApiModel(value="杠杆止盈止损设置", description="")
public class TwLeverSet implements Serializable {

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
    * 类型：1.止损 2.止盈
    */
    @ApiModelProperty("类型：1.止损 2.止盈")
    private Integer type;
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

    @ApiModelProperty("公司id")
    private Integer companyId;
}
