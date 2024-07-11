package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
* 
* @TableName tw_copy_set
*/

@Data
@TableName("tw_copy_set")
public class TwCopySet implements Serializable {

    /**
    * 
    */
    @ApiModelProperty("")
    private Integer id;
    /**
    * 止盈
    */
    @ApiModelProperty("止盈")
    private Integer win;
    /**
    * 止损
    */
    @ApiModelProperty("止损")
    private Integer loss;
    /**
    * 手续费
    */
    @ApiModelProperty("手续费")
    private Integer premium;
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
    /**
    * 更新时间
    */
    @ApiModelProperty("更新时间")
    private Date updateTime;



}
