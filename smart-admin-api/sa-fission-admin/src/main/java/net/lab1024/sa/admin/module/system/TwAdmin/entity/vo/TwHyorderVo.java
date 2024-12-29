package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

@Data
public class TwHyorderVo extends PageParam {

    private Integer hyzd;    //合约涨跌1买涨2买跌

    private String username;    //用户名称

    private Integer status;  //状态：0.计划中 1待结算2已结算

    private String userCode;    //用户code

    private String orderNo;   //订单号

    private String plantimeStart;   //计划时间

    private String plantimeEnd;   //计划时间

    private String time;
}
