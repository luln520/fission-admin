package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 合约订单表(TwHyorder)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:23:17
 */
public interface TwHyorderService extends IService<TwHyorder> {

    int countUnClosedOrders();

}
