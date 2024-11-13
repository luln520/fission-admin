package net.lab1024.sa.admin.module.system.TwAdmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddress;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwAddressDetail;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.TwReceipt;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AddressVo;

import java.math.BigInteger;
import java.util.List;

public interface TwAddressService {
    IPage<TwAddress> listpage(AddressVo addressVo);

    List<TwAddressDetail> listDetail(AddressVo addressVo);

    String createAddress(int uid, int chainId, int coinId);

    void oneKeyCollect(int coinId, byte[] keyBytes);

    List<TwAddress> listBalanceAddress(int coinId);

    void updateAddressBalance(String address);

    void transfer(int coinId, String privateKey);

    List<TwAddress> listAddress();

    void checkTransfer(TwAddress twAddress);

    IPage<TwReceipt> listReceiptPage(AddressVo addressVo);
}
