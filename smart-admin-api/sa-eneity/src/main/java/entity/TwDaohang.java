package entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (TwDaohang)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:22:01
 */
@Data
@TableName("tw_daohang")
public class TwDaohang implements Serializable {
    private static final long serialVersionUID = 671502362450027220L;
/**
     * 自增id
     */
    private String id;

    private String lang;
/**
     * 名称
     */
    private String name;
/**
     * 名称
     */
    private String title;
/**
     * url
     */
    private String url;
/**
     * 排序
     */
    private String sort;
/**
     * 添加时间
     */
    private String addtime;
/**
     * 编辑时间
     */
    private String endtime;
/**
     * 状态
     */
    private Integer status;

    private Integer getLogin;

    private Integer access;

}

