package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatisticAmountVo {

    private BigDecimal totalVolume;
    private BigDecimal totalProfit;
    private BigDecimal totalLoss;

}
