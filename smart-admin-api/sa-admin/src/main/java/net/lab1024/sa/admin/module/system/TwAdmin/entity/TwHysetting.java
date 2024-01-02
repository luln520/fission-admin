package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 合约参数
* @TableName tw_hysetting
*/
@Data
@TableName("tw_hysetting")
@ApiModel(value="合约参数", description="")
public class TwHysetting implements Serializable {

    /**
    * ID
    */
    @ApiModelProperty("ID")
    private Integer id;
    /**
    * 交易手续费
    */
    @ApiModelProperty("交易手续费")
    private BigDecimal hySxf;
    /**
    * 合约时间组
    */
    @ApiModelProperty("合约时间组")
    private String hyTime;
    /**
    * 盈亏比例组
    */
    @ApiModelProperty("盈亏比例组")
    private String hyYkbl;
    /**
    * 投资额度组
    */
    @ApiModelProperty("投资额度组")
    private String hyTzed;
    /**
    * 开市时间
    */
    @ApiModelProperty("开市时间")
    private String hyKstime;
    /**
    * 亏损ID组
    */
    @ApiModelProperty("亏损ID组")
    private String hyKsid;
    /**
    * 盈利ID组
    */
    @ApiModelProperty("盈利ID组")
    private String hyYlid;
    /**
    * 风控概率组
    */
    @ApiModelProperty("风控概率组")
    private String hyFkgl;
    /**
    * 合约最低投资额
    */
    @ApiModelProperty("合约最低投资额")
    private BigDecimal hyMin;


}
