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
* 新闻
* @TableName tw_news
*/
@Data
@TableName("tw_news")
@ApiModel(value="新闻表", description="")
public class TwNews implements Serializable {

    /**
    * id
    */
    @NotNull(message="[id]不能为空")
    @ApiModelProperty("id")
    private Integer id;
    /**
    * 标题
    */
    @ApiModelProperty("标题")
    private String title;
    /**
    * 简介
    */
    @ApiModelProperty("简介")
    private String info;
    /**
    * 图片
    */
    @ApiModelProperty("图片")
    private String img;
    /**
    * 内容
    */
    @ApiModelProperty("内容")
    private String content;
    /**
    * 类型
    */
    @ApiModelProperty("类型")
    private String type;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
    * 是否显示 0.不显示，1.显示
    */
    @ApiModelProperty("是否显示 0.不显示，1.显示")
    private Integer isShow;

}
