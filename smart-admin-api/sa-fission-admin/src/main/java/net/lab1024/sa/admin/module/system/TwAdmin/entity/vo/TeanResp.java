package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUserAgent;

import java.util.List;

@Data
public class TeanResp {

    @ApiModelProperty("团队总人数")
    private int numCount;

    @ApiModelProperty("一级团队成员")
    private List<TwUserAgent> oneTeam;

    @ApiModelProperty("二级团队成员")
    private List<TwUserAgent> TwoTeam;

    @ApiModelProperty("三级团队成员")
    private List<TwUserAgent> ThreeTeam;


}
