package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 合约交易对配置
* @TableName tw_ctmarket
*/
@Data
@TableName("tw_ctmarket")
@ApiModel(value="合约交易对配置", description="")
public class TwCtmarket implements Serializable {
    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
    * 
    */
    @ApiModelProperty("")
    private String coinname;
    /**
    * 市场名称
    */
    @ApiModelProperty("市场名称")
    private String name;
    /**
    * 交易对
    */
    @ApiModelProperty("交易对")
    private String symbol;
    /**
    * BTC/USDT格式
    */
    @ApiModelProperty("BTC/USDT格式")
    private String title;
    /**
    * 状态1正常2禁用
    */
    @ApiModelProperty("状态1正常2禁用")
    private Integer status;
    /**
    * 交易状态1正常2禁止
    */
    @ApiModelProperty("交易状态1正常2禁止")
    private Integer state;
    /**
    * 排序
    */
    @ApiModelProperty("排序")
    private Integer sort;
    /**
    * 添加时间
    */
    @ApiModelProperty("添加时间")
    private Date addtime=new Date();
    /**
    * 
    */
    @ApiModelProperty("")
    private String logo;

    @ApiModelProperty("简介中文")
    private String infoZh;

    @ApiModelProperty("简介英文")
    private String infoEn;

    @ApiModelProperty("简介西班牙语")
    private String infoEs;

    @ApiModelProperty("简介阿拉伯语")
    private String infoAr;

    @ApiModelProperty("简介越语")
    private String infoVi;

    @ApiModelProperty("简介日语")
    private String infoJa;


}
