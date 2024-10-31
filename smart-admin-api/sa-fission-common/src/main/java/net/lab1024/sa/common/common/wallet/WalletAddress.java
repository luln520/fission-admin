package net.lab1024.sa.common.common.wallet;

import lombok.Data;

@Data
public class WalletAddress {

    private String address;
    private String privateKey;
    private String publicKey;

}
