package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 用户币种表
* @TableName tw_user_coin
*/
@Data
@TableName("tw_user_coin")
@ApiModel(value="广告图片表", description="")
public class TwUserCoin implements Serializable {

    /**
    * 
    */
    @ApiModelProperty("")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer userid;
    /**
    * 
    */
    @ApiModelProperty("")
    private BigDecimal usdt;
    /**
    * 
    */
    @ApiModelProperty("")
    private BigDecimal usdtd;
    /**
    * 
    */
    @ApiModelProperty("")
    private String usdtb;
    /**
    * 
    */
    @ApiModelProperty("")
    private BigDecimal btc;
    /**
    * 
    */
    @ApiModelProperty("")
    private BigDecimal btcd;
    /**
    * 
    */
    @ApiModelProperty("")
    private String btcb;
    /**
    * 
    */
    @ApiModelProperty("")
    private BigDecimal eth;
    /**
    * 
    */
    @ApiModelProperty("")
    private BigDecimal ethd;
    /**
    * 
    */
    @ApiModelProperty("")
    private String ethb;

    @TableField(exist = false)
    private  String username;

    @ApiModelProperty("公司id")
    private Integer companyId;

}
