package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class McdInfoVo {

    private int uid;
    private String name;
    private int followCount;
    private BigDecimal profitRate;
    private BigDecimal profit;
    private BigDecimal totalAmount;
}
