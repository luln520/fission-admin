package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 矿机订单表(TwKjorder)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:24:16
 */
public interface TwKjorderService extends IService<TwKjorder> {

    int countAllOrders();
}
