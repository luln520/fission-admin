package net.lab1024.sa.admin.module.system.TwPC.controller.Req;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class UserReq {
     private Integer uid;

     private String username;

     private String password;
}
