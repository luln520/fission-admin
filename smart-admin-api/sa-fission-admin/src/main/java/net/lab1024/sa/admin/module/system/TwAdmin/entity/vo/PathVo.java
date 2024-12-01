package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PathVo {
    private Long employeeId;
    private String loginName;
    private BigDecimal depositAmount;
    private BigDecimal withdrawAmount;
    private BigDecimal manualDeposit;
    private BigDecimal manualReduce;
    private BigDecimal donateAmount;
    private int userCount;
}
