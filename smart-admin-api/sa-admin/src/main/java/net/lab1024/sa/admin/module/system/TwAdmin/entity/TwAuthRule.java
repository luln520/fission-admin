package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (TwAuthRule)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:19:36
 */
@Data
@TableName("tw_auth_rule")
public class TwAuthRule implements Serializable {
    private static final long serialVersionUID = -13551982897146885L;
    /**
     * 规则id,自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 规则所属module
     */
    private String module;
    /**
     * 1-url;2-主菜单
     */
    private Integer type;
    /**
     * 规则唯一英文标识
     */
    private String name;
    /**
     * 规则中文描述
     */
    private String title;
    /**
     * 是否有效(0:无效,1:有效)
     */
    private Integer status;
    /**
     * 规则附加条件
     */
    private String condition;


}

