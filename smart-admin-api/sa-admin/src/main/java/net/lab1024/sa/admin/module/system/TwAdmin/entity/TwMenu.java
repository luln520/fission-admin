package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (TwMenu)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:26:22
 */
@Data
@TableName("tw_menu")
public class TwMenu implements Serializable {
    private static final long serialVersionUID = -55403686871396734L;
/**
     * 文档ID
     */
    private String id;
/**
     * 标题
     */
    private String title;
/**
     * 上级分类ID
     */
    private String pid;
/**
     * 排序（同级有效）
     */
    private String sort;
/**
     * 链接地址
     */
    private String url;
/**
     * 是否隐藏
     */
    private String hide;
/**
     * 提示
     */
    private String tip;
/**
     * 分组
     */
    private String group;
/**
     * 是否仅开发者模式可见
     */
    private String isDev;

    private String icoName;


}

