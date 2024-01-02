package net.lab1024.sa.admin.module.system.TwPC.controller.Res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HyorderOneRes {

    @ApiModelProperty("描述")
    private String statusstr;
    private int code;
    @ApiModelProperty("剩余秒数")
    private int timeResidue;
    @ApiModelProperty("现价")
    private BigDecimal timerNewprice;
    @ApiModelProperty("不知道")
    private int clear;
    @ApiModelProperty("盈亏金额")
    private BigDecimal ploss;
    @ApiModelProperty("买涨跌")
    private String timerType;
    @ApiModelProperty("建仓单价")
    private BigDecimal buyprice;
    @ApiModelProperty("结算分钟数")
    private int time;
    @ApiModelProperty("投资金额")
    private BigDecimal num;
    @ApiModelProperty("合约涨跌1买涨2买跌")
    private Integer hyzd;
}
