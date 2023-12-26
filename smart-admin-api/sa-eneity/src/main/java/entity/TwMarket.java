package entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 行情配置表(TwMarket)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:24:55
 */
@Data
@TableName("tw_market")
public class TwMarket implements Serializable {
    private static final long serialVersionUID = -48117464549715856L;

    private String id;
/**
     * 市场名称
     */
    private String name;

    private String round;

    private String roundMum;
/**
     * 买入最小交易价
     */
    private String buyMin;
/**
     * 买入最大交易价
     */
    private String buyMax;
/**
     * 卖出最小交易价
     */
    private String sellMin;
/**
     * 卖出最大交易价
     */
    private String sellMax;
/**
     * 单笔最小交易额
     */
    private String tradeMin;
/**
     * 单笔最大交易额
     */
    private String tradeMax;
/**
     * 涨幅限制
     */
    private String zhang;
/**
     * 跌幅限制
     */
    private String die;
/**
     * 昨日收盘价
     */
    private String houPrice;

    private String tendency;
/**
     * 开启交易
     */
    private String trade;
/**
     * 最新成交价
     */
    private String newPrice;
/**
     * 买一价
     */
    private String buyPrice;
/**
     * 卖一价
     */
    private String sellPrice;
/**
     * 最低价
     */
    private String minPrice;
/**
     * 最高价
     */
    private String maxPrice;
/**
     * 交易量
     */
    private String volume;
/**
     * 涨跌幅
     */
    private Double change;

    private String apiMin;

    private String apiMax;
/**
     * 排序
     */
    private String sort;

    private String addtime;

    private String endtime;
/**
     * 状态
     */
    private Integer status;
/**
     * 单笔买入最小交易数量
     */
    private String tradeBuyNumMin;
/**
     * 单笔买入最大交易数量
     */
    private String tradeBuyNumMax;
/**
     * 单笔卖出最小交易数量:
     */
    private String tradeSellNumMin;
/**
     * 单笔卖出最大交易数量
     */
    private String tradeSellNumMax;

    private Integer fshow;
/**
     * 刷单开关
     */
    private Integer shuadan;
/**
     * 发行价
     */
    private Double faxingjia;
/**
     * 刷单最高价格
     */
    private String sdhigh;
/**
     * 刷单最低价格
     */
    private String sdlow;
/**
     * 刷单最高数量
     */
    private String sdhighNum;
/**
     * 刷单最低数量
     */
    private String sdlowNum;


}

