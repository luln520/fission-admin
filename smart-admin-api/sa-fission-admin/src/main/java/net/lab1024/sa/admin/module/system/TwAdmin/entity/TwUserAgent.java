package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 会员代理表
* @TableName tw_user_agent
*/

@Data
@TableName("tw_user_agent")
@ApiModel(value="会员代理表", description="")
public class TwUserAgent implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer id;
    /**
    * 直推有效人数
    */
    @ApiModelProperty("uid")
    private Integer uid;

    /**
    * 一级代理
    */
    @ApiModelProperty("一级代理")
    private Integer oneUid;
    /**
    * 二级代理
    */
    @ApiModelProperty("二级代理")
    private Integer twoUid;
    /**
    * 三级代理
    */
    @ApiModelProperty("三级代理")
    private Integer threeUid;
    /**
    * 
    */
    @ApiModelProperty("直推有效人数")
    private Integer num;

    @ApiModelProperty("")
    private Integer companyId;
    /**
    * 
    */
    @ApiModelProperty("")
    private String path;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer department;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;

    @TableField(exist = false)
    @ApiModelProperty("用户code")
    private String userCode;

    @TableField(exist = false)
    @ApiModelProperty("会员名称")
    private String username;

    @TableField(exist = false)
    @ApiModelProperty("一级代理名称")
    private String oneName;

    @TableField(exist = false)
    @ApiModelProperty("二级代理名称")
    private String twoName;

    @TableField(exist = false)
    @ApiModelProperty("三级代理名称")
    private String threeName;


}
