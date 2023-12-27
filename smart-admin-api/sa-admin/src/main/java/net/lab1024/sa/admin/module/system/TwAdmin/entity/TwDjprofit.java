package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 数字币冻结记录表(TwDjprofit)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:22:14
 */

@Data
@TableName("tw_djprofit")
public class TwDjprofit implements Serializable {
    private static final long serialVersionUID = 705256414817684774L;
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
    private String username;
/**
     * 冻结额度
     */
    private Double num;
/**
     * 币名称
     */
    private String coin;
/**
     * 状态1冻结中2已释放
     */
    private Integer status;
/**
     * 冻结时间
     */
    private Date addtime;
/**
     * 冻结日期
     */
    private Date addday;
/**
     * 解冻结时间
     */
    private Date thawtime;
/**
     * 解冻日期
     */
    private Date thawday;
/**
     * 冻结说明
     */
    private String remark;

}

