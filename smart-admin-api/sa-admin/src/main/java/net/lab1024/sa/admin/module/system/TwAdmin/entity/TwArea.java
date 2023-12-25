package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (TwArea)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:18:24
 */
@Data
@TableName("tw_area")
public class TwArea implements Serializable {
    private static final long serialVersionUID = 718272322570018712L;

    private Integer id;

    private String nameZh;

    private String nameEn;

    private String nameAbbr;

    private String internationalAreaCode;



}

