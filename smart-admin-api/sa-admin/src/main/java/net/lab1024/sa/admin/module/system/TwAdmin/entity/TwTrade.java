package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 交易下单表(TwTrade)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:27:48
 */
@Data
@TableName("tw_trade")
@ApiModel(value="广告图片表", description="")
public class TwTrade implements Serializable {
    private static final long serialVersionUID = -34506882725888635L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String userid;

    private String market;

    private String price;

    private String num;

    private String deal;

    private String mum;

    private String type;

    private String sort;

    private String addtime;

    private String endtime;

    private Integer status;

}

