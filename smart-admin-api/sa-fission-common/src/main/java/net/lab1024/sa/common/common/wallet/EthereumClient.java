package net.lab1024.sa.common.common.wallet;

import lombok.extern.slf4j.Slf4j;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.core.methods.response.EthBlock.*;
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
public class EthereumClient {

    private Web3j web3j;

    public EthereumClient(String rpcUrl){
        this.web3j = Web3j.build(new HttpService(rpcUrl));
    }

    public Block getBlock(int blockNumer) {
        Request<?, EthBlock> blockNumberRequest = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(blockNumer), true);
        try {
            EthBlock ethBlock = blockNumberRequest.send();
            return ethBlock.getBlock();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }

    }

    public Transaction getTransaction(String tx) {
        Request<?, EthTransaction> request = web3j.ethGetTransactionByHash(tx);
        try {
            EthTransaction ethTransaction = request.send();
            return ethTransaction.getResult();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public List<Type> decodeInput(String input) {
        String methodId = input.substring(0, 10);
        String encodedParameters = input.substring(10);
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

    public BigInteger getErc20Balance(String ownerAddress, String contractAddress) {
        Function function = new Function(
                "balanceOf",
                Collections.singletonList(new org.web3j.abi.datatypes.Address(ownerAddress)),
                Collections.singletonList(new TypeReference<Uint256>() {})
        );

        String encodedFunction = FunctionEncoder.encode(function);

        org.web3j.protocol.core.methods.request.Transaction transaction = org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(
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

    public String transferErc20(String privateKey, String toAddress, String contractAddress, BigInteger amount) {
        log.info("current address send to address:{} = {} => {}", toAddress, contractAddress, amount);
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

            BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
            BigInteger gasLimit = BigInteger.valueOf(100000);
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    nonce,
                    gasPrice,
                    gasLimit,
                    contractAddress,
                    encodedFunction
            );

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, 1, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

        } catch (IOException e) {
            throw new RuntimeException("网络异常:" + e.getMessage());
        }

        if (ethSendTransaction.hasError()) {
            throw new RuntimeException("Error sending USDT transaction: " +
                    ethSendTransaction.getError().getMessage());
        }
        return ethSendTransaction.getTransactionHash();
    }


    public String sendFund(String privateKey, String toAddress, BigDecimal bigDecimal) {
        Credentials credentials = Credentials.create(privateKey);

        try {
            // 动态获取 baseFee
            BigInteger baseFee = web3j.ethFeeHistory(
                    1, DefaultBlockParameterName.LATEST, null
            ).send().getFeeHistory().getBaseFeePerGas().get(0);

            // Gas 参数
            BigInteger gasLimit = BigInteger.valueOf(21000); // 简单转账
            BigInteger maxPriorityFeePerGas = BigInteger.valueOf(2).multiply(BigInteger.TEN.pow(9)); // 2 Gwei
            BigInteger maxFeePerGas = baseFee.add(maxPriorityFeePerGas);

            TransactionReceipt transactionReceipt = Transfer.sendFundsEIP1559(
                    web3j, credentials, toAddress,
                    bigDecimal, Convert.Unit.ETHER, gasLimit, maxPriorityFeePerGas, maxFeePerGas).send();

            return transactionReceipt.getTransactionHash();
        } catch (Exception e) {
            throw new RuntimeException("空投eth异常:" + e.getMessage());
        }
    }

    public BigInteger getEthBalance(String address) {
        try {
            EthGetBalance latestBalance = web3j.ethGetBalance(address,
                    DefaultBlockParameterName.LATEST).send();
            log.info("地址{}最新区块余额是: {}", address, latestBalance.getBalance());
            EthGetBalance pendingBalance = web3j.ethGetBalance(address,
                    DefaultBlockParameterName.PENDING).send();
            log.info("地址{}交易池中余额是: {}", address, pendingBalance.getBalance());

            return latestBalance.getBalance().max(pendingBalance.getBalance());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return BigInteger.ZERO;
        }
    }

    public long getNowBlock() {
        try {
            EthBlock.Block block = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send().getBlock();
            return block.getNumber().longValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String transferEth(String privateKey, String address, BigDecimal amount) {
        Credentials credentials = Credentials.create(privateKey);
        EthSendTransaction sendTransactionResponse = null;
        try {
            EthGetTransactionCount transactionCountResponse = web3j.ethGetTransactionCount(
                    credentials.getAddress(),
                    org.web3j.protocol.core.DefaultBlockParameterName.LATEST
            ).send();

            BigInteger nonce = transactionCountResponse.getTransactionCount();
            BigInteger amountInWei = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
            BigInteger gasLimit = BigInteger.valueOf(21000);
            BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();


            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                    nonce, gasPrice, gasLimit, address, amountInWei
            );

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, 1, credentials);
            String signedTransactionData = Numeric.toHexString(signedMessage);


            sendTransactionResponse = web3j.ethSendRawTransaction(signedTransactionData).send();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ("交易失败: " + e.getMessage());
        }
        if (sendTransactionResponse.hasError()) {
            throw new RuntimeException("Error sending ETH transaction: " +
                    sendTransactionResponse.getError().getMessage());
        }
        return sendTransactionResponse.getTransactionHash();
    }
}
