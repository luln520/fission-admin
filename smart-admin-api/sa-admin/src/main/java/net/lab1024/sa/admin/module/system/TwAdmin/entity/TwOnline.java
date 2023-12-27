package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (TwOnline)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:27:16
 */
@Data
@TableName("tw_online")
public class TwOnline implements Serializable {
    private static final long serialVersionUID = 550540801395165261L;
/**
     * ID
     */
    private Integer id;
/**
     * 会员ID
     */
    private Integer uid;
/**
     * 会员账号
     */
    private String username;
/**
     * 类型：1客服2会员
     */
    private Integer type;
/**
     * 内容
     */
    private String content;
/**
     * 发送时间
     */
    private Date addtime;
/**
     * 后台查看状态0未查看2已查看
     */
    private Integer state;

}

