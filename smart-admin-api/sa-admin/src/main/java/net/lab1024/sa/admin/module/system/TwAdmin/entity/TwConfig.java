package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 网站配置表
* @TableName tw_config
*/
@Data
@TableName("tw_config")
@ApiModel(value="网站配置表", description="")
public class TwConfig implements Serializable {

    /**
    * 记录ID
    */
    @ApiModelProperty("记录ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
    * 网站名称
    */
    @ApiModelProperty("网站名称")
    private String webname;
    /**
    * 网络标题
    */
    @ApiModelProperty("网络标题")
    private String webtitle;
    /**
    * 手机端网站logo
    */
    @ApiModelProperty("手机端网站logo")
    private String weblogo;
    /**
    * PC端网站logo
    */
    @ApiModelProperty("PC端网站logo")
    private String waplogo;
    /**
    * 网站开关1开2关
    */
    @ApiModelProperty("网站开关1开2关")
    private Integer webswitch;
    /**
    * 手机端轮播图1
    */
    @ApiModelProperty("手机端轮播图1")
    private String websildea;
    /**
    * 手机端轮播图2
    */
    @ApiModelProperty("手机端轮播图2")
    private String websildeb;
    /**
    * 手机端轮播图2
    */
    @ApiModelProperty("手机端轮播图2")
    private String websildec;
    /**
    * 
    */
    @ApiModelProperty("")
    private String wapsilded;
    /**
    * 手机端新币认购图片
    */
    @ApiModelProperty("手机端新币认购图片")
    private String webissue;
    /**
    * 手机端矿机首页图片
    */
    @ApiModelProperty("手机端矿机首页图片")
    private String webkj;
    /**
    * PC端轮播图1
    */
    @ApiModelProperty("PC端轮播图1")
    private String wapsildea;
    /**
    * PC端轮播图2
    */
    @ApiModelProperty("PC端轮播图2")
    private String wapsildeb;
    /**
    * PC端轮播图3
    */
    @ApiModelProperty("PC端轮播图3")
    private String wapsildec;
    /**
    * PC端新币认购图片
    */
    @ApiModelProperty("PC端新币认购图片")
    private String wapissue;
    /**
    * PC端矿机首页图片
    */
    @ApiModelProperty("PC端矿机首页图片")
    private String wapkj;
    /**
    * 手机端推荐页面logo图片
    */
    @ApiModelProperty("手机端推荐页面logo图片")
    private String webtjimgs;
    /**
    * PC端推荐页面logo图片
    */
    @ApiModelProperty("PC端推荐页面logo图片")
    private String waptjimgs;
    /**
    * 短信发送邮箱
    */
    @ApiModelProperty("短信发送邮箱")
    private String smsemail;
    /**
    * 邮箱授权码
    */
    @ApiModelProperty("邮箱授权码")
    private String emailcode;
    /**
    * smtp服务器地址
    */
    @ApiModelProperty("smtp服务器地址")
    private String smtpdz;
    /**
    * 短信验证码模板
    */
    @ApiModelProperty("短信验证码模板")
    private String smstemple;
    /**
    * 推荐页面推广语
    */
    @ApiModelProperty("推荐页面推广语")
    private String tgtext;
    /**
    * 官方客服邮箱
    */
    @ApiModelProperty("官方客服邮箱")
    private String gfemail;
    /**
    * PC端下方文字
    */
    @ApiModelProperty("PC端下方文字")
    private String footertext;
    /**
    * 注册开关
    */
    @NotNull(message="[注册开关]不能为空")
    @ApiModelProperty("注册开关")
    private Integer regswitch;
    /**
    * 提币开关
    */
    @NotNull(message="[提币开关]不能为空")
    @ApiModelProperty("提币开关")
    private Integer tbswitch;
    /**
    * 注册是赠送体验矿机
    */
    @NotNull(message="[注册是赠送体验矿机]不能为空")
    @ApiModelProperty("注册是赠送体验矿机")
    private Integer regjl;
    /**
    * 注册赠送的体验金
    */
    @NotNull(message="[注册赠送的体验金]不能为空")
    @ApiModelProperty("注册赠送的体验金")
    private BigDecimal tymoney;
    /**
    * 不可修改,否则报错
    */
    @ApiModelProperty("不可修改,否则报错")
    private String versionkey;
    /**
    * 短信提交网关
    */
    @ApiModelProperty("短信提交网关")
    private String smsUrl;
    /**
    * 短信商户ID
    */
    @ApiModelProperty("短信商户ID")
    private String smsId;
    /**
    * 短信商户密钥
    */
    @ApiModelProperty("短信商户密钥")
    private String smsKey;
    /**
    * 自动归集起始金额
    */
    @ApiModelProperty("自动归集起始金额")
    private BigDecimal startmoney;
    /**
    * TRX手续费账户
    */
    @ApiModelProperty("TRX手续费账户")
    private String shouxufeiid;
    /**
    * 自动归集收款账户
    */
    @ApiModelProperty("自动归集收款账户")
    private String guijiid;


}
