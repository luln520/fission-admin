package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

@Data
public class TwHyorderVo extends PageParam {

    private Integer hyzd;

    private String username;

    private Integer status;  //为2 的时候就是 平仓体验列表

    private Long employeeId;

    private Long departmentId;
}
