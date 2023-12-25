package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 认购记录表(TwIssueLog)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:24:00
 */
@Data
@TableName("tw_issue_log")
public class TwIssueLog implements Serializable {
    private static final long serialVersionUID = 562342860911615284L;
/**
     * 记录ID
     */
    private String id;
/**
     * 认购项目ID
     */
    private String pid;
/**
     * 会员ID
     */
    private String uid;
/**
     * 会员账号
     */
    private String account;
/**
     * 项目名称
     */
    private String name;
/**
     * 认购币名称
     */
    private String coinname;
/**
     * 支付币名称
     */
    private String buycoin;
/**
     * 认购单价
     */
    private String price;
/**
     * 利息
     */
    private Double lixiMoney;
/**
     * 预估收益
     */
    private Double yugu;
/**
     * 认购数量
     */
    private String num;
/**
     * 支付金额
     */
    private String mum;
/**
     * 冻结数量
     */
    private String lockday;
/**
     * 认购时间
     */
    private Date addtime;
/**
     * 释放时间
     */
    private Date endtime;
/**
     * 释放日期
     */
    private Date endday;
/**
     * 状态1冻结中2已解冻
     */
    private Integer status;
/**
     * 收益是否结算到余额
     */
    private Integer jiesuan;

}

