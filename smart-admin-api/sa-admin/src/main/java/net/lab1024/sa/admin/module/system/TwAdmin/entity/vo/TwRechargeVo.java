package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

@Data
public class TwRechargeVo extends PageParam {

        private String username;

        private String startTime;

        private String endTime;

}
