package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
* 邀请规则设置
* @TableName tw_invite_set
*/

@Data
@TableName("tw_invite_set")
@ApiModel(value="邀请规则设置", description="")
public class TwInviteSet implements Serializable {

    /**
    * 
    */
    @ApiModelProperty("")
    private Integer id;
    /**
    * 等级名称
    */
    @ApiModelProperty("等级名称")
    private String name;
    /**
    * 最低人数
    */
    @ApiModelProperty("最低人数")
    private Integer min;
    /**
    * 最高人数
    */
    @ApiModelProperty("最高人数")
    private Integer max;

    @ApiModelProperty("公司id")
    private Integer companyId;

    @ApiModelProperty("排序")
    private Integer sort;
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
    private Date updateTime;

}
