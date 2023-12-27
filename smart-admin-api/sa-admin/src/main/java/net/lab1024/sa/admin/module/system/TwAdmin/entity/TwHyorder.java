package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 合约订单表(TwHyorder)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:23:17
 */
@Data
@TableName("tw_hyorder")
public class TwHyorder implements Serializable {
    private static final long serialVersionUID = 987650303006884415L;
/**
     * ID
     */
    private Integer id;
/**
     * 会员ID
     */
    private Integer uid;
/**
     * 会员账号
     */
    private String username;
/**
     * 投资金额
     */
    private String num;
/**
     * 买后余额
     */
    private Double buyOrblance;
/**
     * 盈亏比例
     */
    private String hybl;
/**
     * 合约涨跌1买涨2买跌
     */
    private Integer hyzd;
/**
     * 交易对
     */
    private String coinname;
/**
     * 状态：1待结算2已结算3无效结算
     */
    private Integer status;
/**
     * 盈亏状态：1盈利2亏损
     */
    private Integer isWin;
/**
     * 购买时间
     */
    private Date buytime;
/**
     * 结算时间
     */
    private Date selltime;
/**
     * 结算时间戳
     */
    private Integer intselltime;
/**
     * 建仓单价
     */
    private Double buyprice;
/**
     * 结算单价
     */
    private Double sellprice;
/**
     * 盈亏金额
     */
    private Double ploss;
/**
     * 结算分钟数
     */
    private Integer time;
/**
     * 控制盈亏1盈利2亏损0未指定
     */
    private Integer kongyk;
/**
     * 邀请码(上级)
     */
    private String invit;
/**
     * 0未通知，1已通知
     */
    private Integer tznum;


}

