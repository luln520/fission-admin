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
* 
* @TableName tw_online
*/
@Data
@TableName("tw_online")
@ApiModel(value="客服表", description="")
public class TwOnline implements Serializable {

    /**
    * ID
    */
    @ApiModelProperty("ID")
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
    private String username;
    /**
    * 类型：1客服2会员
    */
    @ApiModelProperty("类型：1客服2会员")
    private Integer type;
    /**
    * 内容
    */
    @ApiModelProperty("内容")
    private String content;
    /**
    * 发送时间
    */
    @ApiModelProperty("发送时间")
    private Date addtime=new Date();
    /**
    * 后台查看状态0未查看2已查看
    */
    @ApiModelProperty("后台查看状态0未查看2已查看")
    private Integer state;


}
