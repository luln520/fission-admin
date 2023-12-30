package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户钱包表(TwUserQianbao)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:29:25
 */
@Data
@TableName("tw_user_qianbao")
@ApiModel(value="广告图片表", description="")
public class TwUserQianbao implements Serializable {
    private static final long serialVersionUID = -55634165593268324L;
/**
     * ID
     */
    private String id;
/**
     * 会员ID
     */
    private String userid;
/**
     * 会员账号
     */
    private String coinname;
/**
     * 币名称
     */
    private String name;
/**
     * 地址备注
     */
    private String remark;
/**
     * 充值网络
     */
    private String czline;
/**
     * 提币地址
     */
    private String addr;
/**
     * 排序
     */
    private String sort;
/**
     * 添加时间
     */
    private Date addtime;
/**
     * 状态
     */
    private Integer status;

}

