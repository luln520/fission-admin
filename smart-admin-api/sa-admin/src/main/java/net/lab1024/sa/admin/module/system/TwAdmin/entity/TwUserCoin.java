package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户币种表(TwUserCoin)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:28:58
 */
@Data
@TableName("tw_user_coin")
@ApiModel(value="广告图片表", description="")
public class TwUserCoin implements Serializable {
    private static final long serialVersionUID = -97030412396849313L;

    private String id;

    private Integer userid;

    private double usdt;

    private double usdtd;

    private double usdtb;

    private double btc;

    private double btcd;

    private double btcb;

    private double eth;

    private double ethd;

    private double ethb;

    @TableField(exist = false) // 排除数据库字段
    private String username;
}

