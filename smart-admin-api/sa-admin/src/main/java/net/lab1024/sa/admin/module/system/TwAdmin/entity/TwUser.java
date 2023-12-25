package net.lab1024.sa.admin.module.system.TwAdmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户信息表(TwUser)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:28:46
 */
@Data
@TableName("tw_user")
public class TwUser implements Serializable {
    private static final long serialVersionUID = 695574628051017553L;
/**
     * ID
     */
    private String id;
/**
     * 账号(邮箱)
     */
    private String username;
/**
     * 手机号码
     */
    private String phone;
/**
     * 合约体验金
     */
    private Double money;
/**
     * 注册类型 1 手机  2 邮箱
     */
    private Integer type;
/**
     * 登陆密码
     */
    private String password;
/**
     * 支付密码
     */
    private String paypassword;
/**
     * 身份证正面
     */
    private String cardzm;
/**
     * 身份证反面
     */
    private String cardfm;
/**
     * 认证状态0未申请1已提交2已认让3已驳回
     */
    private Integer rzstatus;
/**
     * 待定
     */
    private Integer level;
/**
     * 上一代
     */
    private String invit1;
/**
     * 上二代
     */
    private String invit2;
/**
     * 上三代
     */
    private String invit3;
/**
     * 团队路径
     */
    private String path;
/**
     * 登陆次数
     */
    private String logins;
/**
     * 注册IP
     */
    private String addip;
/**
     * IP区域
     */
    private String addr;
/**
     * 注册时间j
     */
    private String addtime;

    private String endtime;
/**
     * 登陆时间
     */
    private Date lgtime;
/**
     * 登陆IP
     */
    private String loginip;
/**
     * 登陆地址
     */
    private String loginaddr;
/**
     * 登陆时间
     */
    private Date logintime;
/**
     * 认证提交时间
     */
    private String rztime;
/**
     * 认证处理时间
     */
    private String rzuptime;
/**
     * 状态1正常2禁用
     */
    private String status;
/**
     * 提币状态1正常2禁止
     */
    private Integer txstate;
/**
     * 邀请码
     */
    private String invit;
/**
     * 禁止提现时间
     */
    private Integer stoptime;
/**
     * 	0否1是
     */
    private Integer isAgent;
/**
     * 区号
     */
    private String areaCode;

    private String realName;

    private String area;
/**
     *  1 护照 2驾驶证 3SSN 4身份ID
     */
    private String rztype;
/**
     * 1允许交易2禁止交易
     */
    private Integer buyOn;
/**
     * 默认语言
     */
    private String lang;
/**
     * 1=正常用户 2=测试用户
     */
    private Integer userType;
/**
     * 信用分
     */
    private Integer jifen;

}

