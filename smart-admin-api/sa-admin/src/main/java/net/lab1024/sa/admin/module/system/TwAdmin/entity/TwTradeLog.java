package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * (TwTradeLog)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:28:13
 */
@Data
@TableName("tw_trade_log")
@ApiModel(value="广告图片表", description="")
public class TwTradeLog implements Serializable {
    private static final long serialVersionUID = -70550827321589893L;

    private String id;

    private String market;

    private String price;

    private String num;

    private String mum;

    private String type;

    private String addtime;

    private String endtime;

    private Integer status;

}

