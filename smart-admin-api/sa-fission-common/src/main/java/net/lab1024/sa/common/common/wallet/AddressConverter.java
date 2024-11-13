package net.lab1024.sa.common.common.wallet;

import java.util.Arrays;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.util.encoders.Hex;

public class AddressConverter {

    private static final int ADDRESS_SIZE = 20;
    private static final int ADDRESS_LENGTH_IN_HEX = 40;
    private static final String TRON_ADDRESS_PREFIX = "41";

    /**
     * Tron地址转换为ETH地址
     * @param tronAddress Tron地址 (Base58Check格式，如 "TXXXXYourAddressXXXX")
     * @return ETH地址 (带0x前缀，如 "0xYourAddress")
     */
    public static String tronToEth(String tronAddress) {
        try {
            // 如果输入的是十六进制格式（以41开头），直接处理
            if (tronAddress.startsWith(TRON_ADDRESS_PREFIX)) {
                String addressWithout41 = tronAddress.substring(2);
                if (addressWithout41.length() == ADDRESS_LENGTH_IN_HEX) {
                    return "0x" + addressWithout41;
                }
            }

            // 解码Base58Check格式的Tron地址
            byte[] decoded = decodeBase58Check(tronAddress);

            // 移除前缀（0x41）
            byte[] addressBytes = Arrays.copyOfRange(decoded, 1, decoded.length);

            // 转换为ETH地址格式
            String ethAddress = "0x" + Hex.toHexString(addressBytes);

            return ethAddress;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Tron address: " + tronAddress, e);
        }
    }

    /**
     * ETH地址转换为Tron地址
     * @param ethAddress ETH地址 (可以带或不带0x前缀)
     * @return Tron地址 (Base58Check格式)
     */
    public static String ethToTron(String ethAddress) {
        try {
            // 移除0x前缀（如果有）
            String cleanAddress = ethAddress.startsWith("0x") ?
                    ethAddress.substring(2) : ethAddress;

            if (cleanAddress.length() != ADDRESS_LENGTH_IN_HEX) {
                throw new IllegalArgumentException("Invalid ETH address length");
            }

            // 添加Tron地址前缀（0x41）
            byte[] addressBytes = Hex.decode(TRON_ADDRESS_PREFIX + cleanAddress);

            // 编码为Base58Check格式
            return encodeBase58Check(addressBytes);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid ETH address: " + ethAddress, e);
        }
    }

    /**
     * 获取十六进制格式的Tron地址（以41开头）
     * @param tronAddress Base58Check格式的Tron地址
     * @return 十六进制格式的Tron地址
     */
    public static String tronToHex(String tronAddress) {
        try {
            // 如果已经是十六进制格式，直接返回
            if (tronAddress.startsWith(TRON_ADDRESS_PREFIX) &&
                    tronAddress.length() == ADDRESS_LENGTH_IN_HEX + 2) {
                return tronAddress;
            }

            // 解码Base58Check格式
            byte[] decoded = decodeBase58Check(tronAddress);
            return Hex.toHexString(decoded);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Tron address: " + tronAddress, e);
        }
    }

    // Base58Check 解码
    private static byte[] decodeBase58Check(String input) {
        byte[] decoded = Base58.decode(input);

        // 验证校验和
        byte[] checksum = calculateChecksum(Arrays.copyOfRange(decoded, 0, decoded.length - 4));
        byte[] providedChecksum = Arrays.copyOfRange(decoded, decoded.length - 4, decoded.length);

        if (!Arrays.equals(checksum, providedChecksum)) {
            throw new IllegalArgumentException("Invalid checksum for address: " + input);
        }

        return Arrays.copyOfRange(decoded, 0, decoded.length - 4);
    }

    // Base58Check 编码
    private static String encodeBase58Check(byte[] input) {
        byte[] checksum = calculateChecksum(input);
        byte[] addressWithChecksum = new byte[input.length + 4];
        System.arraycopy(input, 0, addressWithChecksum, 0, input.length);
        System.arraycopy(checksum, 0, addressWithChecksum, input.length, 4);
        return Base58.encode(addressWithChecksum);
    }

    // 计算校验和
    private static byte[] calculateChecksum(byte[] input) {
        byte[] first = sha256(input);
        byte[] second = sha256(first);
        return Arrays.copyOfRange(second, 0, 4);
    }

    // SHA256 哈希
    private static byte[] sha256(byte[] input) {
        SHA256Digest digest = new SHA256Digest();
        byte[] output = new byte[digest.getDigestSize()];
        digest.update(input, 0, input.length);
        digest.doFinal(output, 0);
        return output;
    }

    // Base58 实现
    private static class Base58 {
        private static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
        private static final int[] INDEXES = new int[128];

        static {
            Arrays.fill(INDEXES, -1);
            for (int i = 0; i < ALPHABET.length; i++) {
                INDEXES[ALPHABET[i]] = i;
            }
        }

        public static String encode(byte[] input) {
            if (input.length == 0) {
                return "";
            }

            // Count leading zeros
            int zeros = 0;
            while (zeros < input.length && input[zeros] == 0) {
                zeros++;
            }

            // Convert to numeric base58 representation
            byte[] encoded = new byte[input.length * 2];
            int outputStart = encoded.length;

            // Process all bytes
            for (int inputStart = zeros; inputStart < input.length;) {
                encoded[--outputStart] = (byte) ALPHABET[divmod(input, inputStart, 256, 58)];
                if (input[inputStart] == 0) {
                    inputStart++;
                }
            }

            // Preserve leading zeros
            while (outputStart < encoded.length && encoded[outputStart] == ALPHABET[0]) {
                outputStart++;
            }
            while (--zeros >= 0) {
                encoded[--outputStart] = (byte) ALPHABET[0];
            }

            // Return encoded string
            return new String(encoded, outputStart, encoded.length - outputStart);
        }

        public static byte[] decode(String input) {
            if (input.length() == 0) {
                return new byte[0];
            }

            // Convert to byte values
            byte[] input58 = new byte[input.length()];
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                int digit = c < 128 ? INDEXES[c] : -1;
                if (digit < 0) {
                    throw new IllegalArgumentException("Invalid character in address: " + c);
                }
                input58[i] = (byte) digit;
            }

            // Count leading zeros
            int zeros = 0;
            while (zeros < input58.length && input58[zeros] == 0) {
                zeros++;
            }

            // Convert from base58 to base256
            byte[] decoded = new byte[input.length()];
            int outputStart = decoded.length;

            // Process the bytes
            for (int inputStart = zeros; inputStart < input58.length;) {
                decoded[--outputStart] = (byte) divmod(input58, inputStart, 58, 256);
                if (input58[inputStart] == 0) {
                    inputStart++;
                }
            }

            // Ignore extra leading zeros
            while (outputStart < decoded.length && decoded[outputStart] == 0) {
                outputStart++;
            }

            // Return decoded result with leading zeros
            return Arrays.copyOfRange(decoded, outputStart - zeros, decoded.length);
        }

        private static byte divmod(byte[] number, int firstDigit, int base, int divisor) {
            int remainder = 0;
            for (int i = firstDigit; i < number.length; i++) {
                int digit = number[i] & 0xFF;
                int temp = remainder * base + digit;
                number[i] = (byte) (temp / divisor);
                remainder = temp % divisor;
            }
            return (byte) remainder;
        }
    }

    // 使用示例
    public static void main(String[] args) {
        try {
            System.out.println(ethToTron("0xf23e2730eb925770df4df12bea03b16f93fa3c8b"));
            // Tron地址转ETH地址
            String tronAddress = "TY44yxJn3BUCuvBCtQcHygdw64kG5VSQ1F"; // 替换为实际的Tron地址
            String ethAddress = tronToEth(tronAddress);
            System.out.println("Tron to ETH: " + ethAddress);

            // ETH地址转Tron地址
            String convertedTronAddress = ethToTron(ethAddress);
            System.out.println("ETH to Tron: " + convertedTronAddress);

            // 获取十六进制格式的Tron地址
            String hexTronAddress = tronToHex(tronAddress);
            System.out.println("Tron to Hex: " + hexTronAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}