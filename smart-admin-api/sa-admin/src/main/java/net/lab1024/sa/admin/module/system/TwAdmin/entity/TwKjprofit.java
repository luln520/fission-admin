package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 矿机收益表
* @TableName tw_kjprofit
*/
@Data
@TableName("tw_kjprofit")
@ApiModel(value="矿机收益表", description="")
public class TwKjprofit implements Serializable {

    /**
    * 记录ID
    */
    @ApiModelProperty("记录ID")
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
    * 矿机ID
    */
    @ApiModelProperty("矿机ID")
    private Integer kid;
    /**
    * 矿机名称
    */
    @ApiModelProperty("矿机名称")
    private String ktitle;
    /**
    * 收益金额
    */
    @ApiModelProperty("收益金额")
    private BigDecimal num;
    /**
    * 收益币种
    */
    @ApiModelProperty("收益币种")
    private String coin;
    /**
    * 收益时间
    */
    @ApiModelProperty("收益时间")
    private Date addtime=new Date();
    /**
    * 收益日期
    */
    @ApiModelProperty("收益日期")
    private Date day;

    @ApiModelProperty("团队路径")
    private String path;

    @ApiModelProperty("部门id")
    private Integer department;

    @TableField(exist = false)
    private int status;

    @ApiModelProperty("订单编号")
    private String orderNo;

}
