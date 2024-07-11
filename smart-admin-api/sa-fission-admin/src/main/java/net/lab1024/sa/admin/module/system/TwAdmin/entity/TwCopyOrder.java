package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
* 跟单表
* @TableName tw_copy_order
*/

@Data
@TableName("tw_copy_order")
public class TwCopyOrder implements Serializable {

    /**
    * 
    */
    @ApiModelProperty("")
    private Integer id;
    /**
    * 跟单id
    */
    @ApiModelProperty("跟单id")
    private Integer copyId;
    /**
    * 跟单订单号
    */
    @ApiModelProperty("跟单订单号")
    private String copyOrder;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer uid;
    /**
    * 用户名称
    */
    @ApiModelProperty("用户名称")
    private String username;
    /**
    * 订单号
    */
    @ApiModelProperty("订单号")
    private String order;
    /**
    * 状态 1.未结算  2.已结算
    */
    @ApiModelProperty("状态 1.未结算  2.已结算")
    private Integer status;
    /**
    * 状态：1.跟单 2.结束跟单
    */
    @ApiModelProperty("状态：1.跟单 2.结束跟单")
    private Integer close;
    /**
    * 团队路径
    */
    @ApiModelProperty("团队路径")
    private String path;
}
