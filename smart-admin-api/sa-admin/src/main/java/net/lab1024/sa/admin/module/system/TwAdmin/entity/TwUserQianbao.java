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
* 用户钱包表
* @TableName tw_user_qianbao
*/

@Data
@TableName("tw_user_qianbao")
@ApiModel(value="用户钱包表", description="")
public class TwUserQianbao implements Serializable {

    /**
    * ID
    */
    @ApiModelProperty("ID")
    private Integer id;
    /**
    * 会员ID
    */
    @ApiModelProperty("会员ID")
    private Integer userid;
    /**
    * 会员账号
    */
    @ApiModelProperty("会员账号")
    private String coinname;
    /**
    * 币名称
    */
    @ApiModelProperty("币名称")
    private String name;
    /**
    * 地址备注
    */
    @ApiModelProperty("地址备注")
    private String remark;
    /**
    * 充值网络
    */
    @ApiModelProperty("充值网络")
    private String czline;
    /**
    * 提币地址
    */
    @ApiModelProperty("提币地址")
    private String addr;
    /**
    * 排序
    */
    @ApiModelProperty("排序")
    private Integer sort;
    /**
    * 添加时间
    */
    @ApiModelProperty("添加时间")
    private Date addtime;
    /**
    * 状态
    */
    @ApiModelProperty("状态")
    private Integer status;


}
