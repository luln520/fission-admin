package net.lab1024.sa.common.common.wallet;


import lombok.extern.slf4j.Slf4j;
import org.tron.trident.abi.FunctionReturnDecoder;
import org.tron.trident.abi.TypeReference;
import org.tron.trident.abi.datatypes.Function;
import org.tron.trident.abi.datatypes.Utf8String;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.contract.Contract;
import org.tron.trident.core.contract.Trc20Contract;
import org.tron.trident.core.exceptions.IllegalException;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Chain.Transaction;
import org.tron.trident.proto.Response;
import org.tron.trident.proto.Response.TransactionExtention;
import org.tron.trident.proto.Response.TransactionReturn;
import org.tron.trident.utils.Convert;
import org.tron.trident.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class TronClient {

    private boolean isMainNet;
    private ApiWrapper wrapper;
    private String contractAddress;
    private String apiKey;

    public TronClient(boolean isMainNet, String contractAddress, String apiKey) {
        this.isMainNet = isMainNet;
        this.contractAddress = contractAddress;
        this.apiKey = apiKey;
    }

    public void init(String privateKey) {
        if(isMainNet) {
            wrapper = ApiWrapper.ofMainnet(privateKey, apiKey);
        }else {
            wrapper = ApiWrapper.ofNile(privateKey);
        }
    }

    public BigInteger getTrc20Balance(String ownerAddress) {
        try {
            Contract contract = wrapper.getContract(contractAddress);
            Trc20Contract token = new Trc20Contract(contract, ownerAddress, wrapper);
            return token.balanceOf(ownerAddress);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            return new BigInteger("0");
        } finally {
            wrapper.close();
        }
    }

    public boolean delegateResource(String ownerAddress, String toAddress, int resourceCode, String balance) {
        BigInteger sunAmountValue = Convert.toSun(balance, Convert.Unit.TRX).toBigInteger();
        TransactionExtention te = null;
        try {
            te = wrapper.delegateResource(ownerAddress, sunAmountValue.longValue(), resourceCode, toAddress,false);
        } catch (IllegalException e) {
            e.printStackTrace();
        }

        Transaction signedTxn = wrapper.signTransaction(te);
        TransactionReturn ret = wrapper.blockingStub.broadcastTransaction(signedTxn);

        if(ret.getCode() != TransactionReturn.response_code.SUCCESS){
            log.info("delegate failed, reason is {}", ret.toString());
            throw new RuntimeException("delegate failed, reason is " + ret.toString());
        }
        return ret.getResult();
    }

    public String sendFund(String fromAddress, String toAddress, String amount) {
        TransactionExtention transaction = null;
        try {
            BigInteger sunAmountValue = Convert.toSun(amount, Convert.Unit.TRX).toBigInteger();
            transaction = wrapper.transfer(fromAddress, toAddress, sunAmountValue.longValue());
            Transaction signedTxn = wrapper.signTransaction(transaction);
            String ret = wrapper.broadcastTransaction(signedTxn);
            return ret;
        } catch (Exception e) {
            throw new RuntimeException("send fund failed, reason is " + e.getMessage());
        } finally {
            wrapper.close();
        }
    }

    /**
     *
     * @param toAddress
     * @param amount
     * @return
     */
    public String transferTrc20(String fromAddress, String toAddress, String amount) {
        Contract contract = wrapper.getContract(contractAddress);
        Trc20Contract token = new Trc20Contract(contract, fromAddress, wrapper);
        BigInteger sunAmountValue = Convert.toSun(amount, Convert.Unit.TRX).toBigInteger();
        long maxTrx = Convert.toSun("17", Convert.Unit.TRX).longValue();
        try {
            return token.transfer(toAddress, sunAmountValue.longValue(), 0, "", maxTrx);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            wrapper.close();
        }
    }

    /**
     *
     * @param fromAddress
     * @param toAddress
     * @param amount
     * @return
     */
    public String transferFrom(String fromAddress, String toAddress, String amount) {
        Contract contract = wrapper.getContract(contractAddress);
        Trc20Contract token = new Trc20Contract(contract, toAddress, wrapper);
        BigInteger sunAmountValue = Convert.toSun(amount, Convert.Unit.TRX).toBigInteger();
        long maxTrx = Convert.toSun("17", Convert.Unit.TRX).longValue();
        try {
            return token.transferFrom(fromAddress, toAddress, sunAmountValue.longValue(), 0, "", maxTrx);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            wrapper.close();
        }
        return null;
    }

    public void getEvents(String toAddress, long currentNum) {
        Contract contract = wrapper.getContract(contractAddress);
        Trc20Contract token = new Trc20Contract(contract, toAddress, wrapper);
        try {
            Chain.Block block = wrapper.getNowBlock();
            long latest = block.getBlockHeader().getRawData().getNumber();
            long batchSize = 100;
            for (long currentBlock = currentNum; currentBlock <= latest; currentBlock += batchSize) {
                long endBlock = Math.min(currentBlock + batchSize - 1, latest);
                Response.BlockListExtention blockListExtention = wrapper.getBlockByLimitNext(currentBlock, endBlock + 1);
                List<Response.BlockExtention> blockExtentionList =  blockListExtention.getBlockList();
                for(Response.BlockExtention blockExtention : blockExtentionList) {
                    System.out.println(blockExtention);
                }

            }
        } catch (IllegalException e) {
            e.printStackTrace();
        }
    }

    public long getNowBlock() {
        try {
            Chain.Block block = wrapper.getNowBlock();
            return block.getBlockHeader().getRawData().getNumber();
        } catch (IllegalException e) {
            e.printStackTrace();
        } finally {
            wrapper.close();
        }
        return 0;
    }

    private BigDecimal convertBalance(BigInteger rawBalance, int decimals) {
        return new BigDecimal(rawBalance)
                .divide(BigDecimal.TEN.pow(decimals), decimals, RoundingMode.HALF_DOWN);
    }

    public static void main(String[] args) {
        TronClient tronClient = new TronClient(false, "TXLAQ63Xg1NAzckPwKHvzw7CSEmLMEqcdj", "");
        tronClient.init("8aa9a9215ba4f6f7f2db2bcd260d7a7629a51e1b95710b964b96dc7c8d405fb1");
        tronClient.getEvents("TY44yxJn3BUCuvBCtQcHygdw64kG5VSQ1F", 51601294);
    }
}
