package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class McdUserInfoVo {

    private int starCount;
    private BigDecimal amount;
    private BigDecimal ploss;
    private BigDecimal profitRate;
}
