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
* 贷款表
* @TableName tw_pledge
*/
@Data
@TableName("tw_pledge")
@ApiModel(value="贷款表", description="")
public class TwPledge implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer id;
    /**
    * 贷款类型
    */
    @ApiModelProperty("贷款类型")
    private Integer type;
    /**
    * 余额百分比
    */
    @ApiModelProperty("余额百分比")
    private BigDecimal ratio;
    /**
    *  质押百分比（单位%）
    */
    @ApiModelProperty(" 质押百分比（单位%）")
    private BigDecimal pledgeRatio;
    /**
    * 贷款手续费
    */
    @ApiModelProperty("贷款手续费")
    private BigDecimal premium;
    /**
    * 状态（1，开，2，关)
    */
    @ApiModelProperty("状态（1，开，2，关)")
    private Integer status;
    /**
    *  公司id
    */
    @ApiModelProperty(" 公司id")
    private Integer companyId;
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
