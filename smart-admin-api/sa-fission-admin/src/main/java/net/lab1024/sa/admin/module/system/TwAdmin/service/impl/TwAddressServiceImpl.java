package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AddressVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwAddressService;
import net.lab1024.sa.admin.module.system.TwAdmin.service.TwUserCoinService;
import net.lab1024.sa.common.common.wallet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@Slf4j
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
    private TronRpcClient tronRpcClient;

    @Autowired
    private TwCoinDao twCoinDao;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    private TwAddressDetailMapper twAddressDetailMapper;

    @Autowired
    private TwUserCoinService twUserCoinService;

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
    public List<TwAddressDetail> listDetail(AddressVo addressVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("address_id",addressVo.getAddressId());
        List<TwAddressDetail> listpage = this.twAddressDetailMapper.selectList(queryWrapper);
        return listpage;
    }

    @Override
    public String createAddress(int uid, int chainId, int coinId) {
        Resource resource = resourceLoader.getResource("classpath:public.key");
        byte[] keyBytes = new byte[0];
        try (InputStream is = resource.getInputStream()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            keyBytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TwAddress twAddress = baseMapper.findByChainId(uid, chainId, coinId);
        if(twAddress == null) {
            twAddress = new TwAddress();
            //获取助记词
            TwRootAddress twRootAddress = twRootAddressMapper.findByChainId(chainId);
            WalletClient walletClient = new WalletClient(Arrays.asList(twRootAddress.getMnemonic().split(" ")), ChainEnum.getByCode(chainId));
            int step = twRootAddress.getStep() + 1;
            WalletAddress walletAddress = walletClient.generateAddress(step);
            twRootAddressMapper.updateStep(twRootAddress.getId(), step);

            long blockNum = 0;
            if(chainId == ChainEnum.ETH.getCode()) {
                blockNum = ethClient.getNowBlock();
            }else if(chainId == ChainEnum.TRON.getCode()) {
                blockNum = tronClient.getNowBlock();
            }

            twAddress.setUid(uid);
            twAddress.setChainId(chainId);
            twAddress.setAddress(walletAddress.getAddress());
            twAddress.setPublicKey(walletAddress.getPublicKey());
            twAddress.setCoinId(coinId);
            if(blockNum != 0)
                twAddress.setBlockNumber((int) blockNum);

            try{
                twAddress.setPrivateKey(CertificateManager.encrypt(walletAddress.getPrivateKey(), keyBytes));
            }catch (Exception e){
                log.error("加密私钥失败, {}", e);
            }
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
    public void oneKeyCollect(int coinId, byte[] keyBytes) {

        TwCoin twCoin = twCoinDao.selectById(coinId);
        List<TwAddress> twAddressList = this.listBalanceAddress(coinId);
        if(!CollectionUtils.isEmpty(twAddressList)) {
            for(TwAddress twAddress : twAddressList) {

                String privateKey = null;

                try {
                    privateKey = CertificateManager.decrypt(twAddress.getPrivateKey(), keyBytes);
                }catch(Exception e) {
                    log.error("当前账户{}解析私钥失败{}", twAddress.getAddress(), e);
                }

                String amount = twAddress.getBalance().setScale(6, RoundingMode.HALF_UP).toString();
                if(twCoin.getCzline().equals(NetworkConst.ETH)) {
                    TwReceipt twReceipt = new TwReceipt();
                    twReceipt.setUid(twAddress.getUid());
                    twReceipt.setBizStatus(1);
                    twReceipt.setChainId(60);
                    twReceipt.setFromAddress(twAddress.getAddress());
                    twReceipt.setToAddress(twCoin.getCzaddress());
                    try {
                        String txHash = ethClient.transferErc20(privateKey, twCoin.getCzaddress(), TokenUtils.toWei(amount));
                        twReceipt.setTx(txHash);
                    }catch (RuntimeException e) {
                        twReceipt.setCaused(e.getMessage());
                    }
                    twReceipt.setAmount(new BigDecimal(TokenUtils.toWei(amount)));
                    twReceiptMapper.insert(twReceipt);

                    this.updateAddressBalance(twAddress.getAddress());
                }else if(twCoin.getCzline().equals(NetworkConst.TRON)) {
                    tronClient.init(privateKey);

                    TwReceipt twReceipt = new TwReceipt();
                    twReceipt.setUid(twAddress.getUid());
                    twReceipt.setBizStatus(1);
                    twReceipt.setChainId(195);
                    twReceipt.setFromAddress(twAddress.getAddress());
                    twReceipt.setToAddress(twCoin.getCzaddress());
                    try {
                        String txHash = tronClient.transferTrc20(twAddress.getAddress(), twCoin.getCzaddress(), amount);
                        twReceipt.setTx(txHash);
                    }catch (RuntimeException e) {
                        twReceipt.setCaused(e.getMessage());
                    }
                    twReceipt.setAmount(new BigDecimal(amount));
                    twReceiptMapper.insert(twReceipt);

                    this.updateAddressBalance(twAddress.getAddress());
                }

            }
        }
    }

    @Override
    public List<TwAddress> listBalanceAddress(int coinId) {
        return baseMapper.listBalanceAddress(coinId, 100);
    }

    @Override
    public void updateAddressBalance(String address) {
        TwAddress twAddress = baseMapper.findByAddress(address);

        if(twAddress != null) {
            if(twAddress.getChainId() == ChainEnum.ETH.getCode()) {
                BigInteger balance = ethClient.getErc20Balance(twAddress.getAddress());
                twAddressBalanceMapper.updateBalance(TokenUtils.convertUsdtBalance(balance), twAddress.getId());
            }else if(twAddress.getChainId() == ChainEnum.TRON.getCode()) {
                tronClient.init("");
                BigInteger balance = tronClient.getTrc20Balance(twAddress.getAddress());
                twAddressBalanceMapper.updateBalance(TokenUtils.convertUsdtBalance(balance), twAddress.getId());
            }
        }
    }

    @Async
    @Override
    public void transfer(int coinId, String privateKey) {
        TwCoin twCoin = twCoinDao.selectById(coinId);

        List<TwAddress> twAddressList = this.listBalanceAddress(coinId);
        if(!CollectionUtils.isEmpty(twAddressList)) {
            for(TwAddress twAddress : twAddressList) {
                if(twCoin.getCzline().equals(NetworkConst.ETH)) {
                    //默认0.002eth
                    BigDecimal bigDecimal = new BigDecimal("0.002");

                    TwReceipt twReceipt = new TwReceipt();
                    twReceipt.setBizStatus(0);
                    twReceipt.setChainId(60);
                    twReceipt.setFromAddress(twCoin.getFeeaddress());
                    twReceipt.setToAddress(twAddress.getAddress());
                    try {
                        String txHash = ethClient.sendFund(privateKey, twAddress.getAddress(), bigDecimal);
                        twReceipt.setTx(txHash);
                        log.info("接收eth账户为{}, 发送eth的交易Hash为:{}", twAddress.getAddress(), txHash);
                    }catch(RuntimeException e) {
                        twReceipt.setCaused(e.getMessage());
                    }
                    twReceiptMapper.insert(twReceipt);
                }else if(twCoin.getCzline().equals(NetworkConst.TRON)) {
                    tronClient.init(privateKey);
                    //默认50trx

                    TwReceipt twReceipt = new TwReceipt();
                    twReceipt.setBizStatus(0);
                    twReceipt.setChainId(195);
                    twReceipt.setFromAddress(twCoin.getFeeaddress());
                    twReceipt.setToAddress(twAddress.getAddress());

                    try {
                        String ret = tronClient.sendFund(twCoin.getCzaddress(), twAddress.getAddress(), "50");
                        log.info("接收能量账户为{}, 发送trx的交易Hash为:{}", twAddress.getAddress(), ret);
                        twReceipt.setTx(ret);
                    }catch(RuntimeException e) {
                        twReceipt.setCaused(e.getMessage());
                    }
                    twReceiptMapper.insert(twReceipt);
                }
            }
        }
    }

    @Override
    public List<TwAddress> listAddress() {
        return baseMapper.listAddress();
    }

    @Override
    public void checkTransfer(TwAddress twAddress) {
        if(twAddress.getBlockNumber() == null) {
            if(twAddress.getChainId() == ChainEnum.ETH.getCode()) {
                twAddress.setBlockNumber((int) ethClient.getNowBlock());
            }else if(twAddress.getChainId() == ChainEnum.TRON.getCode()) {
                tronClient.init("");
                twAddress.setBlockNumber((int) tronClient.getNowBlock());
            }
            this.baseMapper.updateById(twAddress);
            return;
        }
        int startBlockNumber = twAddress.getBlockNumber();
        BigInteger fromBlock = BigInteger.valueOf(startBlockNumber + 1);
        List<TransferRecord> transferRecordList = Lists.newArrayList();

        if(twAddress.getChainId() == ChainEnum.ETH.getCode()) {
            try {
                transferRecordList = ethClient.queryTransfers(twAddress.getAddress(), fromBlock);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(twAddress.getChainId() == ChainEnum.TRON.getCode()) {
            transferRecordList =  tronRpcClient.queryTransfers(fromBlock, twAddress.getAddress());
        }

        if(!CollectionUtils.isEmpty(transferRecordList)) {
            BigInteger totalAmount = new BigInteger("0");
            for(TransferRecord transferRecord : transferRecordList) {
                TwAddressDetail twAddressDetail = new TwAddressDetail();
                twAddressDetail.setAddressId(twAddress.getId());
                twAddressDetail.setFromAddress(transferRecord.getFrom());
                twAddressDetail.setToAddress(transferRecord.getTo());
                twAddressDetail.setTx(transferRecord.getTransactionHash());
                twAddressDetail.setBlockNumber(transferRecord.getBlockNumber().intValue());
                twAddressDetail.setAmount(TokenUtils.convertUsdtBalance(transferRecord.getValue()));
                twAddressDetailMapper.insert(twAddressDetail);

                totalAmount = totalAmount.add(transferRecord.getValue());
                BigDecimal amount = TokenUtils.convertUsdtBalance(totalAmount);

                twAddress.setBlockNumber(transferRecord.getBlockNumber().intValue());
                this.baseMapper.updateById(twAddress);
                //更新账户
                QueryWrapper<TwUserCoin> queryCoin = new QueryWrapper<>();
                queryCoin.eq("userid", twAddress.getUid());
                TwUserCoin twUserCoin = twUserCoinService.getOne(queryCoin);
                BigDecimal usdt = twUserCoin.getUsdt();
                twUserCoinService.incre(twAddress.getUid(), amount, usdt);
            }
        }
    }

    @Override
    public IPage<TwReceipt> listReceiptPage(AddressVo addressVo) {
        Page<TwReceipt> objectPage = new Page<>(addressVo.getPageNum(), addressVo.getPageSize());
        List<TwReceipt> listpage = this.twReceiptMapper.listpage(objectPage, addressVo);
        objectPage.setRecords(listpage);
        return objectPage;
    }


}
