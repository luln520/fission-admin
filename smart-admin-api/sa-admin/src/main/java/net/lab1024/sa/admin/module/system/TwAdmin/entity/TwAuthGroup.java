package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (TwAuthGroup)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:19:04
 */
@Data
@TableName("tw_auth_group")
public class TwAuthGroup implements Serializable {
    private static final long serialVersionUID = -50405015353335741L;
    /**
     * 用户组id,自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 用户组所属模块
     */
    private String module;
    /**
     * 组类型
     */
    private Integer type;
    /**
     * 用户组中文名称
     */
    private String title;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 用户组状态：为1正常，为0禁用,-1为删除
     */
    private Integer status;
    /**
     * 用户组拥有的规则id，多个规则 , 隔开
     */
    private String rules;


}

