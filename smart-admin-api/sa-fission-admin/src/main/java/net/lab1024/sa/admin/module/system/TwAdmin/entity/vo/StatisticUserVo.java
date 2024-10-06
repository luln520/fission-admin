package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class StatisticUserVo {

    private List<String> dateList;
    private List<Integer> countList;
}
