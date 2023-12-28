package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 用户信息表(TwUser)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:28:46
 */
public interface TwUserService extends IService<TwUser> {

    Integer countAllUsers();

}
