package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AddressVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAddressService;
import net.lab1024.sa.common.common.wallet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@Service
public class TwAddressServiceImpl extends ServiceImpl<TwAddressMapper, TwAddress> implements TwAddressService {

    @Autowired
    private TwRootAddressMapper twRootAddressMapper;

    @Autowired
    private TwAddressBalanceMapper twAddressBalanceMapper;

    @Autowired
    private TwReceiptMapper twReceiptMapper;

    @Autowired
    private EthClient ethClient;

    @Autowired
    private TronClient tronClient;

    @Autowired
    private TwCoinDao twCoinDao;

    @Override
    public IPage<TwAddress> listpage(AddressVo addressVo) {
        /*BigInteger amount = new BigInteger("1000000000000000000");
        String txHash = ethClient.transferFrom("c6f090c2278a2de858c7da2860ab0ff868c9f9d0865a27f9d11704fa64face39", "0xfFd3b16bf2E3B25cFB68265f72DfAFab24E94E77", "0x59f87D2D4B4A9c4Be86244b7209C4EbAC75B2EDc", amount);
        System.out.println(txHash);*/

        /*tronClient.init("f73d5995e626d76504ea0f09e86c5cba225d4ddbc5e6c7bccf7be2de5b828c51");
        tronClient.transferFrom("TPEKP6w1RS7zzBWtf5hLdrqcfXmYiC2NKv", "TLgYVgfkxdGN5gHJ9SbjPEv9xg4bHSkfok", "99");*/
        Page<TwAddress> objectPage = new Page<>(addressVo.getPageNum(), addressVo.getPageSize());
        List<TwAddress> listpage = baseMapper.listpage(objectPage, addressVo);
        objectPage.setRecords(listpage);
        return objectPage;
    }

    @Override
    public String createAddress(int uid, int chainId) {
        TwAddress twAddress = baseMapper.findByChainId(uid, chainId);
        if(twAddress == null) {
            twAddress = new TwAddress();
            //获取助记词
            TwRootAddress twRootAddress = twRootAddressMapper.findByChainId(chainId);
            WalletClient walletClient = new WalletClient(Arrays.asList(twRootAddress.getMnemonic().split(" ")), ChainEnum.getByCode(chainId));
            int step = twRootAddress.getStep() + 1;
            WalletAddress walletAddress = walletClient.generateAddress(step);
            twRootAddressMapper.updateStep(twRootAddress.getId(), step);

            twAddress.setUid(uid);
            twAddress.setChainId(chainId);
            twAddress.setAddress(walletAddress.getAddress());
            twAddress.setPublicKey(walletAddress.getPublicKey());
            twAddress.setPrivateKey(walletAddress.getPrivateKey());
            baseMapper.insert(twAddress);

            TwAddressBalance twAddressBalance = new TwAddressBalance();
            twAddressBalance.setAddressId(twAddress.getId());
            twAddressBalance.setCurrency("USDT");
            twAddressBalanceMapper.insert(twAddressBalance);
        }

        return twAddress.getAddress();
    }

    @Async
    @Override
    public void transfer(int coinId, String primaryKey) {
        TwCoin twCoin = twCoinDao.selectById(coinId);
        List<TwAddress> twAddressList = this.listBalance();
        if(!CollectionUtils.isEmpty(twAddressList)) {
            for(TwAddress twAddress : twAddressList) {
                String amount = twAddress.getBalance().setScale(6, RoundingMode.HALF_UP).toString();
                if(twCoin.getCzline().equals("ERC20")) {
                    String txHash = ethClient.transferFrom(primaryKey, twAddress.getAddress(), twCoin.getCzaddress(), TokenUtils.toWei(amount));

                    TwReceipt twReceipt = new TwReceipt();
                    twReceipt.setChainId(60);
                    twReceipt.setFromAddress(twAddress.getAddress());
                    twReceipt.setToAddress(twCoin.getCzaddress());
                    twReceipt.setTx(txHash);
                    twReceiptMapper.insert(twReceipt);
                }else if(twCoin.getCzline().equals("TRC20")) {
                    tronClient.init(primaryKey);
                    String txHash = tronClient.transferFrom(twAddress.getAddress(), twCoin.getCzaddress(), amount);

                    TwReceipt twReceipt = new TwReceipt();
                    twReceipt.setChainId(195);
                    twReceipt.setFromAddress(twAddress.getAddress());
                    twReceipt.setToAddress(twCoin.getCzaddress());
                    twReceipt.setTx(txHash);
                    twReceiptMapper.insert(twReceipt);
                }
            }
        }
    }

    @Override
    public List<TwAddress> listBalance() {
        return baseMapper.listBalance(100);
    }


}
