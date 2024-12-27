package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FollowVo {

    private int uid;
    private String name;
    private int day;
    private BigDecimal profit;
    private Date date;
}
