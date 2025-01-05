package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class McdInfoVo {

    private int uid;
    private String name;
    private int followCount;
    private BigDecimal monthProfitRate;
    private BigDecimal monthProfit;
    private BigDecimal totalAmount;
    private BigDecimal profit;
    private BigDecimal minInvest;
    private int days;

    private List<ProfitVo> profitHistory;
    private boolean isFollow;
}
