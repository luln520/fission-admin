package net.lab1024.sa.common.common.wallet;

import org.bitcoinj.core.*;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.web3j.crypto.Credentials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WalletClient {

    private static final NetworkParameters NETWORK_PARAMETERS = MainNetParams.get();

    private DeterministicKey rootKey;
    private int chainId;
    private String path;

    public WalletClient(List<String> mnemonic, ChainEnum chainEnum) {
        DeterministicSeed seed = new DeterministicSeed(mnemonic, null, "", 0);
        DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();
        rootKey = keyChain.getKeyByPath(HDUtils.parsePath("M"), true);
        chainId = chainEnum.getCode();
        path = chainEnum.getValue();
    }

    public WalletAddress generateAddress(int index) {
        WalletAddress walletAddress = new WalletAddress();

        DeterministicHierarchy hierarchy = new DeterministicHierarchy(rootKey);
        List<ChildNumber> parentPath = HDUtils.parsePath(path);

        DeterministicKey deterministicKey = hierarchy.deriveChild(parentPath, true, true, new ChildNumber(index, false));
        if(chainId == ChainEnum.BTC.getCode()) {
            Address address = SegwitAddress.fromKey(NETWORK_PARAMETERS, deterministicKey);
            walletAddress.setAddress(address.toString());
            walletAddress.setPrivateKey(deterministicKey.getPrivateKeyAsHex());
            walletAddress.setPublicKey(deterministicKey.getPublicKeyAsHex());
        }else if(chainId == ChainEnum.ETH.getCode()) {
            Credentials credentials = Credentials.create(deterministicKey.getPrivKey().toString(16));
            walletAddress.setAddress(credentials.getAddress());
            walletAddress.setPrivateKey(credentials.getEcKeyPair().getPrivateKey().toString(16));
            walletAddress.setPublicKey(credentials.getEcKeyPair().getPublicKey().toString(16));
        }else if(chainId == ChainEnum.TRON.getCode()) {
            Credentials credentials = Credentials.create(deterministicKey.getPrivKey().toString(16));
            walletAddress.setAddress(AddressUtils.ethToTron(credentials.getAddress()));
            walletAddress.setPrivateKey(credentials.getEcKeyPair().getPrivateKey().toString(16));
            walletAddress.setPublicKey(credentials.getEcKeyPair().getPublicKey().toString(16));
        }

        return walletAddress;
    }



    public static void main(String[] args) {
    }
}
