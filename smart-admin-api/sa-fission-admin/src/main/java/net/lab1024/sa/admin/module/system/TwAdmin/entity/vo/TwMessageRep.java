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

    @ApiModelProperty("客服消息")
    private int onlineCount;

    @ApiModelProperty("C2C卡号分配")
    private int bankCount;

    @ApiModelProperty("C2C消息")
    private int C2CCount;

    @ApiModelProperty("用户消息")
    private int userCount;

    @ApiModelProperty("通知消息")
    private int noticeCount;

    private Long employeeId;

    private Long departmentId;

}
