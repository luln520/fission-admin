package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

@Data
public class TwBillVo extends PageParam {

    private String userName;

    private String coinname;

    private Integer st;
}
