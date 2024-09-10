package com.bulewalnut.tokenpaymentsystem.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import org.apache.commons.codec.binary.Base64;

/**
 * AES 암호화 및 복호화를 수행하는 유틸리티 클래스.
 */
@Component
public class EncryptionUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding"; // 암호화 알고리즘
    private static final String TRANSFORMATION = "AES"; // 암호화 변환 방식
    private static final int KEY_SIZE = 16; // 키 사이즈 (바이트 단위)
    private static final String CHARSET_NAME = "UTF-8"; // 문자 인코딩

    private String iv; // 초기화 벡터 (IV)
    private Key keySpec; // 암호화에 사용할 비밀 키

    @Value("${crypto.aes-secret-key}")
    private String secretKey;

    /**
     * 주어진 암호화 비밀 키를 사용하여 객체를 생성.
     * 비밀 키는 16자리로 자르고, 이를 바탕으로 암호화 키를 생성.
     */
    @PostConstruct
    public void init() throws UnsupportedEncodingException {
        this.iv = secretKey.substring(0, KEY_SIZE);
        byte[] keyBytes = new byte[KEY_SIZE];
        byte[] b = secretKey.getBytes(CHARSET_NAME);
        int len = b.length;

        if (len > keyBytes.length) {
            len = keyBytes.length;
        }

        System.arraycopy(b, 0, keyBytes, 0, len);
        this.keySpec = new SecretKeySpec(keyBytes, TRANSFORMATION);
    }

    /**
     * 주어진 문자열을 암호화합니다.
     */
    public String encrypt(String str)
            throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] encrypted = c.doFinal(str.getBytes(CHARSET_NAME));
        return new String(Base64.encodeBase64(encrypted));
    }

    /**
     * 주어진 암호화된 문자열을 복호화합니다.
     */
    public String decrypt(String str)
            throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(c.doFinal(byteStr), CHARSET_NAME);
    }

}
