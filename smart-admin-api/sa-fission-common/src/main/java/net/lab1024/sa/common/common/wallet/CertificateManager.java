package net.lab1024.sa.common.common.wallet;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.util.Base64;

public class CertificateManager {
    private static final String PRIVATE_KEY_PATH = "private.key";
    private static final String PUBLIC_KEY_PATH = "public.key";

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    // 生成证书和密钥对
    public static void generateCertificateAndKeys() throws Exception {
        // 生成RSA密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 保存私钥
        try (FileOutputStream fos = new FileOutputStream(PRIVATE_KEY_PATH)) {
            fos.write(keyPair.getPrivate().getEncoded());
        }

        // 保存公钥
        try (FileOutputStream fos = new FileOutputStream(PUBLIC_KEY_PATH)) {
            fos.write(keyPair.getPublic().getEncoded());
        }
    }

    // 使用公钥加密
    public static String encrypt(String data, byte[] keyBytes) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
        PublicKey publicKey = keyFactory.generatePublic(
                new java.security.spec.X509EncodedKeySpec(keyBytes));

        // 加密数据
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // 由于RSA加密的数据长度限制，这里需要分段加密
        byte[] dataBytes = data.getBytes();
        int maxLength = 245; // RSA 2048位密钥的最大加密长度
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int offset = 0;
        while (offset < dataBytes.length) {
            int size = Math.min(maxLength, dataBytes.length - offset);
            byte[] encryptedBlock = cipher.doFinal(dataBytes, offset, size);
            out.write(encryptedBlock);
            offset += size;
        }

        return Base64.getEncoder().encodeToString(out.toByteArray());
    }

    // 使用私钥解密
    public static String decrypt(String encryptedData, byte[] keyBytes) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
        PrivateKey privateKey = keyFactory.generatePrivate(
                new java.security.spec.PKCS8EncodedKeySpec(keyBytes));

        // 解密数据
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // 分段解密
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        int blockSize = 256; // RSA 2048位密钥的密文块大小
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int offset = 0;
        while (offset < encryptedBytes.length) {
            int size = Math.min(blockSize, encryptedBytes.length - offset);
            byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, size);
            out.write(decryptedBlock);
            offset += size;
        }

        return new String(out.toByteArray());
    }

    // 测试方法
    public static void main(String[] args) {
        try {
            // 生成证书和密钥对
            generateCertificateAndKeys();
            System.out.println("证书和密钥对生成成功！");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}