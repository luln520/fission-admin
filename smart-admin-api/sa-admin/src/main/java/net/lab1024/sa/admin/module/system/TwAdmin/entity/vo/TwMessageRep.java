package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TwMessageRep {

    @ApiModelProperty("认证消息")
    private int authCount;

    @ApiModelProperty("充值消息")
    private int rechargeCount;

    @ApiModelProperty("提现消息")
    private int myzcCount;

    private Long employeeId;

    private Long departmentId;

}
