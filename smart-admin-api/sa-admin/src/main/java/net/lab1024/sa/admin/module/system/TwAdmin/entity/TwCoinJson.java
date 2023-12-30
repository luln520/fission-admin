package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * (TwCoinJson)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:21:02
 */
@Data
@TableName("tw_coin_json")
@ApiModel(value="广告图片表", description="")
public class TwCoinJson implements Serializable {
    private static final long serialVersionUID = -75794272065325925L;

    private String id;

    private String name;

    private String data;

    private String type;

    private String sort;

    private String addtime;

    private String endtime;

    private Integer status;

}

