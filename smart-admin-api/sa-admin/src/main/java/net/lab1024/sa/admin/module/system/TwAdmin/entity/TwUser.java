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
* 用户信息表
* @TableName tw_user
*/
@Data
@TableName("tw_user")
@ApiModel(value="用户信息表", description="")
public class TwUser implements Serializable {

    /**
    * ID
    */
    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
    * 账号(邮箱)
    */
    @ApiModelProperty("账号(邮箱)")
    private String username;
    /**
    * 手机号码
    */
    @ApiModelProperty("手机号码")
    private String phone;
    /**
    * 合约体验金
    */
    @ApiModelProperty("合约体验金")
    private BigDecimal money;
    /**
    * 注册类型 1 手机  2 邮箱
    */
    @ApiModelProperty("注册类型 1 手机  2 邮箱")
    private Integer type;
    /**
    * 登陆密码
    */
    @ApiModelProperty("登陆密码")
    private String password;
    /**
    * 支付密码
    */
    @ApiModelProperty("支付密码")
    private String paypassword;
    /**
    * 身份证正面
    */
    @ApiModelProperty("身份证正面")
    private String cardzm;
    /**
    * 身份证反面
    */
    @ApiModelProperty("身份证反面")
    private String cardfm;
    /**
    * 认证状态0未申请1已提交2已认让3已驳回
    */
    @ApiModelProperty("认证状态0未申请1已提交2已认让3已驳回")
    private Integer rzstatus;
    /**
    * 待定
    */
    @ApiModelProperty("待定")
    private Integer level;
    /**
    * 上一代
    */
    @ApiModelProperty("上一代")
    private String invit1;
    /**
    * 上二代
    */
    @ApiModelProperty("上二代")
    private String invit2;
    /**
    * 上三代
    */
    @ApiModelProperty("上三代")
    private String invit3;
    /**
    * 团队路径
    */
    @ApiModelProperty("团队路径")
    private String path;
    /**
    * 登陆次数
    */
    @ApiModelProperty("登陆次数")
    private Integer logins;
    /**
    * 注册IP
    */
    @ApiModelProperty("注册IP")
    private String addip;
    /**
    * IP区域
    */
    @ApiModelProperty("IP区域")
    private String addr;
    /**
    * 注册时间j
    */
    @ApiModelProperty("注册时间j")
    private Integer addtime;
    /**
    * 
    */
    @ApiModelProperty("")
    private Integer endtime;
    /**
    * 登陆时间
    */
    @ApiModelProperty("登陆时间")
    private Date lgtime;
    /**
    * 登陆IP
    */
    @ApiModelProperty("登陆IP")
    private String loginip;
    /**
    * 登陆地址
    */
    @ApiModelProperty("登陆地址")
    private String loginaddr;
    /**
    * 登陆时间
    */
    @ApiModelProperty("登陆时间")
    private Date logintime;
    /**
    * 认证提交时间
    */
    @ApiModelProperty("认证提交时间")
    private Integer rztime;
    /**
    * 认证处理时间
    */
    @ApiModelProperty("认证处理时间")
    private Integer rzuptime;
    /**
    * 状态1正常2禁用
    */
    @ApiModelProperty("状态1正常2禁用")
    private Integer status;
    /**
    * 提币状态1正常2禁止
    */
    @ApiModelProperty("提币状态1正常2禁止")
    private Integer txstate;
    /**
    * 邀请码
    */
    @ApiModelProperty("邀请码")
    private String invit;
    /**
    * 禁止提现时间
    */
    @ApiModelProperty("禁止提现时间")
    private Integer stoptime;
    /**
    * 	0否1是
    */
    @ApiModelProperty("	0否1是")
    private Integer isAgent;
    /**
    * 区号
    */
    @ApiModelProperty("区号")
    private String areaCode;
    /**
    * 
    */
    @ApiModelProperty("")
    private String realName;
    /**
    * 
    */
    @ApiModelProperty("")
    private String area;
    /**
    *  1 护照 2驾驶证 3SSN 4身份ID
    */
    @ApiModelProperty(" 1 护照 2驾驶证 3SSN 4身份ID")
    private Object rztype;
    /**
    * 1允许交易2禁止交易
    */
    @ApiModelProperty("1允许交易2禁止交易")
    private Integer buyOn;
    /**
    * 默认语言
    */
    @ApiModelProperty("默认语言")
    private String lang;
    /**
    * 1=正常用户 2=测试用户
    */
    @ApiModelProperty("1=正常用户 2=测试用户")
    private Integer userType;
    /**
    * 信用分
    */
    @ApiModelProperty("信用分")
    private Integer jifen;

    @TableField(exist = false)
    private  String token;



}
