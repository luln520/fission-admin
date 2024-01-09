package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwCtmarket;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.AllsymbolRes;
import net.lab1024.sa.admin.module.system.TwPC.controller.Res.CoinpriceRes;
import net.lab1024.sa.common.common.domain.PageParam;

import java.util.List;

/**
 * 合约交易对配置(TwCtmarket)表服务接口
 *
 * @author makejava
 * @since 2023-12-23 18:21:46
 */
public interface TwCtmarketService extends IService<TwCtmarket> {

    IPage<TwCtmarket> listpage(PageParam pageParam);

    IPage<TwCtmarket> listPCpage(PageParam pageParam);

    boolean addOrUpdate(TwCtmarket twCtmarket);

    boolean updateStatus(int id, int status);

    boolean delete(int id);

    TwCtmarket find(int id);

    CoinpriceRes getcoinprice(String symbol);

    List<AllsymbolRes> getallsymbol();

}
