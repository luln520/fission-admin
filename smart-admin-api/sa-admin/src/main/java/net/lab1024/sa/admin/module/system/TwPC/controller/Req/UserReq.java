package net.lab1024.sa.admin.module.system.TwPC.controller.Req;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class UserReq {

     private Integer uid;

     private String username;

     private String password;

     private String phoneEmail;
     //验证码
     private String regcode;

     //邀请码
     private String invit;

     //（注册类型 1 手机  2 邮箱）
     private int type;

     private String language;

     private Integer companyId;

}
