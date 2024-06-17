package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

@Data
public class CompanyVo extends PageParam {

    @ApiModelProperty("平台名称")
    private String companyName;

    @ApiModelProperty("货币名称")
    private String currency;

}
