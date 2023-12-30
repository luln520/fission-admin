package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * (TwFooter)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:22:59
 */
@Data
@TableName("tw_footer")
@ApiModel(value="广告图片表", description="")
public class TwFooter implements Serializable {
    private static final long serialVersionUID = -98326543815443005L;

    private String id;

    private String lang;

    private String name;

    private String title;

    private String url;

    private String img;

    private String type;

    private String remark;

    private String sort;

    private String addtime;

    private String endtime;

    private Integer status;

    private Integer getLogin;

}

