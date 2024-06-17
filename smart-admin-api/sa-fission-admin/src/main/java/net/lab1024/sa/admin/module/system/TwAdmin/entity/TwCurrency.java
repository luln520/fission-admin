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
* 货币表
* @TableName tw_currency
*/
@Data
@TableName("tw_currency")
@ApiModel(value = "货币表", description = "")
public class TwCurrency implements Serializable {

    /**
    * 
    */
    private Integer id;
    /**
    * 货币名称
    */
    @ApiModelProperty("货币名称")
    private String name;
    /**
    * 货币名称
    */
    @ApiModelProperty("货币名称")
    private String currency;
    /**
    * 汇率
    */
    @ApiModelProperty("汇率")
    private BigDecimal rate;

    @ApiModelProperty("公司ID")
    private int companyId;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
