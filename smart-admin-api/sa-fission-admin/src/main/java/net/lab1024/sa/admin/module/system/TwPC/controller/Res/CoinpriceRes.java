package net.lab1024.sa.admin.module.system.TwPC.controller.Res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinpriceRes {

    @ApiModelProperty("开盘价")
    private BigDecimal open;
    @ApiModelProperty("现价")
    private BigDecimal price;
    @ApiModelProperty("涨跌")
    private BigDecimal lowhig;
    @ApiModelProperty("涨跌幅")
    private BigDecimal change ;
    @ApiModelProperty("code")
    private String code;
    @ApiModelProperty("最高")
    private BigDecimal high;
    @ApiModelProperty("最低")
    private BigDecimal low;
    @ApiModelProperty("量")
    private BigDecimal amount;
}
