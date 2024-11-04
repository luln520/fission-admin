package net.lab1024.sa.common.common.wallet;

import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.Credentials;

import java.util.Arrays;

public class AddressUtils {

    public static String getAddressFromPrivateKey(String privateKeyHex) {
        try {
            // 确保私钥是十六进制格式
            if (!privateKeyHex.startsWith("0x")) {
                privateKeyHex = "0x" + privateKeyHex;
            }

            // 通过私钥创建凭证
            Credentials credentials = Credentials.create(privateKeyHex);

            // 获取地址
            return credentials.getAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String ethToTron(String ethAddress) {
        try {
            // 移除ETH地址的"0x"前缀(如果有的话)
            if (ethAddress.startsWith("0x")) {
                ethAddress = ethAddress.substring(2);
            }

            // 确保地址长度正确(40个字符)
            if (ethAddress.length() != 40) {
                throw new IllegalArgumentException("Invalid ETH address length");
            }

            // 添加TRON地址前缀(41)
            byte[] addressBytes = Hex.decode(ethAddress);
            byte[] tronAddressBytes = new byte[addressBytes.length + 1];
            tronAddressBytes[0] = 0x41;
            System.arraycopy(addressBytes, 0, tronAddressBytes, 1, addressBytes.length);

            // 计算校验和
            byte[] hash1 = Sha256Hash.hash(tronAddressBytes);
            byte[] hash2 = Sha256Hash.hash(hash1);
            byte[] checkSum = Arrays.copyOfRange(hash2, 0, 4);

            // 组合地址字节和校验和
            byte[] finalBytes = new byte[tronAddressBytes.length + 4];
            System.arraycopy(tronAddressBytes, 0, finalBytes, 0, tronAddressBytes.length);
            System.arraycopy(checkSum, 0, finalBytes, tronAddressBytes.length, 4);

            // Base58编码得到最终的TRON地址
            return Base58.encode(finalBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error converting address", e);
        }
    }
}
