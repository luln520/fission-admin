package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* 接单员表
* @TableName tw_copy_take
*/

@Data
@TableName("tw_copy_take")
public class TwCopyTake implements Serializable {

    /**
    * id
    */
    @ApiModelProperty("id")
    private Integer id;
    /**
    * 用户id
    */
    @ApiModelProperty("用户id")
    private Integer uid;
    /**
    * 用户名称
    */
    @ApiModelProperty("用户名称")
    private String username;
    /**
    * 标题
    */
    @ApiModelProperty("标题")
    private String title;
    /**
    * 简介
    */
    @ApiModelProperty("简介")
    private String info;
    /**
    * 最小跟单金额
    */
    @ApiModelProperty("最小跟单金额")
    private BigDecimal min;
    /**
    * 跟单人数
    */
    @ApiModelProperty("跟单人数")
    private Integer num;
    /**
    * 跟单管理总额
    */
    @ApiModelProperty("跟单管理总额")
    private BigDecimal sum;
    /**
    * 盈亏总额
    */
    @ApiModelProperty("盈亏总额")
    private BigDecimal ploss;
    /**
    * 路径团队
    */
    @ApiModelProperty("路径团队")
    private String path;
    /**
    * 部门id
    */
    @ApiModelProperty("部门id")
    private Integer department;
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
