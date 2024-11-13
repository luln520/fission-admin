package net.lab1024.sa.common.common.wallet;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRecord {

    public BigInteger blockNumber;
    public String transactionHash;
    public String from;
    public String to;
    public BigInteger value;

    @Override
    public String toString() {
        return String.format(
                "Block: %s, TxHash: %s, From: %s, To: %s, Value: %s",
                blockNumber, transactionHash, from, to, value
        );
    }
}
