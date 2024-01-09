package net.lab1024.sa.admin.module.system.TwPC.controller.Res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TwPCKjprofitVo {

    @ApiModelProperty("托管的USDT")
    public BigDecimal buynum;

    @ApiModelProperty("预计今日收益")
    public BigDecimal todaynum;

    @ApiModelProperty("累计收益")
    public BigDecimal sumnum;

    @ApiModelProperty("託管中的訂單")
    public int count;
}
