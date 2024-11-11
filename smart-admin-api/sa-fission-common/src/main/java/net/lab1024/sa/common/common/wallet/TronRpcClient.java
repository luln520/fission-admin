package net.lab1024.sa.common.common.wallet;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.google.api.client.util.Lists;
import okhttp3.*;

import java.math.BigInteger;
import java.util.List;

public class TronRpcClient {

    private boolean isMainNet;
    private String contractAddress;
    private String apiKey;
    private String rpcUrl;
    private final OkHttpClient client;


    public TronRpcClient(boolean isMainNet, String contractAddress, String apiKey, String rpcUrl) {
        this.isMainNet = isMainNet;
        this.contractAddress = contractAddress;
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
        this.rpcUrl = rpcUrl;
    }

    public List<TransferRecord> queryTransfers(BigInteger fromBlock, String receiverAddress) {
        List<TransferRecord> transferRecordList = Lists.newArrayList();

        receiverAddress = AddressConverter.tronToEth(receiverAddress);
        String contract = AddressConverter.tronToEth(contractAddress);
        String receiverTopic = "0x000000000000000000000000" + receiverAddress.substring(2);

        TronLogRequest tronLogRequest = new TronLogRequest();
        tronLogRequest.setId(1);
        tronLogRequest.setJsonrpc("2.0");
        tronLogRequest.setMethod("eth_getLogs");
        TronLogRequest.Params params = new TronLogRequest.Params();
        params.setAddress(contract);
        params.setFromBlock("0x" + fromBlock.toString(16));
        params.setToBlock("latest");
        params.setTopics(new String[]{"0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef", null, receiverTopic});
        tronLogRequest.setParams(new TronLogRequest.Params[]{params});
        String jsonRequest = JSON.toJSONString(tronLogRequest);

        RequestBody body = RequestBody.create(jsonRequest, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(rpcUrl)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("TRON-PRO-API-KEY", apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            TronLogResponse tronLogResponse = JSON.parseObject(response.body().string(), TronLogResponse.class);
            if(CollectionUtil.isNotEmpty(tronLogResponse.getResult())) {
                for(TronLogResponse.LogResult logResult : tronLogResponse.getResult()) {
                    TransferRecord transferRecord = new TransferRecord();
                    transferRecord.setBlockNumber(new BigInteger(logResult.getBlockNumber().substring(2), 16));
                    transferRecord.setFrom(AddressConverter.ethToTron(logResult.getTopics().get(1).substring(26)));
                    transferRecord.setTo(AddressConverter.ethToTron(logResult.getTopics().get(2).substring(26)));
                    BigInteger value = new BigInteger(logResult.getData().substring(2), 16);
                    transferRecord.setValue(value);
                    transferRecord.setTransactionHash(logResult.getTransactionHash());
                    transferRecordList.add(transferRecord);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transferRecordList;
    }

    public static void main(String[] args) {
        TronRpcClient tronRpcClient = new TronRpcClient(false, "TXLAQ63Xg1NAzckPwKHvzw7CSEmLMEqcdj", "", "https://nile.trongrid.io/jsonrpc");
        List<TransferRecord> recordList = tronRpcClient.queryTransfers(new BigInteger("51601294"), "TY44yxJn3BUCuvBCtQcHygdw64kG5VSQ1F");
        for(TransferRecord transferRecord : recordList) {
            System.out.println(transferRecord);
        }

    }
}
