package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwHyorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjprofit;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwHyorderVo;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjprofitVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 合约订单表(TwHyorder)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:23:17
 */
public interface TwHyorderService extends IService<TwHyorder> {

    int countUnClosedOrders();

    TwHyorder hyorderId(int id);

    IPage<TwHyorder> listpage(TwHyorderVo twHyorderVo);

    boolean editKongyK(Integer kongyk, int id);

}
