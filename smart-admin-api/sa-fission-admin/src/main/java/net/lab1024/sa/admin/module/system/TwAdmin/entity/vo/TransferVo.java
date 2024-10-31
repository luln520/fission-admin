package net.lab1024.sa.admin.module.system.TwAdmin.entity.vo;

import lombok.Data;

@Data
public class TransferVo {
    private Integer id;
    private String privateKey;
    private Integer companyId;
    private String address;
}
