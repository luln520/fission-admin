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
* 用户收藏
* @TableName tw_user_collect
*/
@Data
@TableName("tw_user_collect")
@ApiModel(value="用户收藏", description="")
public class TwUserCollect implements Serializable {

    /**
    * 
    */
    private Integer id;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer uid;
    /**
    * 币种名称
    */
    @ApiModelProperty("币种名称")
    private String coinname;
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
