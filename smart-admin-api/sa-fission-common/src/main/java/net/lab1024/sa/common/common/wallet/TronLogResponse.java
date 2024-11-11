package net.lab1024.sa.common.common.wallet;

import lombok.Data;

import java.util.List;

@Data
public class TronLogResponse {
    private String jsonrpc;
    private int id;
    private List<LogResult> result;

    @Data
    public static class LogResult {
        private String address;
        private String blockHash;
        private String blockNumber;
        private String data;
        private String logIndex;
        private boolean removed;
        private List<String> topics;
        private String transactionHash;
        private String transactionIndex;
    }
}
