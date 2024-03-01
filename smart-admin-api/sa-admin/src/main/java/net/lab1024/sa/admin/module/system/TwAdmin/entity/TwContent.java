package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 公告内容
* @TableName tw_content
*/
@Data
@TableName("tw_content")
@ApiModel(value="公告内容", description="")
public class TwContent implements Serializable {

    @ApiModelProperty("ID")
    private Integer id;
    /**
    * 标题
    */
    @ApiModelProperty("标题")
    private String title;
    /**
    * 公告图片
    */
    @ApiModelProperty("公告图片")
    private String img;
    /**
    * 中文内容
    */
    @ApiModelProperty("中文内容")
    private String content;
    /**
    * 西班牙
    */
    @ApiModelProperty("西班牙")
    private String contentEs;
    /**
    * 越语
    */
    @ApiModelProperty("越语")
    private String contentVi;
    /**
    * 日语
    */
    @ApiModelProperty("日语")
    private String contentJa;
    /**
    * 英语
    */
    @ApiModelProperty("英语")
    private String contentEn;
    /**
    * 阿拉伯
    */
    @ApiModelProperty("阿拉伯")
    private String contentAr;
    /**
    * 添加时间
    */
    @ApiModelProperty("添加时间")
    private Date addtime;
    /**
    * 状态1显示2隐藏
    */
    @ApiModelProperty("状态1显示2隐藏")
    private Integer status;

    @ApiModelProperty("公司id")
    private Integer companyId;
}
