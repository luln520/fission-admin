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

    @ApiModelProperty("图标")
    private String img;

    @ApiModelProperty("中文内容")
    private String content;

    @ApiModelProperty("西班牙语内容")
    private String contentEs;

    @ApiModelProperty("越语内容")
    private String contentVi;

    @ApiModelProperty("日语内容")
    private String contentJa;

    @ApiModelProperty("英语内容")
    private String contentEn;

    @ApiModelProperty("阿拉伯内容")
    private String contentAr;

    @ApiModelProperty("类型-中文")
    private String type;

    @ApiModelProperty("类型-西班牙")
    private String typeEs;

    @ApiModelProperty("类型-越语")
    private String typeVi;

    @ApiModelProperty("类型-日语")
    private String typeJa;

    @ApiModelProperty("类型-英语")
    private String typeEn;

    @ApiModelProperty("类型-阿拉伯语")
    private String typeAr;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("是否显示 0.不显示，1.显示")
    private Integer isShow;

}
