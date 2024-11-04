package net.lab1024.sa.common.common.wallet;

import lombok.extern.slf4j.Slf4j;
import ognl.Token;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
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
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class EthClient {

    private String contractAddress;
    private Web3j web3j;
    private ContractGasProvider gasProvider;

    public EthClient(String rpcUrl, String contractAddress) {
        this.contractAddress = contractAddress;
        this.web3j = Web3j.build(new HttpService(rpcUrl));
        this.gasProvider = new DefaultGasProvider();
    }

    public BigInteger getErc20Balance(String ownerAddress) {
        Function function = new Function(
                "balanceOf",
                Collections.singletonList(new org.web3j.abi.datatypes.Address(ownerAddress)),
                Collections.singletonList(new TypeReference<Uint256>() {})
        );

        String encodedFunction = FunctionEncoder.encode(function);

        Transaction transaction = Transaction.createEthCallTransaction(
                ownerAddress,
                contractAddress,
                encodedFunction
        );

        EthCall response = null;
        try{
             response = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
        }catch (Exception e) {
            log.error("查询ERC20余额失败，原因:", e);
        }


        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(),
                function.getOutputParameters()
        );

        if (result.size() > 0) {
            return ((Uint256) result.get(0)).getValue();
        }

        return BigInteger.ZERO;
    }

    public String sendFund(String privateKey, String toAddress, BigDecimal bigDecimal) {
        Credentials credentials = Credentials.create(privateKey);

        try {
            TransactionReceipt transactionReceipt = Transfer.sendFunds(
                    web3j, credentials, toAddress,
                    bigDecimal, Convert.Unit.ETHER).send();

            return transactionReceipt.getTransactionHash();
        } catch (Exception e) {
            throw new RuntimeException("空投eth异常:" + e.getMessage());
        }
    }

    public String transferErc20(String privateKey, String toAddress, BigInteger amount) {
        Credentials credentials = Credentials.create(privateKey);

        Function function = new Function(
                "transfer",
                Arrays.asList(
                        new Address(toAddress),
                        new Uint256(amount)
                ),
                Collections.singletonList(new TypeReference<Type>() {})
        );

        String encodedFunction = FunctionEncoder.encode(function);
        EthSendTransaction ethSendTransaction = null;


        try {
            BigInteger nonce = web3j.ethGetTransactionCount(
                    credentials.getAddress(), DefaultBlockParameterName.LATEST
            ).send().getTransactionCount();

            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    nonce,
                    gasProvider.getGasPrice(),
                    gasProvider.getGasLimit(),
                    this.contractAddress,
                    encodedFunction
            );

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

        } catch (IOException e) {
            throw new RuntimeException("网络异常:" + e.getMessage());
        }

        if (ethSendTransaction.hasError()) {
            throw new RuntimeException("Error sending transaction: " +
                    ethSendTransaction.getError().getMessage());
        }
        return ethSendTransaction.getTransactionHash();
    }

    public String transferFrom(String privateKey, String fromAddress, String toAddress, BigInteger amount) {
        Credentials credentials = Credentials.create(privateKey);

        Function function = new Function(
                "transferFrom",
                Arrays.asList(
                        new Address(fromAddress),
                        new Address(toAddress),
                        new Uint256(amount)
                ),
                Collections.singletonList(new TypeReference<Type>() {})
        );

        String encodedFunction = FunctionEncoder.encode(function);
        EthSendTransaction ethSendTransaction = null;

        try {
            BigInteger nonce = web3j.ethGetTransactionCount(
                    credentials.getAddress(), DefaultBlockParameterName.LATEST
            ).send().getTransactionCount();

            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    nonce,
                    gasProvider.getGasPrice(),
                    gasProvider.getGasLimit(),
                    this.contractAddress,
                    encodedFunction
            );

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

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
