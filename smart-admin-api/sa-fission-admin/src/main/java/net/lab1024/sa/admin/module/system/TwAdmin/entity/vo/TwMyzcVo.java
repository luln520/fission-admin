package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

@Data
public class TwMyzcVo extends PageParam {
        private String username;

        private String startTime;

        private String endTime;

        private String userCode;

        private Integer status;

}
