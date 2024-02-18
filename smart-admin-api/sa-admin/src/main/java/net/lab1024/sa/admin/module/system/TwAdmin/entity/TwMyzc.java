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
* 提币表
* @TableName tw_myzc
*/

@Data
@TableName("tw_myzc")
@ApiModel(value="提币表", description="")
public class TwMyzc implements Serializable {

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
    private Integer userid;
    /**
    * 会员账号
    */
    @ApiModelProperty("会员账号")
    private String username;
    /**
    * 提币币种
    */
    @ApiModelProperty("提币币种")
    private String coinname;
    /**
    * 
    */
    @ApiModelProperty("")
    private String txid;
    /**
    * 提币数量
    */
    @ApiModelProperty("提币数量")
    private BigDecimal num;
    /**
    * 手续费
    */
    @ApiModelProperty("手续费")
    private BigDecimal fee;
    /**
    * 实际到账
    */
    @ApiModelProperty("实际到账")
    private BigDecimal mum;
    /**
    * 提币地址
    */
    @ApiModelProperty("提币地址")
    private String address;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer sort;
    /**
    * 申请时间
    */
    @ApiModelProperty("申请时间")
    private Date addtime=new Date();
    /**
    * 
    */
    @ApiModelProperty("")
    private Date endtime;
    /**
    * 1待审核2完成3未通过
    */
    @ApiModelProperty("1待审核2完成3未通过")
    private Integer status;
    /**
    * 会员转币
    */
    @ApiModelProperty("会员转币")
    private Integer toUser;

    @ApiModelProperty("")
    private String czline;

    @ApiModelProperty("团队路径")
    private String path;

    @ApiModelProperty("部门id")
    private Integer department;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty(value = "用户code")
    private String userCode;

    @ApiModelProperty("公司id")
    private Integer companyId;


}
