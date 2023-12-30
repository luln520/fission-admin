package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * (TwMarketJson)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:25:08
 */
@Data
@TableName("tw_market_json")
@ApiModel(value="广告图片表", description="")
public class TwMarketJson implements Serializable {
    private static final long serialVersionUID = -89046548153716764L;

    private String id;

    private String name;

    private String data;

    private String type;

    private String sort;

    private String addtime;

    private String endtime;

    private Integer status;

}

