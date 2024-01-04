package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 行情配置表
* @TableName tw_market
*/
@Data
@TableName("tw_market")
@ApiModel(value="行情配置表", description="")
public class TwMarket implements Serializable {

    /**
    * 
    */
    @ApiModelProperty("")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
    * 市场名称
    */
    @ApiModelProperty("市场名称")
    private String name;
    /**
    * 
    */
    @ApiModelProperty("")
    private String round;
    /**
    * 
    */
    @ApiModelProperty("")
    private String roundMum;
    /**
    * 买入最小交易价
    */
    @ApiModelProperty("买入最小交易价")
    private String buyMin;
    /**
    * 买入最大交易价
    */
    @ApiModelProperty("买入最大交易价")
    private String buyMax;
    /**
    * 卖出最小交易价
    */
    @ApiModelProperty("卖出最小交易价")
    private String sellMin;
    /**
    * 卖出最大交易价
    */
    @ApiModelProperty("卖出最大交易价")
    private String sellMax;
    /**
    * 单笔最小交易额
    */
    @ApiModelProperty("单笔最小交易额")
    private String tradeMin;
    /**
    * 单笔最大交易额
    */
    @ApiModelProperty("单笔最大交易额")
    private String tradeMax;
    /**
    * 涨幅限制
    */
    @ApiModelProperty("涨幅限制")
    private String zhang;
    /**
    * 跌幅限制
    */
    @ApiModelProperty("跌幅限制")
    private String die;
    /**
    * 昨日收盘价
    */
    @ApiModelProperty("昨日收盘价")
    private String houPrice;
    /**
    * 
    */
    @ApiModelProperty("")
    private String tendency;
    /**
    * 开启交易
    */
    @ApiModelProperty("开启交易")
    private Integer trade;
    /**
    * 最新成交价
    */
    @ApiModelProperty("最新成交价")
    private BigDecimal newPrice;
    /**
    * 买一价
    */
    @ApiModelProperty("买一价")
    private BigDecimal buyPrice;
    /**
    * 卖一价
    */
    @ApiModelProperty("卖一价")
    private BigDecimal sellPrice;
    /**
    * 最低价
    */
    @ApiModelProperty("最低价")
    private BigDecimal minPrice;
    /**
    * 最高价
    */
    @ApiModelProperty("最高价")
    private BigDecimal maxPrice;
    /**
    * 交易量
    */
    @ApiModelProperty("交易量")
    private BigDecimal volume;
    /**
    * 涨跌幅
    */
    @ApiModelProperty("涨跌幅")
    private BigDecimal change;
    /**
    * 
    */
    @ApiModelProperty("")
    private BigDecimal apiMin;
    /**
    * 
    */
    @ApiModelProperty("")
    private BigDecimal apiMax;
    /**
    * 排序
    */
    @ApiModelProperty("排序")
    private Integer sort;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer addtime;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer endtime;
    /**
    * 状态
    */
    @ApiModelProperty("状态")
    private Integer status;
    /**
    * 单笔买入最小交易数量
    */
    @ApiModelProperty("单笔买入最小交易数量")
    private String tradeBuyNumMin;
    /**
    * 单笔买入最大交易数量
    */
    @ApiModelProperty("单笔买入最大交易数量")
    private String tradeBuyNumMax;
    /**
    * 单笔卖出最小交易数量:
    */
    @ApiModelProperty("单笔卖出最小交易数量:")
    private String tradeSellNumMin;
    /**
    * 单笔卖出最大交易数量
    */
    @ApiModelProperty("单笔卖出最大交易数量")
    private String tradeSellNumMax;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer fshow;
    /**
    * 刷单开关
    */
    private Integer shuadan;
    /**
    * 发行价
    */
    @ApiModelProperty("发行价")
    private BigDecimal faxingjia;
    /**
    * 刷单最高价格
    */
    @ApiModelProperty("刷单最高价格")
    private String sdhigh;
    /**
    * 刷单最低价格
    */
    @ApiModelProperty("刷单最低价格")
    private String sdlow;
    /**
    * 刷单最高数量
    */
    @ApiModelProperty("刷单最高数量")
    private String sdhighNum;
    /**
    * 刷单最低数量
    */
    @ApiModelProperty("刷单最低数量")
    private String sdlowNum;

}
