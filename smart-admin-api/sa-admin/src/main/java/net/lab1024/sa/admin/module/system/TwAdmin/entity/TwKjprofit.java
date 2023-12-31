package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 矿机收益表(TwKjprofit)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:24:29
 */

@Data
@TableName("tw_kjprofit")
@ApiModel(value="广告图片表", description="")
public class TwKjprofit implements Serializable {
    private static final long serialVersionUID = -37058945386684550L;
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
     * 矿机ID
     */
    private Integer kid;
/**
     * 矿机名称
     */
    private String ktitle;
/**
     * 收益金额
     */
    private Double num;
/**
     * 收益币种
     */
    private String coin;
/**
     * 收益时间
     */
    private Date addtime;
/**
     * 收益日期
     */
    private Date day;

    @TableField(exist = false) //
    private Integer status;

}

