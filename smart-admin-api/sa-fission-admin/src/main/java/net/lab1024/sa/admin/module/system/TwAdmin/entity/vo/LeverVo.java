package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

@Data
public class LeverVo extends PageParam {

     @ApiModelProperty("币种")
     private String symbol;

     @ApiModelProperty("用户名")
     private String username;

     @ApiModelProperty("用户id")
     private int uid;

     private String userCode;
}



