package entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 币币交易记录(TwBborder)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:19:54
 */
@Data
@TableName("tw_bborder")
public class TwBborder implements Serializable {
    private static final long serialVersionUID = -73526390976631874L;
/**
     * 记录ID
     */
    private Integer id;
/**
     * 会员ID
     */
    private Integer uid;
/**
     * 会员账号
     */
    private String account;
/**
     * 订单类型1买2卖
     */
    private Integer type;
/**
     * 交易类别1限价2市价
     */
    private Integer ordertype;
/**
     * 交易对
     */
    private String symbol;
/**
     * 币名称
     */
    private String coin;
/**
     * 交易币数量
     */
    private Double coinnum;
/**
     * 交易USDT数量
     */
    private Double usdtnum;
/**
     * 交易单价
     */
    private Double price;
/**
     * 限价单价
     */
    private Double xjprice;
/**
     * 添加交易
     */
    private Date addtime;
/**
     * 成交时间
     */
    private Date tradetime;
/**
     * 手续费
     */
    private Double fee;
/**
     * 手续费比例
     */
    private String sxfbl;
/**
     * 1委托2交易完成3已撤消
     */
    private Integer status;



}

