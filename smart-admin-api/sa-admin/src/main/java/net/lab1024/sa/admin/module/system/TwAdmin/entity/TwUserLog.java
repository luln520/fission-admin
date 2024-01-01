package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 用户记录表
* @TableName tw_user_log
*/
@Data
@TableName("tw_user_log")
@ApiModel(value="用户记录表", description="")
public class TwUserLog implements Serializable {

    /**
    * 
    */
    @ApiModelProperty("")
    private Integer id;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer userid;
    /**
    * 
    */
    @ApiModelProperty("")
    private String type;
    /**
    * 
    */
    @ApiModelProperty("")
    private String remark;
    /**
    * 
    */
    @ApiModelProperty("")
    private String addip;
    /**
    * 
    */
    @ApiModelProperty("")
    private String addr;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer sort;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer addtime;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer endtime;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer status;

    @TableField(exist = false)
    private  String username;

}
