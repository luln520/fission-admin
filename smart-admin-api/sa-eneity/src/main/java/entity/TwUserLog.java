package entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户记录表(TwUserLog)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:29:10
 */
@Data
@TableName("tw_user_log")
public class TwUserLog implements Serializable {
    private static final long serialVersionUID = 799632603465017419L;

    private String id;

    private String userid;

    private String type;

    private String remark;

    private String addip;

    private String addr;

    private String sort;

    private String addtime;

    private String endtime;

    private Integer status;

}

