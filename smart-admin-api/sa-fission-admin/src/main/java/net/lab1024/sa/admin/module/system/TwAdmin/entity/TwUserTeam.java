package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 会员团队
* @TableName tw_user_team
*/
@Data
@TableName("tw_user_team")
@ApiModel(value="会员团队", description="")
public class TwUserTeam implements Serializable {

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

    @ApiModelProperty("用户名")
    private String username;
    /**
    * 直推总人数
    */
    @ApiModelProperty("直推总人数")
    private Integer total;
    /**
    * 团队无效人数
    */
    @ApiModelProperty("团队无效人数")
    private Integer voidNum;
    /**
    * 团队有效人数
    */
    @ApiModelProperty("团队有效人数")
    private Integer num;
    /**
    * 团队总充值
    */
    @ApiModelProperty("团队总充值")
    private BigDecimal amount;
    /**
    * 公司id
    */
    @ApiModelProperty("公司id")
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
    @ApiModelProperty("团队充值")
    private BigDecimal recharge;

    @TableField(exist = false)
    @ApiModelProperty("团队提现")
    private BigDecimal myzc;

    @TableField(exist = false)
    @ApiModelProperty("团队总盈利")
    private BigDecimal totalWinOrder;

    @TableField(exist = false)
    @ApiModelProperty("团队总亏损")
    private BigDecimal totalLossOrder;

    @TableField(exist = false)
    @ApiModelProperty("团队总投注")
    private BigDecimal totalnumOrder;

    @ApiModelProperty("等级")
    private int grado;


}
