package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 币种配置表
* @TableName tw_coin
*/
@Data
@TableName("tw_coin")
@ApiModel(value="币种配置表", description="")
public class TwCoin implements Serializable {

    /**
    * ID
    */
    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
    * 币种代码
    */
    @ApiModelProperty("币种代码")
    private String name;
    /**
    * 充值网络
    */
    @ApiModelProperty("充值网络")
    private String czline;
    /**
    * 币类型：1钱包币2平台币3认购币
    */
    @ApiModelProperty("币类型：1钱包币2平台币3认购币")
    private Integer type;
    /**
    * 币种名称
    */
    @ApiModelProperty("币种名称")
    private String title;
    /**
    * 排序
    */
    @ApiModelProperty("排序")
    private Integer sort;
    /**
    * 添加时间
    */
    @ApiModelProperty("添加时间")
    private Date addtime=new Date();
    /**
    * 状态：1可用2禁用
    */
    @ApiModelProperty("状态：1可用2禁用")
    private Integer status;
    /**
    * 充值状态：1正常2禁止
    */
    @ApiModelProperty("充值状态：1正常2禁止")
    private Integer czstatus;
    /**
    * 每日利息
    */
    @ApiModelProperty("每日利息")
    private BigDecimal lixi;
    /**
    * 充值地址
    */
    @ApiModelProperty("充值地址")
    private String czaddress;
    /**
    * 最小充值数量
    */
    @ApiModelProperty("最小充值数量")
    private BigDecimal czminnum;
    /**
    * 提币状态：1正常2禁止
    */
    @ApiModelProperty("提币状态：1正常2禁止")
    private Integer txstatus;
    /**
    * 1按比例，2按数量
    */
    @ApiModelProperty("1按比例，2按数量")
    private Integer sxftype;
    /**
    * 提币手续费比例
    */
    @ApiModelProperty("提币手续费比例")
    private BigDecimal txsxf;
    /**
    * 提币手续费数量
    */
    @ApiModelProperty("提币手续费数量")
    private BigDecimal txsxfN;
    /**
    * 最小提币数量
    */
    @ApiModelProperty("最小提币数量")
    private BigDecimal txminnum;
    /**
    * 最大提币数量
    */
    @ApiModelProperty("最大提币数量")
    private BigDecimal txmaxnum;
    /**
    * 币币手续费
    */
    @ApiModelProperty("币币手续费")
    private BigDecimal bbsxf;
    /**
    * 合约手续费
    */
    @ApiModelProperty("合约手续费")
    private BigDecimal hysxf;
    /**
    * 充值网络2
    */
    @ApiModelProperty("充值网络2")
    private String czline2;
    /**
    * 充值地址2
    */
    @ApiModelProperty("充值地址2")
    private String czaddress2;
    /**
    * 代理ID
    */
    @ApiModelProperty("代理ID")
    private Integer agentId;
    /**
    * 默认充值
    */
    @ApiModelProperty("默认充值")
    private Integer defaultOn;
    /**
    * usdt余额
    */
    @ApiModelProperty("usdt余额")
    private BigDecimal blance;
    /**
    * 矿工费余额
    */
    @ApiModelProperty("矿工费余额")
    private BigDecimal trxBlance;
    /**
    * usdt私钥
    */
    @ApiModelProperty("usdt私钥")
    private String usdtkey;

    @ApiModelProperty("公司id")
    private Integer companyId;

}
