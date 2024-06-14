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
* 用户邀请表
* @TableName tw_user_invite
*/

@Data
@TableName("tw_user_invite")
@ApiModel(value="用户邀请表", description="")
public class TwUserInvite implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer id;
    /**
    * 用户id
    */
    @ApiModelProperty("用户id")
    private Integer uid;
    /**
    * 被邀请人id
    */
    @ApiModelProperty("被邀请人id")
    private Integer invitUid;

    @ApiModelProperty("邀请人code")
    private String userCode;

    @ApiModelProperty("用户名称")
    private String username;
    /**
    * 公司id
    */
    @ApiModelProperty("公司id")
    private Integer companyId;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;



}
