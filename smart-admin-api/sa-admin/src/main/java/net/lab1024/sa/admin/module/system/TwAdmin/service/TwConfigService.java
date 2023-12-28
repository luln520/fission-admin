package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 网站配置表(TwConfig)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:21:16
 */
public interface TwConfigService extends IService<TwConfig> {

    TwConfig find(int id);

    boolean addOrUpdate(TwConfig twConfig);

}
