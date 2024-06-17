package net.lab1024.sa.admin.module.system.TwPC.controller.Res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TwUserQianbaoRes {


    @ApiModelProperty("今日利润")
    public BigDecimal todaynum;

    @ApiModelProperty("今日总交金额")
    public BigDecimal sumnum;

    @ApiModelProperty("今日交易笔数")
    public int count;
}
