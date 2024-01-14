package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

@Data
public class TwBillVo extends PageParam {

    private String username;

    private String coinname;

    private Integer st;

    @ApiModelProperty(value = "代理id")
    private Long employeeId;

    @ApiModelProperty(value = "部门id")
    private Long department;
}
