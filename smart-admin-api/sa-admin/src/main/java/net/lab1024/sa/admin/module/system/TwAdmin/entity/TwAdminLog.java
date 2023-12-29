package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 后台管理员操作日志表(TwAdminLog)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:17:08
 */

@Data
@TableName("tw_admin_log")
public class TwAdminLog implements Serializable {
    private static final long serialVersionUID = -75349395372140753L;
/**
     * 主键ID
     */
    private String id;
/**
     * 管理员ID
     */
    private String adminId;
/**
     * 管理员用户名
     */
    private String adminUsername;
/**
     * 操作名称
     */
    private String action;
/**
     * 操作IP地址
     */
    private String ip;
/**
     * 创建时间
     */
    private int createTime;
/**
     * 备注
     */
    private String remark;

}

