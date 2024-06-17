package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

@Data
public class C2CVo extends PageParam {

    @ApiModelProperty("订单ID")
    private String orderNo;

    @ApiModelProperty("用户ID")
    private String userCode;

    @ApiModelProperty("类型:1.充值.2.提现")
    private Integer type;

    @ApiModelProperty("状态 1.待支付 2.审核通过 3.审核拒绝 4.分配卡号 5.取消订单")
    private Integer status;
}
