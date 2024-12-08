package net.lab1024.sa.common.common.wallet;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRecord {

    private Integer blockNumber;
    private String transactionHash;
    private String from;
    private String to;
    private BigInteger value;
    private String contract;

    @Override
    public String toString() {
        return String.format(
                "Block: %s, TxHash: %s, From: %s, To: %s, Value: %s",
                blockNumber, transactionHash, from, to, value
        );
    }
}
