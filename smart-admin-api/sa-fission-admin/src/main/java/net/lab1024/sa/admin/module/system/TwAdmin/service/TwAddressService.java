package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddress;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AddressVo;

import java.util.List;

public interface TwAddressService {
    IPage<TwAddress> listpage(AddressVo addressVo);

    String createAddress(int uid, int chainId);

    void transfer(int coinId, String primaryKey);

    List<TwAddress> listBalance();
}
