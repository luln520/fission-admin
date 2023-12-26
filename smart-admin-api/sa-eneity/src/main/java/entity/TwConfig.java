package entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 网站配置表(TwConfig)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:21:16
 */
@Data
@TableName("tw_config")
public class TwConfig implements Serializable {
    private static final long serialVersionUID = -89299605046836402L;
/**
     * 记录ID
     */
    private Integer id;
/**
     * 网站名称
     */
    private String webname;
/**
     * 网络标题
     */
    private String webtitle;
/**
     * 手机端网站logo
     */
    private String weblogo;
/**
     * PC端网站logo
     */
    private String waplogo;
/**
     * 网站开关1开2关
     */
    private Integer webswitch;
/**
     * 手机端轮播图1
     */
    private String websildea;
/**
     * 手机端轮播图2
     */
    private String websildeb;
/**
     * 手机端轮播图2
     */
    private String websildec;

    private String wapsilded;
/**
     * 手机端新币认购图片
     */
    private String webissue;
/**
     * 手机端矿机首页图片
     */
    private String webkj;
/**
     * PC端轮播图1
     */
    private String wapsildea;
/**
     * PC端轮播图2
     */
    private String wapsildeb;
/**
     * PC端轮播图3
     */
    private String wapsildec;
/**
     * PC端新币认购图片
     */
    private String wapissue;
/**
     * PC端矿机首页图片
     */
    private String wapkj;
/**
     * 手机端推荐页面logo图片
     */
    private String webtjimgs;
/**
     * PC端推荐页面logo图片
     */
    private String waptjimgs;
/**
     * 短信发送邮箱
     */
    private String smsemail;
/**
     * 邮箱授权码
     */
    private String emailcode;
/**
     * smtp服务器地址
     */
    private String smtpdz;
/**
     * 短信验证码模板
     */
    private String smstemple;
/**
     * 推荐页面推广语
     */
    private String tgtext;
/**
     * 官方客服邮箱
     */
    private String gfemail;
/**
     * PC端下方文字
     */
    private String footertext;
/**
     * 注册开关
     */
    private Integer regswitch;
/**
     * 提币开关
     */
    private Integer tbswitch;
/**
     * 注册是赠送体验矿机
     */
    private Integer regjl;
/**
     * 注册赠送的体验金
     */
    private Double tymoney;
/**
     * 不可修改,否则报错
     */
    private String versionkey;
/**
     * 短信提交网关
     */
    private String smsUrl;
/**
     * 短信商户ID
     */
    private String smsId;
/**
     * 短信商户密钥
     */
    private String smsKey;
/**
     * 自动归集起始金额
     */
    private Double startmoney;
/**
     * TRX手续费账户
     */
    private String shouxufeiid;
/**
     * 自动归集收款账户
     */
    private String guijiid;


}

