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

            ContractGasProvider gasProvider = new DefaultGasProvider();
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    nonce,
                    gasProvider.getGasPrice(),
                    gasProvider.getGasLimit(),
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
            throw new RuntimeException("Error sending transaction: " +
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

    public static void main(String[] args) {
        EthereumClient ethereumClient = new EthereumClient("https://dimensional-solemn-uranium.ethereum-sepolia.quiknode.pro/d711bd4865a6f0b9a31b5115d3abf40ce280070a");
        Block block = ethereumClient.getBlock(7202266);
        List<EthBlock.TransactionResult> transactionResultList = block.getTransactions();
        for(EthBlock.TransactionResult transactionResult : transactionResultList) {
            EthBlock.TransactionObject transactionObject = (EthBlock.TransactionObject) transactionResult.get();

            //todo to地址为null 创建合约
            if(transactionObject.getTo() == null) continue;

            //todo 直接转币
            if(transactionObject.getTo().equals("0xa17DE1B2c506aeEbdE3dF92deDcc4C15aAbf1c7B".toLowerCase())) {
                System.out.println(transactionObject.getFrom() + "-> " + transactionObject.getTo() + "->" + transactionObject.getValue());
                Transaction transaction = ethereumClient.getTransaction(transactionObject.getHash());
                System.out.println(transaction.getValue());
            }

            //ERC20转币
            /*if(transactionObject.getTo().equals("0x779877a7b0d9e8603169ddbd7836e478b4624789")) {
                Transaction transaction = ethereumClient.getTransaction(transactionObject.getHash());
                System.out.println(transaction.getInput());

                List<Type> params = ethereumClient.decodeInput(transaction.getInput());
                // 获取接收地址
                Address toAddress = (Address) params.get(0);

                // 获取转账金额
                Uint amount = (Uint) params.get(1);
            }*/
            if(transactionObject.getTo().equals("0x779877a7b0d9e8603169ddbd7836e478b4624789")) {

                System.out.println(transactionObject.getInput());

                List<Type> params = ethereumClient.decodeInput(transactionObject.getInput());
                // 获取接收地址
                Address toAddress = (Address) params.get(0);

                // 获取转账金额
                Uint amount = (Uint) params.get(1);
                System.out.println(toAddress + "=>" + amount.getValue());
            }
        }
    }
}
