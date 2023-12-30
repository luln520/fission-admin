package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 操作日志(TwBill)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:20:24
 */

@Data
@TableName("tw_bill")
@ApiModel(value="操作日志", description="")
public class TwBill implements Serializable {
    private static final long serialVersionUID = -11575939499653388L;
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
     * 操作金额
     */
    private Double num;
/**
     * 币名称
     */
    private String coinname;
/**
     * 操作后余额
     */
    private Double afternum;
/**
     * 1充币2提币3购买合约4出售合约5购买矿机6购机奖励7矿机收益冻结8释放冻结收益9币币交易USDT10币币交易币种11认购扣除12认购增加13一代认购奖励14二代认购奖励15三代认购奖励16提币退回17充币成功
     */
    private Integer type;
/**
     * 操作时间
     */
    private Date addtime;
/**
     * 1增加2减少
     */
    private Integer st;
/**
     * 操作说明
     */
    private String remark;
/**
     * trc交易id
     */
    private String trc20id;

}

