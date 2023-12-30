package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="后台管理员操作日志表", description="")
public class TwAdminLog implements Serializable {
    private static final long serialVersionUID = -75349395372140753L;

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "管理员ID")
    private String adminId;

    @ApiModelProperty(value = "管理员用户名")
    private String adminUsername;

     @ApiModelProperty(value = "操作名称")
    private String action;

    @ApiModelProperty(value = "操作IP地址")
    private String ip;

    @ApiModelProperty(value = "创建时间")
    private int createTime;

    @ApiModelProperty(value = "备注")
    private String remark;

}

