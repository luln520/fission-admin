package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (TwAppc)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:18:08
 */
@Data
@TableName("tw_appc")
public class TwAppc implements Serializable {
    private static final long serialVersionUID = 856903459258396029L;

    private Integer id;

    private String webName;

    private String webTitle;

    private String webIcp;

    private String indexImg;

    private String pay;

    private String withdrawNotice;

    private String chargeNotice;

    private String showCoin;

    private String showMarket;



}

