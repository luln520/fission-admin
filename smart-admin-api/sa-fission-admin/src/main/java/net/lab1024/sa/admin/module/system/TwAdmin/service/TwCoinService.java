package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoin;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCoinJson;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwContent;
import net.lab1024.sa.common.common.domain.PageParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 币种配置表(TwCoin)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:20:37
 */
public interface TwCoinService extends IService<TwCoin> {

    IPage<TwCoin> listpage(PageParam pageParam);

    List<TwCoin> lists( int companyId, HttpServletRequest request);

    boolean addOrUpdate(TwCoin twCoin, HttpServletRequest request);

    boolean updateStatus(int id, Integer status);

    boolean delete(int id);

    TwCoin find(int id);

}
