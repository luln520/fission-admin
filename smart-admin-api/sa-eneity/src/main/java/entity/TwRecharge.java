package entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 充值记录(TwRecharge)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:27:30
 */
@Data
@TableName("tw_recharge")
public class TwRecharge implements Serializable {
    private static final long serialVersionUID = -63581557459663594L;
/**
     * id
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
     * 币名称
     */
    private String coin;
/**
     * 名称
     */
    private Double num;
/**
     * 添加时间
     */
    private Date addtime;
/**
     * 处理时间
     */
    private Date updatetime;
/**
     * 状态: 待审核 1 - 审核通过 2 - 不通过3
     */
    private Integer status;
/**
     * 付款凭证
     */
    private String payimg;
/**
     * 不通过说明
     */
    private String msg;

    private String czline;
/**
     * 0用户充值1管理员充值
     */
    private Integer atype;
/**
     * 充值地址
     */
    private String address;
/**
     * trc20交易Id
     */
    private String trc20id;

}

