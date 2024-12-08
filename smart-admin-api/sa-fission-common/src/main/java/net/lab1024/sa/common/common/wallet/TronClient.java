package net.lab1024.sa.common.common.wallet;

import lombok.extern.slf4j.Slf4j;
import org.tron.trident.abi.FunctionReturnDecoder;
import org.tron.trident.abi.TypeReference;
import org.tron.trident.abi.datatypes.Address;
import org.tron.trident.abi.datatypes.Function;
import org.tron.trident.abi.datatypes.Type;
import org.tron.trident.abi.datatypes.generated.Uint256;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.contract.Trc20Contract;
import org.tron.trident.core.exceptions.IllegalException;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Response;
import org.tron.trident.utils.Convert;


import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
//https://morning-lingering-shadow.tron-mainnet.quiknode.pro/99ebb98e22b9010cd69dc01a261f03ab0f9de875
@Slf4j
public class TronClient {

    private boolean isMainNet;
    private ApiWrapper wrapper;
    private String apiKey;

    public TronClient(boolean isMainNet, String apiKey) {
        this.isMainNet = isMainNet;
        this.apiKey = apiKey;
    }

    public void init(String privateKey) {
        if(isMainNet) {
            wrapper = ApiWrapper.ofMainnet(privateKey, apiKey);
        }else {
            wrapper = ApiWrapper.ofNile(privateKey);
        }
    }

    public Response.BlockExtention getBlock(int blockNumer) {
        try {
            this.init("");
            return wrapper.getBlockByNum(blockNumer);
        } catch (IllegalException e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            wrapper.close();
        }
    }

    public Chain.Transaction getTransaction(String tx) {
        try {
            return wrapper.getTransactionById(tx);
        } catch (IllegalException e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            wrapper.close();
        }
    }

    public List<Type> decodeInput(String input) {
        String methodId = input.substring(0, 10);
        String encodedParameters = input.substring(8);
        Function function = new Function(
                "transfer",
                Arrays.asList(),
                Arrays.asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {})
        );
        List<Type> params = FunctionReturnDecoder.decode(
                encodedParameters,
                function.getOutputParameters()
        );

        return params;
    }

    public String transferTrc20(String fromAddress, String toAddress, String contractAddress, String amount) {
        org.tron.trident.core.contract.Contract contract = wrapper.getContract(contractAddress);
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

    public BigInteger getTrc20Balance(String ownerAddress, String contractAddress) {
        try {
            this.init("");
            org.tron.trident.core.contract.Contract contract = wrapper.getContract(contractAddress);
            Trc20Contract token = new Trc20Contract(contract, ownerAddress, wrapper);
            return token.balanceOf(ownerAddress);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            return new BigInteger("0");
        } finally {
            wrapper.close();
        }
    }

    public String sendFund(String fromAddress, String toAddress, String amount) {
        Response.TransactionExtention transaction = null;
        try {
            BigInteger sunAmountValue = Convert.toSun(amount, Convert.Unit.TRX).toBigInteger();
            transaction = wrapper.transfer(fromAddress, toAddress, sunAmountValue.longValue());
            Chain.Transaction signedTxn = wrapper.signTransaction(transaction);
            String ret = wrapper.broadcastTransaction(signedTxn);
            return ret;
        } catch (Exception e) {
            throw new RuntimeException("send fund failed, reason is " + e.getMessage());
        } finally {
            wrapper.close();
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
}
