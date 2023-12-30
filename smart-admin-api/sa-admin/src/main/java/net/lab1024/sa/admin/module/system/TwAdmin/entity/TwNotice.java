package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 通知表(TwNotice)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:27:04
 */
@Data
@TableName("tw_notice")
@ApiModel(value="广告图片表", description="")
public class TwNotice implements Serializable {
    private static final long serialVersionUID = 890157216002230394L;
/**
     * 记录ID
     */
    private Integer id;
/**
     * 会员ID
     */
    private Integer uid;
/**
     * 会员账号
     */
    private String account;
/**
     * 通知标题
     */
    private String title;
/**
     * 通知内容
     */
    private String content;
/**
     * 通知图片 
     */
    private String imgs;
/**
     * 发送时间
     */
    private Date addtime;
/**
     * 1未读2已读
     */
    private Integer status;

}

