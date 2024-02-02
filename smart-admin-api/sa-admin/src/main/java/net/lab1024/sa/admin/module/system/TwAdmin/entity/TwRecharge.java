package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 充值记录
* @TableName tw_recharge
*/
@Data
@TableName("tw_recharge")
@ApiModel(value="充值记录", description="")
public class TwRecharge implements Serializable {

    /**
    * id
    */
    @ApiModelProperty("id")
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
    * 币名称
    */
    @ApiModelProperty("币名称")
    private String coin;
    /**
    * 名称
    */
    @ApiModelProperty("名称")
    private BigDecimal num;
    /**
    * 添加时间
    */
    @ApiModelProperty("添加时间")
    private Date addtime=new Date();
    /**
    * 处理时间
    */
    @ApiModelProperty("处理时间")
    private Date updatetime;
    /**
    * 状态: 待审核 1 - 审核通过 2 - 不通过3
    */
    @ApiModelProperty("状态: 待审核 1 - 审核通过 2 - 不通过3")
    private Integer status;
    /**
    * 付款凭证
    */
    @ApiModelProperty("付款凭证")
    private String payimg;
    /**
    * 不通过说明
    */
    @ApiModelProperty("不通过说明")
    private String msg;
    /**
    * 
    */
    @ApiModelProperty("")
    private String czline;
    /**
    * 0用户充值1管理员充值
    */
    @ApiModelProperty("0用户充值1管理员充值")
    private Integer atype;
    /**
    * 充值地址
    */
    @ApiModelProperty("充值地址")
    private String address;
    /**
    * trc20交易Id
    */
    @ApiModelProperty("trc20交易Id")
    private String trc20id;

    @ApiModelProperty("团队路径")
    private String path;

    @ApiModelProperty("部门id")
    private Integer department;

    @ApiModelProperty("订单编号")
    private String orderNo;

}
