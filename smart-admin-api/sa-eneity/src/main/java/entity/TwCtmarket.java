package entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 合约交易对配置(TwCtmarket)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:21:46
 */
@Data
@TableName("tw_ctmarket")
public class TwCtmarket implements Serializable {
    private static final long serialVersionUID = 382879047322962604L;
/**
     * ID
     */
    private String id;

    private String coinname;
/**
     * 市场名称
     */
    private String name;
/**
     * 交易对
     */
    private String symbol;
/**
     * BTC/USDT格式
     */
    private String title;
/**
     * 状态1正常2禁用
     */
    private Integer status;
/**
     * 交易状态1正常2禁止
     */
    private Integer state;
/**
     * 排序
     */
    private Integer sort;
/**
     * 添加时间
     */
    private Date addtime;

    private String logo;

}

