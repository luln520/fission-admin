package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 合约参数(TwHysetting)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:23:33
 */
@Data
@TableName("tw_hysetting")
@ApiModel(value="广告图片表", description="")
public class TwHysetting implements Serializable {
    private static final long serialVersionUID = -16319306497017161L;
/**
     * ID
     */
    private Integer id;
/**
     * 交易手续费
     */
    private String hySxf;
/**
     * 合约时间组
     */
    private String hyTime;
/**
     * 盈亏比例组
     */
    private String hyYkbl;
/**
     * 投资额度组
     */
    private String hyTzed;
/**
     * 开市时间
     */
    private String hyKstime;
/**
     * 亏损ID组
     */
    private String hyKsid;
/**
     * 盈利ID组
     */
    private String hyYlid;
/**
     * 风控概率组
     */
    private String hyFkgl;
/**
     * 合约最低投资额
     */
    private String hyMin;

}

