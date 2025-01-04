package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import java.io.Serializable;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 模拟用户币种表
* @TableName tw_mock_user_coin
*/
@Data
@TableName("tw_mock_user_coin")
@ApiModel(value="模拟用户币种表", description="")
public class TwMockUserCoin implements Serializable {

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
    /**
    * 公司id
    */
    @ApiModelProperty("公司id")
    private Integer companyId;

    @ApiModelProperty("是否已领取 （1.未领取，2已领取）")
    private Integer status;


    @TableField(exist = false)
    private  String username;


}
