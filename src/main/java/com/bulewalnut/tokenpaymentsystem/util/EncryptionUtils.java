package com.bulewalnut.tokenpaymentsystem.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

@Component
public class EncryptionUtils {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String TRANSFORMATION = "AES";
    private static final int KEY_SIZE = 16;

    @Value("${crypto.aes-secret-key}")
    private String secretKey;

    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(adjustKeyLength(secretKey), TRANSFORMATION);
        byte[] iv = generateIV();
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());

        // Combine IV and encrypted data
        byte[] encryptedDataWithIv = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, encryptedDataWithIv, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, encryptedDataWithIv, iv.length, encryptedBytes.length);

        return Base64.getEncoder().encodeToString(encryptedDataWithIv);
    }

    public String decrypt(String encryptedData) throws Exception {
        byte[] encryptedDataWithIv = Base64.getDecoder().decode(encryptedData);

        byte[] iv = new byte[16];
        byte[] encryptedBytes = new byte[encryptedDataWithIv.length - iv.length];
        System.arraycopy(encryptedDataWithIv, 0, iv, 0, iv.length);
        System.arraycopy(encryptedDataWithIv, iv.length, encryptedBytes, 0, encryptedBytes.length);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(adjustKeyLength(secretKey), TRANSFORMATION);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    private byte[] adjustKeyLength(String key) {
        byte[] keyBytes = key.getBytes();
        if (keyBytes.length < KEY_SIZE) {
            byte[] paddedKey = new byte[KEY_SIZE];
            System.arraycopy(keyBytes, 0, paddedKey, 0, keyBytes.length);
            return paddedKey;
        } else if (keyBytes.length > KEY_SIZE) {
            byte[] truncatedKey = new byte[KEY_SIZE];
            System.arraycopy(keyBytes, 0, truncatedKey, 0, KEY_SIZE);
            return truncatedKey;
        } else {
            return keyBytes;
        }
    }

    private byte[] generateIV() {
        byte[] iv = new byte[KEY_SIZE];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
}
