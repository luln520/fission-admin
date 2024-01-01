package net.lab1024.sa.admin.module.system.TwPC.controller.Res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AllsymbolRes {
    @ApiModelProperty("id")
    private int id;

    @ApiModelProperty("币种名称")
    private String coinname;

    @ApiModelProperty("现价")
    private BigDecimal close;

    @ApiModelProperty("涨跌幅")
    private BigDecimal change;
}
