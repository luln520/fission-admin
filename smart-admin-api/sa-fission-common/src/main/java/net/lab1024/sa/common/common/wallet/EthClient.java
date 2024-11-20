package net.lab1024.sa.common.common.wallet;

import lombok.extern.slf4j.Slf4j;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class EthClient {

    private static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.asList(
                    new TypeReference<Address>() {}, // from
                    new TypeReference<Address>() {}, // to
                    new TypeReference<Uint256>() {} // value
            ));

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

    public List<TransferRecord> queryTransfers(
            String recipientAddress,
            BigInteger fromBlock) throws Exception {
        EthFilter filter = new EthFilter(
                DefaultBlockParameter.valueOf(fromBlock),
                DefaultBlockParameter.valueOf("latest"),
                contractAddress
        );

        String encodedEventSignature = EventEncoder.encode(TRANSFER_EVENT);
        filter.addSingleTopic(encodedEventSignature);
        filter.addNullTopic();
        filter.addOptionalTopics("0x" + "000000000000000000000000" + recipientAddress.substring(2));

        // 获取日志
        List<EthLog.LogResult> logs = web3j.ethGetLogs(filter).send().getLogs();
        List<TransferRecord> transfers = new ArrayList<>();

        // 解析日志
        for (EthLog.LogResult<EthLog.LogObject> log: logs) {
            TransferRecord record = new TransferRecord();
            EthLog.LogObject logObject =  log.get();
            record.blockNumber = logObject.getBlockNumber();
            record.transactionHash = logObject.getTransactionHash();
            record.from = logObject.getTopics().get(1).substring(26);
            record.to = logObject.getTopics().get(2).substring(26);
            BigInteger value = new BigInteger(logObject.getData().substring(2), 16);
            record.value = value;
            transfers.add(record);
        }

        return transfers;
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

    public void close() {
        web3j.shutdown();
    }

    public static void main(String[] args) throws Exception {
        EthClient ethClient = new EthClient("https://sepolia.infura.io/v3/89693b1773544c30b1b4b66c2a81813f", "0x419Fe9f14Ff3aA22e46ff1d03a73EdF3b70A62ED");
        BigInteger fromBlock = BigInteger.valueOf(7112970);
        System.out.println(ethClient.queryTransfers("0x59f87D2D4B4A9c4Be86244b7209C4EbAC75B2EDc", fromBlock));
    }
}
