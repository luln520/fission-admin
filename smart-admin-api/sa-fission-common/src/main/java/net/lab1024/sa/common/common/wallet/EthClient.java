package net.lab1024.sa.common.common.wallet;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class EthClient {

    private String contractAddress;
    private Web3j web3j;
    private ContractGasProvider gasProvider;

    public EthClient(String rpcUrl, String contractAddress) {
        this.contractAddress = contractAddress;
        this.web3j = Web3j.build(new HttpService(rpcUrl));
        this.gasProvider = new DefaultGasProvider();
    }

    public String transferFrom(String privateKey, String fromAddress, String toAddress, BigInteger amount) {
        Credentials credentials = Credentials.create(privateKey);

        // 创建transfer函数调用
        Function function = new Function(
                "transferFrom",
                Arrays.asList(
                        new Address(fromAddress),
                        new Address(toAddress),
                        new Uint256(amount)
                ),
                Collections.singletonList(new TypeReference<Type>() {})
        );

        // 编码函数调用数据
        String encodedFunction = FunctionEncoder.encode(function);
        EthSendTransaction ethSendTransaction = null;

        try {
            // 获取nonce
            BigInteger nonce = web3j.ethGetTransactionCount(
                    credentials.getAddress(), DefaultBlockParameterName.LATEST
            ).send().getTransactionCount();

            // 创建原始交易
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    nonce,
                    gasProvider.getGasPrice(),
                    gasProvider.getGasLimit(),
                    this.contractAddress,
                    encodedFunction
            );

            // 签名交易
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            // 发送交易
            ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

            if (ethSendTransaction.hasError()) {
                throw new RuntimeException("Error sending transaction: " +
                        ethSendTransaction.getError().getMessage());
            }
        }catch(IOException e) {
            e.printStackTrace();
        }

        return ethSendTransaction.getTransactionHash();
    }

    public void close() {
        web3j.shutdown();
    }
}
