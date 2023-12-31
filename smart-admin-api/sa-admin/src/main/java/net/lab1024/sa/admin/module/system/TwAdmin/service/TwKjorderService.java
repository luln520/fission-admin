package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKjorder;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwKuangji;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.TwKjorderVo;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 矿机订单表(TwKjorder)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:24:16
 */
public interface TwKjorderService extends IService<TwKjorder> {
    IPage<TwKjorder> listpage(TwKjorderVo twKjorderVo);

    List<TwKjorder> uidList(int uid);
    int countAllOrders();

    boolean open(int  id);
    boolean close(int  id);

    boolean delete(int  id);
}
