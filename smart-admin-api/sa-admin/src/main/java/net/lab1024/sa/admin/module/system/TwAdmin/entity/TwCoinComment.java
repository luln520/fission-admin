package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * (TwCoinComment)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:20:50
 */
@Data
@TableName("tw_coin_comment")
public class TwCoinComment implements Serializable {
    private static final long serialVersionUID = 809213605637698444L;

    private String id;

    private String userid;

    private String coinname;

    private String content;

    private String cjz;

    private String tzy;

    private String xcd;

    private String sort;

    private String addtime;

    private String endtime;

    private Integer status;



}

