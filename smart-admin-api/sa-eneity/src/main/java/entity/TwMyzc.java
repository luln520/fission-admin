package entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 提币表(TwMyzc)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:26:37
 */
@Data
@TableName("tw_myzc")
public class TwMyzc implements Serializable {
    private static final long serialVersionUID = -34653865457376187L;
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
     * 提币地址
     */
    private String address;

    private String sort;
/**
     * 申请时间
     */
    private Date addtime;

    private Date endtime;
/**
     * 1待审核2完成3未通过
     */
    private Integer status;
/**
     * 会员转币
     */
    private Integer toUser;

    private String czline;

}

