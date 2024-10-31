package net.lab1024.sa.common.common.wallet;


import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.contract.Contract;
import org.tron.trident.core.contract.Trc20Contract;
import org.tron.trident.utils.Convert;

import java.math.BigInteger;

public class TronClient {

    private boolean isMainNet;
    private ApiWrapper wrapper;
    private String contractAddress;

    public TronClient(boolean isMainNet, String contractAddress) {
        this.isMainNet = isMainNet;
        this.contractAddress = contractAddress;
    }

    public void init(String privateKey) {
        if(isMainNet) {
            wrapper = ApiWrapper.ofMainnet(privateKey);
        }else {
            wrapper = ApiWrapper.ofNile(privateKey);
        }
    }

    public String transferFrom(String fromAddress, String toAddress, String amount) {
        Contract contract = wrapper.getContract(contractAddress);
        Trc20Contract token = new Trc20Contract(contract, toAddress, wrapper);
        BigInteger sunAmountValue = Convert.toSun(amount, Convert.Unit.TRX).toBigInteger();
        //设置最大手续费
        long maxTrx = Convert.toSun("17", Convert.Unit.TRX).longValue();
        try {
            //String result = token.transfer("TPEKP6w1RS7zzBWtf5hLdrqcfXmYiC2NKv", sunAmountValue.longValue(), 0, "", maxTrx);
            return token.transferFrom(fromAddress, toAddress, sunAmountValue.longValue(), 0, "", maxTrx);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            wrapper.close();
        }
        return null;
    }
}
