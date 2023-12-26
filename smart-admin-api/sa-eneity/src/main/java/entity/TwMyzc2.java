package entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 提币表(TwMyzc2)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:26:51
 */
@Data
@TableName("tw_myzc2")
public class TwMyzc2 implements Serializable {
    private static final long serialVersionUID = -67751685857778435L;
/**
     * id
     */
    private String id;
/**
     * 会员ID
     */
    private String userid;
/**
     * 会员账号
     */
    private String username;
/**
     * 提币币种
     */
    private String coinname;

    private String txid;
/**
     * 提币数量
     */
    private String num;
/**
     * 手续费
     */
    private String fee;
/**
     * 实际到账
     */
    private String mum;
/**
     * 收款银行户名
     */
    private String receiveName;
/**
     * 收款银行账号
     */
    private String receiveAccount;
/**
     * IFSC号码
     */
    private String remark;

    private String sort;
/**
     * 申请时间
     */
    private Date addtime;

    private Date endtime;
/**
     * 1待审核2完成3未通过4审核中
     */
    private Integer status;
/**
     * 会员转币
     */
    private Integer toUser;
/**
     * 通道名字
     */
    private String td;

}

