package entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 公告内容(TwContent)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:21:31
 */
@Data
@TableName("tw_content")
public class TwContent implements Serializable {
    private static final long serialVersionUID = -47261435617866204L;
/**
     * ID
     */
    private Integer id;
/**
     * 标题
     */
    private String title;
/**
     * 公告图片
     */
    private String img;
/**
     * 内容
     */
    private String content;
/**
     * 添加时间
     */
    private Date addtime;
/**
     * 状态1显示2隐藏
     */
    private Integer status;

}

