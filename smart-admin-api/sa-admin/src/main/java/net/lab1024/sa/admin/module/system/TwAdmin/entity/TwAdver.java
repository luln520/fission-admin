package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 广告图片表(TwAdver)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:17:46
 */
@Data
@TableName("tw_adver")
public class TwAdver implements Serializable {
    private static final long serialVersionUID = -48313779113893827L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String lang;

    private String name;
/**
     * 副标题描述
     */
    private String subhead;

    private String url;

    private String img;

    private String type;

    private String sort;

    private String addtime;

    private String endtime;

    private String onlinetime;

    private String status;
/**
     * 0 电脑端 1手机端
     */
    private Integer look;



}

