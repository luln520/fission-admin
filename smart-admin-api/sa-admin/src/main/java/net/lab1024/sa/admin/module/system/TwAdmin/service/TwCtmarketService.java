package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 合约交易对配置(TwCtmarket)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:21:46
 */
public interface TwCtmarketService extends IService<TwCtmarket> {

    IPage<TwCtmarket> listpage(PageParam pageParam);

    boolean addOrUpdate(TwCtmarket twCtmarket);

    boolean updateStatus(int id, int status);

    boolean delete(int id);

}
