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
* 货币汇率总表
* @TableName tw_curreny_list
*/
@Data
@TableName("tw_curreny_list")
@ApiModel(value = "货币汇率总表", description = "")
public class TwCurrenyList implements Serializable {

    /**
    * 
    */
    @ApiModelProperty("")
    private Integer id;
    /**
    * 货币名称
    */
    @ApiModelProperty("货币名称")
    private String name;
    /**
    * 名称英文
    */
    @ApiModelProperty("名称英文")
    private String nameEn;
    /**
    * 货币汇率
    */
    @ApiModelProperty("货币汇率")
    private BigDecimal currenyRate;
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
