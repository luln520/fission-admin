package net.lab1024.sa.admin.module.system.TwAdmin.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (TwAuthGroupAccess)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:19:21
 */
@Data
@TableName("tw_auth_group_access")
public class TwAuthGroupAccess implements Serializable {
    private static final long serialVersionUID = -65797593883299135L;

    private String id;

    private String uid;
/**
     * 用户组id
     */
    private String groupId;


}

