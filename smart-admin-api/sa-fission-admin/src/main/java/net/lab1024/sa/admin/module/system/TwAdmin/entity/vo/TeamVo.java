package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;


import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

@Data
public class TeamVo extends PageParam {

   private  String username;

   private int uid;
}
