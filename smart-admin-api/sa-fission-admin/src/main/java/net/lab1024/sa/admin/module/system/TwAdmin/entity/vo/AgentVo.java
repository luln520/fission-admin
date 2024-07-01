package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

@Data
public class AgentVo extends PageParam {

    @ApiModelProperty("会员ID")
    private String username;

    @ApiModelProperty("一级代理")
    private String oneName;

    @ApiModelProperty("二级代理")
    private String twoName;

    @ApiModelProperty("三级代理")
    private String threeName;
}
