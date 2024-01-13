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
* 新闻分类
* @TableName tw_news_type
*/
@Data
@TableName("tw_news_type")
@ApiModel(value="新闻分类", description="")
public class TwNewsType implements Serializable {

    /**
    * 
    */
    @ApiModelProperty("")
    private Integer id;
    /**
    * 分类名称
    */
    @ApiModelProperty("分类名称")
    private String name;


    @ApiModelProperty("语言")
    private String language;
    /**
    * 排序
    */
    @ApiModelProperty("排序")
    private Integer sort;
    /**
    * 状态：0.禁用，1.启用
    */
    @ApiModelProperty("状态：0.禁用，1.启用")
    private Integer status;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;

}
