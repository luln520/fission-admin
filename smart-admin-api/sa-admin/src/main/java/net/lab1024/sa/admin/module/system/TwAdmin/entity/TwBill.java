package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
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

    @ApiModelProperty(value = "记录ID")
    private Integer id;

    @ApiModelProperty(value = "会员ID")
    private Integer uid;

    @ApiModelProperty(value = "会员账号")
    private String username;

    @ApiModelProperty(value = "操作金额")
    private BigDecimal num;

    @ApiModelProperty(value = "币名称")
    private String coinname;

    @ApiModelProperty(value = "操作后余额")
    private BigDecimal afternum;

    @ApiModelProperty(value = "1充币2提币3购买合约4出售合约5购买矿机6购机奖励7矿机收益冻结8释放冻结收益9币币交易USDT10币币交易币种11认购扣除12认购增加13一代认购奖励14二代认购奖励15三代认购奖励16提币退回17充币成功")
    private Integer type;

    @ApiModelProperty(value = "操作时间")
    private Date addtime;

    @ApiModelProperty(value = "1增加2减少")
    private Integer st;

    @ApiModelProperty(value = "操作说明")
    private String remark;

    @ApiModelProperty(value = "trc交易id")
    private String trc20id;

}

