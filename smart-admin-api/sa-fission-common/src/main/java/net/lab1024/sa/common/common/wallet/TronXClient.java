package net.lab1024.sa.common.common.wallet;

import com.google.protobuf.InvalidProtocolBufferException;
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
import org.tron.trident.core.utils.ByteArray;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Contract;
import org.tron.trident.proto.Response;
import org.tron.trident.utils.Base58Check;
import org.tron.trident.utils.Convert;


import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
//https://morning-lingering-shadow.tron-mainnet.quiknode.pro/99ebb98e22b9010cd69dc01a261f03ab0f9de875
@Slf4j
public class TronXClient {

    private boolean isMainNet;
    private ApiWrapper wrapper;
    private String apiKey;

    public TronXClient(boolean isMainNet, String apiKey) {
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
            return wrapper.getBlockByNum(blockNumer);
        } catch (IllegalException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public Chain.Transaction getTransaction(String tx) {
        try {
            return wrapper.getTransactionById(tx);
        } catch (IllegalException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public List<Type> decodeInput(String input) {
        String methodId = input.substring(0, 10);
        String encodedParameters = input.substring(8);
        Function function = new Function(
                "transfer",
                Arrays.asList(), // 参数类型
                Arrays.asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}) // 返回值类型
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

    public static void main(String[] args) {
        TronXClient tronXClient = new TronXClient(false, "");
        tronXClient.init("");
        Response.BlockExtention blockExtention = tronXClient.getBlock(51867826);//51604628 51867826
        List<Response.TransactionExtention> transactionExtentionList = blockExtention.getTransactionsList();
        for(Response.TransactionExtention transactionExtention : transactionExtentionList) {

            Chain.Transaction transaction = transactionExtention.getTransaction();

            List<Chain.Transaction.Contract> contractList = transaction.getRawData().getContractList();
            for(Chain.Transaction.Contract contract : contractList) {
                //转trx
                if(contract.getType().equals(Chain.Transaction.Contract.ContractType.TransferContract)) {

                    try {
                        Contract.TransferContract transferContract = Contract.TransferContract.parseFrom(contract.getParameter().getValue());
                        String fromAddress = Base58Check.bytesToBase58(transferContract.getOwnerAddress().toByteArray());
                        String toAddress = Base58Check.bytesToBase58(transferContract.getToAddress().toByteArray());
                        long amount = transferContract.getAmount(); // amount in sun (1 TRX = 1,000,000 sun)
                        System.out.println("From: " + fromAddress);
                        System.out.println("To: " + toAddress);
                        System.out.println("Amount: " + amount + " sun");
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }

                if(contract.getType().equals(Chain.Transaction.Contract.ContractType.TriggerSmartContract)) {
                    try {
                        Contract.TriggerSmartContract triggerSmartContract = Contract.TriggerSmartContract.parseFrom(contract.getParameter().getValue());

                        String contractAddress = Base58Check.bytesToBase58(triggerSmartContract.getContractAddress().toByteArray());
                        String ownerAddress = Base58Check.bytesToBase58(triggerSmartContract.getOwnerAddress().toByteArray());
                        String data = Base58Check.bytesToBase58(triggerSmartContract.getData().toByteArray());

                        List<Type> typeList = tronXClient.decodeInput(ByteArray.toHexString(triggerSmartContract.getData().toByteArray()));
                        System.out.println(typeList);
                        System.out.println(ByteArray.toHexString(transactionExtention.getTxid().toByteArray()));
                        System.out.println(typeList.get(0).getValue());
                        System.out.println(new BigInteger(typeList.get(1).getValue().toString()));
                        System.out.println("From: " + contractAddress);
                        System.out.println("From: " + ownerAddress);
                        System.out.println("From: " + data);
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                    }
                }

//                String txId = ByteArray.toHexString(transactionExtention.getTxid().toByteArray());
//                Chain.Transaction tx = tronXClient.getTransaction(txId);
            }
        }
    }
}
