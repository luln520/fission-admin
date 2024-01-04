package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 通知表
* @TableName tw_notice
*/
@Data
@TableName("tw_notice")
@ApiModel(value="通知表", description="")
public class TwNotice implements Serializable {

    /**
    * 记录ID
    */
    @ApiModelProperty("记录ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
    * 会员ID
    */
    @ApiModelProperty("会员ID")
    private Integer uid;
    /**
    * 会员账号
    */
    @ApiModelProperty("会员账号")
    private String account;
    /**
    * 通知标题
    */
    @ApiModelProperty("通知标题")
    private String title;
    /**
    * 通知内容
    */
    @ApiModelProperty("通知内容")
    private String content;
    /**
    * 通知图片 
    */
    @ApiModelProperty("通知图片 ")
    private String imgs;
    /**
    * 发送时间
    */
    @ApiModelProperty("发送时间")
    private Date addtime=new Date();
    /**
    * 1未读2已读
    */
    @ApiModelProperty("1未读2已读")
    private Integer status;

}
