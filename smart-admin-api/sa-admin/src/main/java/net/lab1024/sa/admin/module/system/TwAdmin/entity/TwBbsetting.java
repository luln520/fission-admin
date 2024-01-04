package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 币币交易设置(TwBbsetting)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:20:11
 */
@Data
@TableName("tw_bbsetting")
public class TwBbsetting implements Serializable {
    private static final long serialVersionUID = 155043243530210350L;
    /**
     * 记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 开市时间
     */
    private String bbKstime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBbKstime() {
        return bbKstime;
    }

    public void setBbKstime(String bbKstime) {
        this.bbKstime = bbKstime;
    }

}

