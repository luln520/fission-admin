package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 币种配置表(TwCoin)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:20:37
 */
@Data
@TableName("tw_coin")
@ApiModel(value="币种配置表", description="")
public class TwCoin implements Serializable {
    private static final long serialVersionUID = 604169327109804867L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "币种代码")
    private String name;

    @ApiModelProperty(value = "充值网络")
    private String czline;

    @ApiModelProperty(value = "币类型：1钱包币2平台币3认购币")
    private Integer type;

    @ApiModelProperty(value = "币种名称")
    private String title;

    @ApiModelProperty(value = "排序")
    private String sort;

    @ApiModelProperty(value = "添加时间")
    private Date addtime;

    @ApiModelProperty(value = "状态：1可用2禁用")
    private String status;

    @ApiModelProperty(value = "充值状态：1正常2禁止")
    private String czstatus;

    @ApiModelProperty(value = "每日利息")
    private Double lixi;

    @ApiModelProperty(value = "充值地址")
    private String czaddress;

    @ApiModelProperty(value = "最小充值数量")
    private String czminnum;

    @ApiModelProperty(value = "提币状态：1正常2禁止")
    private String txstatus;

    @ApiModelProperty(value = "1按比例，2按数量")
    private String sxftype;

    @ApiModelProperty(value = "提币手续费比例")
    private String txsxf;

    @ApiModelProperty(value = "提币手续费数量")
    private String txsxfN;

    @ApiModelProperty(value = "最小提币数量")
    private String txminnum;

    @ApiModelProperty(value = "最大提币数量")
    private String txmaxnum;

    @ApiModelProperty(value = "币币手续费")
    private String bbsxf;

    @ApiModelProperty(value = "合约手续费")
    private String hysxf;

    @ApiModelProperty(value = "充值网络2")
    private String czline2;

    @ApiModelProperty(value = "充值地址2")
    private String czaddress2;

    @ApiModelProperty(value = "代理ID")
    private Integer agentId;

    @ApiModelProperty(value = "默认充值")
    private Integer defaultOn;

    @ApiModelProperty(value = "usdt余额")
    private Double blance;

    @ApiModelProperty(value = "矿工费余额")
    private Double trxBlance;

    @ApiModelProperty(value = "usdt私钥")
    private String usdtkey;


}

