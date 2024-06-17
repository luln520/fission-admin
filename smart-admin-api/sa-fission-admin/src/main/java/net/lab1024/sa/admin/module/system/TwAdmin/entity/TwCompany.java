package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 公司表
* @TableName tw_company
*/

@Data
@TableName("tw_company")
public class TwCompany implements Serializable {

    /**
    * 公司id
    */
    @ApiModelProperty("公司id")
    private Integer id;
    /**
    * 公司名称
    */
    @ApiModelProperty("公司名称")
    private String name;
    /**
    * 平台域名
    */
    @ApiModelProperty("平台域名")
    private String companyDomain;
    /**
    * 平台logo
    */
    @ApiModelProperty("平台logo")
    private String companyLogo;
    /**
    * 平台名称
    */
    @ApiModelProperty("平台名称")
    private String companyName;
    /**
    * 平台logo_图片名称
    */
    @ApiModelProperty("平台logo_图片名称")
    private String companyLogoName;
    /**
    * 平台皮肤(1.碧蓝色,2.翠绿色,3.柠檬黄)
    */
    @ApiModelProperty("平台皮肤(1.碧蓝色,2.翠绿色,3.柠檬黄)")
    private Integer companySkin;
    /**
    * 平台账号
    */
    @ApiModelProperty("平台账号")
    private String companyAccount;
    /**
    * 平台密码
    */
    @ApiModelProperty("平台密码")
    private String companyPwd;
    /**
    * 平台邮箱
    */
    @ApiModelProperty("平台邮箱")
    private String companyMail;
    /**
    * 平台邮箱密码
    */
    @ApiModelProperty("平台邮箱密码")
    private String companyMailPwd;
    /**
    * H5轮播图1
    */
    @ApiModelProperty("H5轮播图1")
    private String logo1;
    /**
    * H5轮播图2
    */
    @ApiModelProperty("H5轮播图2")
    private String logo2;
    /**
    * H5轮播图3
    */
    @ApiModelProperty("H5轮播图3")
    private String logo3;
    /**
    * PC轮播图1
    */
    @ApiModelProperty("PC轮播图1")
    private String pcLogo1;
    /**
    * PC轮播图2
    */
    @ApiModelProperty("PC轮播图2")
    private String pcLogo2;
    /**
    * PC轮播图3
    */
    @ApiModelProperty("PC轮播图3")
    private String pcLogo3;
    /**
    * PC轮播图4
    */
    @ApiModelProperty("PC轮播图4")
    private String pcLogo4;
    /**
    * 手机下载落地页域名
    */
    @ApiModelProperty("手机下载落地页域名")
    private String phoneDomian;
    /**
    * 苹果下载落地页域名
    */
    @ApiModelProperty("苹果下载落地页域名")
    private String iosDomain;
    /**
    * 安卓下载落地页域名
    */
    @ApiModelProperty("安卓下载落地页域名")
    private String androidDomain;
    /**
    * 状态(1.开启，2.关闭)
    */
    @ApiModelProperty("状态(1.开启，2.关闭)")
    private Integer status;
    /**
    * 删除状态(1.正常，2.删除)
    */
    @ApiModelProperty("删除状态(1.正常，2.删除)")
    private Integer isDel;
    /**
    * 杠杆订单交易手续费
    */
    @ApiModelProperty("杠杆订单交易手续费")
    private BigDecimal leverFee;
    /**
    * 合约订单交易手续费
    */
    @ApiModelProperty("合约订单交易手续费")
    private BigDecimal hyFee;
    /**
    * 矿机订单交易手续费
    */
    @ApiModelProperty("矿机订单交易手续费")
    private BigDecimal kjFee;

    @ApiModelProperty("邀请码开关（1.开，2.关)")
    private int inviteType;

    @ApiModelProperty("APP下载开关  1.开 2.关")
    private int appStatus;

    @ApiModelProperty("C2C提现状态：1 ：开 2 ：关")
    private int c2ctxStatus;
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
