package net.lab1024.sa.common.common.wallet;

import lombok.Data;

@Data
public class TronLogRequest {
    private String jsonrpc;
    private String method;
    private Params[] params;
    private int id;

    @Data
    public static class Params {
        private String fromBlock;
        private String toBlock;
        private String address;
        private String[] topics;
    }
}
