package net.lab1024.sa.admin.module.system.TwAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.api.client.util.Maps;
import com.google.common.collect.Lists;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.TwAdmin.dao.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.*;
import net.lab1024.sa.admin.module.system.TwAdmin.entity.vo.AddressVo;
import net.lab1024.sa.admin.module.system.TwAdmin.service.*;
import net.lab1024.sa.common.common.wallet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.tron.trident.core.utils.ByteArray;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Contract;
import org.tron.trident.proto.Response;
import org.tron.trident.utils.Base58Check;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private EthereumClient ethereumClient;

    @Autowired
    private TronXClient tronXClient;

    @Autowired
    private TwCoinDao twCoinDao;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    private TwAddressDetailMapper twAddressDetailMapper;


    @Autowired
    private TwUserCoinService twUserCoinService;

    @Autowired
    private TwNoticeService twNoticeService;

    @Autowired
    private TwBillService twBillService;

    @Autowired
    @Lazy
    private TwUserService twUserService;

    @Autowired
    private TwBlockMapper twBlockMapper;

    @Autowired
    private TwTokenMapper twTokenMapper;

    @Override
    public IPage<TwAddress> listpage(AddressVo addressVo) {
        /*BigInteger amount = new BigInteger("1000000000000000000");
        String txHash = ethClient.transferFrom("c6f090c2278a2de858c7da2860ab0ff868c9f9d0865a27f9d11704fa64face39", "0xfFd3b16bf2E3B25cFB68265f72DfAFab24E94E77", "0x59f87D2D4B4A9c4Be86244b7209C4EbAC75B2EDc", amount);
        System.out.println(txHash);*/

        /*tronClient.init("f73d5995e626d76504ea0f09e86c5cba225d4ddbc5e6c7bccf7be2de5b828c51");
        tronClient.transferFrom("TPEKP6w1RS7zzBWtf5hLdrqcfXmYiC2NKv", "TLgYVgfkxdGN5gHJ9SbjPEv9xg4bHSkfok", "99");*/
        Page<TwAddress> objectPage = new Page<>(addressVo.getPageNum(), addressVo.getPageSize());
        int total = baseMapper.totalCount(addressVo);
        objectPage.setTotal(total);

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

        QueryWrapper<TwUser> query = new QueryWrapper<>();
        query.eq("id", uid);
        TwUser one = twUserService.getOne(query);

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
        synchronized (this) {
            if (twAddress == null) {
                List<TwAddress> twAddressList = baseMapper.listIdleAddress(chainId, coinId);
                if(!CollectionUtils.isEmpty(twAddressList) && twAddressList.size() > 5) {
                    twAddress = twAddressList.get(0);
                    int sourceUid = twAddress.getUid();
                    twAddress.setUid(uid);
                    twAddress.setCompanyId(one.getCompanyId());
                    twAddress.setPath(one.getPath());
                    twAddress.setUpdateTime(new Date());
                    baseMapper.updateById(twAddress);
                    log.info(">>>>> 重新分配地址完成，原用户ID:{}, 新用户ID{}", sourceUid, uid);
                }else {
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
                    twAddress.setCompanyId(one.getCompanyId());
                    twAddress.setPath(one.getPath());
                    twAddress.setCoinId(coinId);

                    try {
                        twAddress.setPrivateKey(CertificateManager.encrypt(walletAddress.getPrivateKey(), keyBytes));
                    } catch (Exception e) {
                        log.error("加密私钥失败, {}", e);
                    }

                    try {
                        baseMapper.insert(twAddress);

                        TwAddressBalance twAddressBalance = new TwAddressBalance();
                        twAddressBalance.setCompanyId(one.getCompanyId());
                        twAddressBalance.setPath(one.getPath());
                        twAddressBalance.setAddressId(twAddress.getId());
                        twAddressBalance.setCurrency("USDT");
                        twAddressBalanceMapper.insert(twAddressBalance);
                    } catch (DuplicateKeyException e) {
                        twAddress = baseMapper.findByChainId(uid, chainId, coinId);
                    }
                }
            }
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
                    TwToken twToken = twTokenMapper.findByChainId(ChainEnum.ETH.getCode());
                    TwReceipt twReceipt = new TwReceipt();
                    twReceipt.setUid(twAddress.getUid());
                    twReceipt.setBizStatus(1);
                    twReceipt.setChainId(60);
                    twReceipt.setFromAddress(twAddress.getAddress());
                    twReceipt.setToAddress(twCoin.getCzaddress());
                    try {
                        String txHash = ethereumClient.transferErc20(privateKey, twCoin.getCzaddress(), twToken.getAddress(), TokenUtils.toUSDTWei(twAddress.getBalance()));
                        twReceipt.setTx(txHash);
                    }catch (RuntimeException e) {
                        twReceipt.setCaused(e.getMessage());
                    }
                    twReceipt.setAmount(new BigDecimal(TokenUtils.toWei(amount)));
                    twReceiptMapper.insert(twReceipt);

                    this.updateAddressBalance(twAddress.getAddress(), twToken.getAddress());
                }else if(twCoin.getCzline().equals(NetworkConst.TRON)) {
                    TwToken twToken = twTokenMapper.findByChainId(ChainEnum.TRON.getCode());
                    tronXClient.init(privateKey);

                    TwReceipt twReceipt = new TwReceipt();
                    twReceipt.setUid(twAddress.getUid());
                    twReceipt.setBizStatus(1);
                    twReceipt.setChainId(195);
                    twReceipt.setFromAddress(twAddress.getAddress());
                    twReceipt.setToAddress(twCoin.getCzaddress());
                    try {
                        String txHash = tronXClient.transferTrc20(twAddress.getAddress(), twCoin.getCzaddress(), twToken.getAddress(), amount);
                        twReceipt.setTx(txHash);
                    }catch (RuntimeException e) {
                        twReceipt.setCaused(e.getMessage());
                    }
                    twReceipt.setAmount(new BigDecimal(amount));
                    twReceiptMapper.insert(twReceipt);

                    this.updateAddressBalance(twAddress.getAddress(), twToken.getAddress());
                }

            }
        }
    }

    @Override
    public List<TwAddress> listBalanceAddress(int coinId) {
        return baseMapper.listBalanceAddress(coinId, 100);
    }

    @Override
    public void updateAddressBalance(String address, String contractAddress) {
        TwAddress twAddress = baseMapper.findByAddress(address);

        if(twAddress != null) {
            if(twAddress.getChainId() == ChainEnum.ETH.getCode()) {
                try {
                    BigInteger balance = ethereumClient.getErc20Balance(twAddress.getAddress(), contractAddress);
                    twAddressBalanceMapper.updateBalance(TokenUtils.convertUsdtBalance(balance), twAddress.getId());
                    log.info("更新eth账户余额成功,地址是=>{}", twAddress.getAddress());
                }catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }
            }else if(twAddress.getChainId() == ChainEnum.TRON.getCode()) {
                TwToken twToken = twTokenMapper.findByChainId(ChainEnum.TRON.getCode());
                BigInteger balance = tronXClient.getTrc20Balance(twAddress.getAddress(), twToken.getAddress());
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
                        String txHash = ethereumClient.sendFund(privateKey, twAddress.getAddress(), bigDecimal);
                        twReceipt.setTx(txHash);
                        log.info("接收eth账户为{}, 发送eth的交易Hash为:{}", twAddress.getAddress(), txHash);
                    }catch(RuntimeException e) {
                        twReceipt.setCaused(e.getMessage());
                    }
                    twReceiptMapper.insert(twReceipt);
                }else if(twCoin.getCzline().equals(NetworkConst.TRON)) {
                    tronXClient.init(privateKey);
                    //默认50trx

                    TwReceipt twReceipt = new TwReceipt();
                    twReceipt.setBizStatus(0);
                    twReceipt.setChainId(195);
                    twReceipt.setFromAddress(twCoin.getFeeaddress());
                    twReceipt.setToAddress(twAddress.getAddress());

                    try {
                        String ret = tronXClient.sendFund(twCoin.getCzaddress(), twAddress.getAddress(), "50");
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

   /* @Override
    public void checkTransfer(TwAddress dbTwAddress) {
        TwAddress twAddress = this.baseMapper.findById(dbTwAddress.getId());

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
        int currentBlockNumber = twAddress.getBlockNumber();
        if(twAddress.getChainId() == ChainEnum.ETH.getCode()) {
            currentBlockNumber = (int)ethClient.getNowBlock();
        }else {
            tronClient.init("");
            currentBlockNumber =  (int)tronClient.getNowBlock();
        }
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

        QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", twAddress.getUid());
        TwUser twUser = twUserService.getOne(queryWrapper1);

        if(!CollectionUtils.isEmpty(transferRecordList)) {
            BigInteger totalAmount = new BigInteger("0");
            for(TransferRecord transferRecord : transferRecordList) {
                TwAddressDetail twAddressDetail = new TwAddressDetail();
                twAddressDetail.setAddressId(twAddress.getId());
                twAddressDetail.setFromAddress(transferRecord.getFrom());
                twAddressDetail.setToAddress(transferRecord.getTo());
                twAddressDetail.setTx(transferRecord.getTransactionHash());
                twAddressDetail.setCompanyId(twUser.getCompanyId());
                twAddressDetail.setPath(twUser.getPath());
                twAddressDetail.setBlockNumber(transferRecord.getBlockNumber().intValue());
                twAddressDetail.setAmount(TokenUtils.convertUsdtBalance(transferRecord.getValue()));
                twAddressDetailMapper.insert(twAddressDetail);

                totalAmount = totalAmount.add(transferRecord.getValue());
                BigDecimal amount = TokenUtils.convertUsdtBalance(totalAmount);
                //更新账户
                QueryWrapper<TwUserCoin> queryCoin = new QueryWrapper<>();
                queryCoin.eq("userid", twAddress.getUid());
                TwUserCoin twUserCoin = twUserCoinService.getOne(queryCoin);
                BigDecimal usdt = twUserCoin.getUsdt();
                twUserCoinService.incre(twAddress.getUid(), amount, usdt);

                TwBill twBill = new TwBill();
                twBill.setUid( twAddress.getUid());
                twBill.setUsername(twUser.getUsername());
                twBill.setNum(amount);
                twBill.setCompanyId(twUser.getCompanyId());
                twBill.setCoinname("USDT");
                twBill.setAfternum(twUserCoinService.afternum(twAddress.getUid()));
                twBill.setType(17);
                twBill.setAddtime(new Date());
                twBill.setSt(1);
                twBill.setRemark("充币到账");
                twBill.setDepartment(twUser.getDepatmentId());
                twBill.setPath(twUser.getPath());
                twBillService.save(twBill);

                TwNotice twNotice = new TwNotice();
                twNotice.setUid(twAddress.getUid());
                twNotice.setAccount(twUser.getUsername());
                twNotice.setTitle("充币审核");
                twNotice.setTitleEn("Deposit review");
                twNotice.setContent("您的充值金额已到账，请注意查收");
                twNotice.setContentEn("Your recharge amount has arrived, please check it carefully");
                twNotice.setAddtime(new Date());
                twNotice.setStatus(1);
                twNotice.setCompanyId(twUser.getCompanyId());
                twNotice.setDepartment(twUser.getDepatmentId());
                twNotice.setPath(twUser.getPath());
                twNoticeService.save(twNotice);

                try {
                    twUser.setCodeAmount(amount.add(twUser.getCodeAmount()));
                    twUserService.updateById(twUser);
                }catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        this.baseMapper.updateAddressBlock(twAddress.getId(), currentBlockNumber);

    }*/

    @Override
    public IPage<TwReceipt> listReceiptPage(AddressVo addressVo) {
        Page<TwReceipt> objectPage = new Page<>(addressVo.getPageNum(), addressVo.getPageSize());
        List<TwReceipt> listpage = this.twReceiptMapper.listpage(objectPage, addressVo);
        objectPage.setRecords(listpage);
        return objectPage;
    }

    @Override
    public List<TwAddressDetail> listRecharge(int uid) {
        return this.twAddressDetailMapper.listAddressDetail(uid);
    }

    @Override
    public String getAddress(int uid, int coinId) {
        TwCoin twCoin = twCoinDao.selectById(coinId);
        switch (twCoin.getCzline()) {
            case NetworkConst.ETH:
                return createAddress(uid, ChainEnum.ETH.getCode(), twCoin.getId());
            case NetworkConst.TRON:
                return createAddress(uid, ChainEnum.TRON.getCode(), twCoin.getId());
            default:
                return createAddress(uid, ChainEnum.BTC.getCode(), twCoin.getId());
        }
    }

    @Override
    public int monitorEthTransfer() {
        TwBlock twBlock = twBlockMapper.findByChainId(ChainEnum.ETH.getCode());
        TwToken twToken = twTokenMapper.findByChainId(ChainEnum.ETH.getCode());
        if(twToken == null || twBlock == null)  return 0;

        int blockNumber = twBlock.getNumber();
        EthBlock.Block block = ethereumClient.getBlock(blockNumber + 1);
        if(block == null) return 0;

        List<TransferRecord> transferEthList = Lists.newArrayList();
        List<TransferRecord> transferErcList = Lists.newArrayList();

        List<EthBlock.TransactionResult> transactionResultList = block.getTransactions();
        for(EthBlock.TransactionResult transactionResult : transactionResultList) {
            EthBlock.TransactionObject transactionObject = (EthBlock.TransactionObject) transactionResult.get();
            if(transactionObject.getTo() == null) continue;

            if(twToken.getAddress().equals(transactionObject.getTo().toLowerCase())) {
                TransferRecord transferRecord = new TransferRecord();
                transferRecord.setBlockNumber(transactionObject.getBlockNumber().intValue());
                transferRecord.setTransactionHash(transactionObject.getHash());
                transferRecord.setFrom(transactionObject.getFrom());
                transferRecord.setTo(transactionObject.getTo());
                transferRecord.setInput(transactionObject.getInput());
                transferRecord.setContract(twToken.getAddress());
                transferErcList.add(transferRecord);
            }else {
                //这里有两种：直接转币 + 其它ERC20转币
                TransferRecord transferRecord = new TransferRecord();
                transferRecord.setBlockNumber(transactionObject.getBlockNumber().intValue());
                transferRecord.setTransactionHash(transactionObject.getHash());
                transferRecord.setFrom(transactionObject.getFrom());
                transferRecord.setTo(transactionObject.getTo());
                transferRecord.setValue(transactionObject.getValue());
                transferRecord.setContract(twToken.getAddress());
                transferEthList.add(transferRecord);
            }
        }
        handleEthTransfer(transferEthList);
        handleERCTransfer(transferErcList);

        twBlock.setNumber(twBlock.getNumber() + 1);
        twBlockMapper.updateById(twBlock);
        return 0;
    }

    private int handleEthTransfer(List<TransferRecord> transferEthList) {
        if(CollectionUtils.isEmpty(transferEthList)) return 0;

        List<String> addressList = transferEthList.stream().map(TransferRecord::getTo).collect(Collectors.toList());
        List<TwAddress> twAddressList = this.baseMapper.selectByAddresses(addressList);

        if(CollectionUtils.isEmpty(twAddressList)) return 0;
        List<String> inAddressList = twAddressList.stream().map(TwAddress::getAddress).collect(Collectors.toList());

        int total = 0;
        for(TransferRecord transferRecord : transferEthList) {
            //Transaction transaction = ethereumClient.getTransaction(tx);
            if(inAddressList.contains(transferRecord.getTo())) {
                TwAddress twAddress = this.baseMapper.findByAddress(transferRecord.getTo());

                TwAddressDetail twAddressDetail = new TwAddressDetail();
                twAddressDetail.setAddressId(twAddress.getId());
                twAddressDetail.setFromAddress(transferRecord.getFrom());
                twAddressDetail.setToAddress(transferRecord.getTo());
                twAddressDetail.setTx(transferRecord.getTransactionHash());
                //            twAddressDetail.setCompanyId(twUser.getCompanyId());
                //            twAddressDetail.setPath(twUser.getPath());
                twAddressDetail.setBlockNumber(transferRecord.getBlockNumber());
                twAddressDetail.setAmount(TokenUtils.convertUsdtBalance(transferRecord.getValue()));
                twAddressDetailMapper.insert(twAddressDetail);

                //todo 更新账户余额
                BigInteger balance = ethereumClient.getErc20Balance(twAddress.getAddress(), "");
                twAddressBalanceMapper.updateBalance(TokenUtils.convertUsdtBalance(balance), twAddress.getId());
                total++;
            }
        }
        return total;
    }

    private int handleERCTransfer(List<TransferRecord> transferErcList) {
        if(CollectionUtils.isEmpty(transferErcList)) return 0;

//        transferErcList.stream().
//        List<String> addressList = transferErcList.stream().map(TransferRecord::getTo).collect(Collectors.toList());
        List<TwAddress> twAddressList =null;

        if(CollectionUtils.isEmpty(twAddressList)) return 0;
        List<String> inAddressList = twAddressList.stream().map(TwAddress::getAddress).collect(Collectors.toList());

        int total = 0;

        for(TransferRecord transferRecord : transferErcList) {
            Transaction transaction = ethereumClient.getTransaction(transferRecord.getTransactionHash());
            List<Type> params = ethereumClient.decodeInput(transaction.getInput());
            Address toAddress = (Address) params.get(0);
            Uint amount = (Uint) params.get(1);

            TwAddress twAddress = this.baseMapper.findByAddress(toAddress.getValue());

            TwAddressDetail twAddressDetail = new TwAddressDetail();
            twAddressDetail.setAddressId(twAddress.getId());
            twAddressDetail.setFromAddress(transaction.getFrom());
            twAddressDetail.setToAddress(toAddress.getValue());
            twAddressDetail.setTx(transferRecord.getTransactionHash());
//            twAddressDetail.setCompanyId(twUser.getCompanyId());
//            twAddressDetail.setPath(twUser.getPath());
            twAddressDetail.setBlockNumber(transaction.getBlockNumber().intValue());
            twAddressDetail.setAmount(TokenUtils.convertUsdtBalance(amount.getValue()));
            twAddressDetailMapper.insert(twAddressDetail);

            BigInteger balance = ethereumClient.getErc20Balance(twAddress.getAddress(), "contractAddress");
            twAddressBalanceMapper.updateBalance(TokenUtils.convertUsdtBalance(balance), twAddress.getId());
        }
        return total;
    }

    @Override
    public int monitorTronTransfer() {
        TwBlock twBlock = twBlockMapper.findByChainId(ChainEnum.TRON.getCode());
        TwToken twToken = twTokenMapper.findByChainId(ChainEnum.TRON.getCode());
        if(twToken == null || twBlock == null)  return 0;

        int blockNumber = twBlock.getNumber();
        Response.BlockExtention blockExtention = tronXClient.getBlock(blockNumber + 1);
        if(blockExtention == null) return 0;

        List<TransferRecord> transferRecordList = Lists.newArrayList();
        List<Response.TransactionExtention> transactionExtentionList = blockExtention.getTransactionsList();
        for(Response.TransactionExtention transactionExtention : transactionExtentionList) {
            Chain.Transaction transaction = transactionExtention.getTransaction();
            List<Chain.Transaction.Contract> contractList = transaction.getRawData().getContractList();

            for(Chain.Transaction.Contract contract : contractList) {
                if(contract.getType().equals(Chain.Transaction.Contract.ContractType.TriggerSmartContract)) {

                    try {
                        Contract.TriggerSmartContract triggerSmartContract = Contract.TriggerSmartContract.parseFrom(contract.getParameter().getValue());

                        String contractAddress = Base58Check.bytesToBase58(triggerSmartContract.getContractAddress().toByteArray());
                        if(twToken.getAddress().equals(contractAddress)) {
                            String ownerAddress = Base58Check.bytesToBase58(triggerSmartContract.getOwnerAddress().toByteArray());

                            List<org.tron.trident.abi.datatypes.Type> typeList = tronXClient.decodeInput(ByteArray.toHexString(triggerSmartContract.getData().toByteArray()));
                            TransferRecord transferRecord = new TransferRecord();
                            transferRecord.setBlockNumber(blockNumber + 1);
                            transferRecord.setTransactionHash(ByteArray.toHexString(transactionExtention.getTxid().toByteArray()));
                            transferRecord.setFrom(ownerAddress);
                            transferRecord.setTo(typeList.get(0).toString());
                            transferRecord.setValue(new BigInteger(typeList.get(1).getValue().toString()));
                            transferRecord.setContract(twToken.getAddress());
                            transferRecordList.add(transferRecord);
                        }
                    } catch (InvalidProtocolBufferException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }

        return handleTRCTransfer(transferRecordList);
    }

    private int handleTRCTransfer(List<TransferRecord> transferRecordList) {
        if(CollectionUtils.isEmpty(transferRecordList)) return 0;

        List<String> addressList = transferRecordList.stream().map(TransferRecord::getTo).collect(Collectors.toList());
        List<TwAddress> twAddressList = this.baseMapper.selectByAddresses(addressList);

        if(CollectionUtils.isEmpty(twAddressList)) return 0;
        List<String> inAddressList = twAddressList.stream().map(TwAddress::getAddress).collect(Collectors.toList());
        Map<String, Integer> map = twAddressList.stream()
                .collect(Collectors.toMap(TwAddress::getAddress, TwAddress::getUid));

        int total = 0;
        for(TransferRecord transferRecord : transferRecordList) {
            if(inAddressList.contains(transferRecord.getTo())) {

                TwAddressDetail twAddressDetail = new TwAddressDetail();
                TwAddress twAddress = this.baseMapper.findByAddress(transferRecord.getTo());
                twAddressDetail.setAddressId(twAddress.getId());
                twAddressDetail.setFromAddress(transferRecord.getFrom());
                twAddressDetail.setToAddress(transferRecord.getTo());
                twAddressDetail.setTx(transferRecord.getTransactionHash());
//            twAddressDetail.setCompanyId(twUser.getCompanyId());
//            twAddressDetail.setPath(twUser.getPath());
                twAddressDetail.setBlockNumber(transferRecord.getBlockNumber());
                twAddressDetail.setAmount(TokenUtils.convertUsdtBalance(transferRecord.getValue()));
                twAddressDetailMapper.insert(twAddressDetail);

                BigInteger balance = tronXClient.getTrc20Balance(twAddress.getAddress(), transferRecord.getContract());
                twAddressBalanceMapper.updateBalance(TokenUtils.convertUsdtBalance(balance), twAddress.getId());
                //todo 放开 updateUserBalance(map.get(transferRecord.getTo()), TokenUtils.convertUsdtBalance(transferRecord.getValue()));
                total++;
            }

        }
        return total;
    }

    private void updateUserBalance(int uid, BigDecimal amount) {
        QueryWrapper<TwUser> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", uid);
        TwUser twUser = twUserService.getOne(queryWrapper1);

        //更新账户
        QueryWrapper<TwUserCoin> queryCoin = new QueryWrapper<>();
        queryCoin.eq("userid", uid);
        TwUserCoin twUserCoin = twUserCoinService.getOne(queryCoin);
        BigDecimal usdt = twUserCoin.getUsdt();
        twUserCoinService.incre(uid, amount, usdt);

        TwBill twBill = new TwBill();
        twBill.setUid( uid);
        twBill.setUsername(twUser.getUsername());
        twBill.setNum(amount);
        twBill.setCompanyId(twUser.getCompanyId());
        twBill.setCoinname("USDT");
        twBill.setAfternum(twUserCoinService.afternum(uid));
        twBill.setType(17);
        twBill.setAddtime(new Date());
        twBill.setSt(1);
        twBill.setRemark("充币到账");
        twBill.setDepartment(twUser.getDepatmentId());
        twBill.setPath(twUser.getPath());
        twBillService.save(twBill);

        TwNotice twNotice = new TwNotice();
        twNotice.setUid(uid);
        twNotice.setAccount(twUser.getUsername());
        twNotice.setTitle("充币审核");
        twNotice.setTitleEn("Deposit review");
        twNotice.setContent("您的充值金额已到账，请注意查收");
        twNotice.setContentEn("Your recharge amount has arrived, please check it carefully");
        twNotice.setAddtime(new Date());
        twNotice.setStatus(1);
        twNotice.setCompanyId(twUser.getCompanyId());
        twNotice.setDepartment(twUser.getDepatmentId());
        twNotice.setPath(twUser.getPath());
        twNoticeService.save(twNotice);

        try {
            twUser.setCodeAmount(amount.add(twUser.getCodeAmount()));
            twUserService.updateById(twUser);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
