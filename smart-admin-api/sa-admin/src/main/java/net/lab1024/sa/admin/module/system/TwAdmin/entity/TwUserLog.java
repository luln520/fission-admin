package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户记录表(TwUserLog)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:29:10
 */
@Data
@TableName("tw_user_log")
@ApiModel(value="广告图片表", description="")
public class TwUserLog implements Serializable {
    private static final long serialVersionUID = 799632603465017419L;

    private Integer id;

    private Integer userid;

    private String type;

    private String remark;

    private String addip;

    private String addr;

    private Integer sort;

    private Integer addtime;

    private Integer endtime;

    private Integer status;

    @TableField(exist = false) // 排除数据库字段
    private String username;

}

