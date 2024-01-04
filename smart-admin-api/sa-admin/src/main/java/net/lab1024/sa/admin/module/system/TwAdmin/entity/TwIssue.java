package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 认购发行表(TwIssue)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:23:47
 */

@Data
@TableName("tw_issue")
@ApiModel(value = "广告图片表", description = "")
public class TwIssue implements Serializable {
    private static final long serialVersionUID = -14581264919967208L;
    /**
     * 记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 认购项目标题
     */
    private String name;
    /**
     * 认购币名称
     */
    private String coinname;
    /**
     * 购买币名称
     */
    private String buycoin;
    /**
     * 发行数量
     */
    private String num;
    /**
     * 发行单价
     */
    private String price;
    /**
     * 每日利息增幅
     */
    private Double lixi;
    /**
     * 已售数量
     */
    private String sellnum;
    /**
     * 预设已售数量
     */
    private String ysnum;
    /**
     * 个人认购量上限
     */
    private String allmax;
    /**
     * 最小购买值
     */
    private String min;
    /**
     * 最大购买值
     */
    private String max;
    /**
     * 锁仓周期
     */
    private String lockday;
    /**
     * 认购周期
     */
    private String tian;
    /**
     * 币图片
     */
    private String imgs;
    /**
     * 币说明
     */
    private String content;
    /**
     * 发布日期
     */
    private Date addtime=new Date();
    /**
     * 开始认购时间
     */
    private Date starttime;
    /**
     * 结束认购时间
     */
    private Date finishtime;
    /**
     * 状态1正常2隐藏
     */
    private Integer status;
    /**
     * 状态1启用认购2禁止认购
     */
    private Integer state;
    /**
     * 奖励币种名称
     */
    private String jlcoin;
    /**
     * 一代奖励比例
     */
    private String oneJl;
    /**
     * 二代奖励比例
     */
    private String twoJl;
    /**
     * 三代奖励比例
     */
    private String threeJl;
    /**
     * 原始单价
     */
    private Double yuanPrice;


}

