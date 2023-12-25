package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getKid() {
        return kid;
    }

    public void setKid(Integer kid) {
        this.kid = kid;
    }

    public String getKtitle() {
        return ktitle;
    }

    public void setKtitle(String ktitle) {
        this.ktitle = ktitle;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

}

