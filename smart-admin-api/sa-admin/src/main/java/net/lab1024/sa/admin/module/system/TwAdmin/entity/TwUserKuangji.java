package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 用户矿机单控
* @TableName tw_user_kuangji
*/
@Data
@TableName("tw_user_kuangji")
@ApiModel(value="用户矿机单控", description="")
public class TwUserKuangji implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer id;
    /**
    * 矿机id
    */
    @ApiModelProperty("矿机id")
    private Integer kjId;
    /**
    * 矿机名称
    */
    @ApiModelProperty("矿机名称")
    private String kjName;
    /**
    * 用户id
    */
    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("公司id")
    private Integer companyId;
    /**
    * 投资金额最小值
    */
    @ApiModelProperty("投资金额最小值")
    private BigDecimal min;
    /**
    * 投资金额最大值
    */
    @ApiModelProperty("投资金额最大值")
    private BigDecimal max;
    /**
    * 次数
    */
    @ApiModelProperty("次数")
    private Integer num;
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

}
