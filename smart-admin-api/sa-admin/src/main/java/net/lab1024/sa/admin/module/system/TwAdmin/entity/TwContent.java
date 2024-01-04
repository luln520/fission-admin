package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 公告内容(TwContent)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:21:31
 */
@Data
@TableName("tw_content")
@ApiModel(value="公告内容", description="")
public class TwContent implements Serializable {
    private static final long serialVersionUID = -47261435617866204L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

     @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("公告图片")
    private String img;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("添加时间")
    private Date addtime=new Date();

    @ApiModelProperty("状态1显示2隐藏")
    private Integer status;

}

