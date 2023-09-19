package com.mice.backoffice.c2v_mice_backoffice_api.common;

import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 암호화 로직
 * @author	HyukJun Kwon
 * @email	hyukjk@com2us.com
 * @since	2022.11.01
 */
public class SecretLogic {
    // AES256 암호화 키
    private static String symmetricKey = "VUq1mlukb5A9_w-iAigLVZGGPyr1Rgt_EZUO1a5V9VTxbd6XGorQ_HPYnnijhb6DKN_ETwTroOoWVlaiWs7a3w";

    /**
     * 원본 문자열을 AES256 암호화하여 반환함.
     * @param	plainText	원본 문자열
     * @return	byte[]		AES256 암호화 데이터
     */
    public static byte[] AesEncrypt(String plainText) {
        byte[] encryptedData;
        try {
            String key = symmetricKey.substring(0, 32);
            String iv = symmetricKey.substring(0, 16);
            Cipher cipher = Cipher.getInstance(Constants.Auth.API_TOKEN_DATA_SECRET_INSTANCE);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), Constants.Auth.API_TOKEN_DATA_SECRET_ALGORITHM);
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
            encryptedData = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return encryptedData;
    }
    /**
     * AES256 암호화 데이터를 복호화하여 반환함.
     * @param	encryptedData	AES256 암호화 데이터
     * @return	String		    AES256 복호화된 문자열
     */
    public static String AesDecrypt(byte[] encryptedData) {
        String result = "";
        try {
            String key = symmetricKey.substring(0, 32);
            String iv = symmetricKey.substring(0, 16);
            Cipher cipher = Cipher.getInstance(Constants.Auth.API_TOKEN_DATA_SECRET_INSTANCE);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), Constants.Auth.API_TOKEN_DATA_SECRET_ALGORITHM);
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
            byte[] decrypted = cipher.doFinal(encryptedData);
            result = new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }
    /**
     * Base64 인코딩된 privateKey 문자열을 알고리즘에 맞는 PrivateKey 객체로 변환하여 반환함.
     * @param	privateKeyEncoded   Base64Encoded privateKey 문자열
     * @return	PrivateKey
     */
    public static PrivateKey convertStringToPrivateKey(String privateKeyEncoded) {
        PrivateKey privateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(Constants.Auth.API_KEY_INSTANCE);
            privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyEncoded)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return privateKey;
    }
    /**
     * Base64 인코딩된 publicKey 문자열을 알고리즘에 맞는 PublicKey 객체로 변환하여 반환함.
     * @param	publicKeyEncoded    Base64Encoded publicKey 문자열
     * @return	PublicKey
     */
    public static PublicKey convertStringToPublicKey(String publicKeyEncoded) {
        PublicKey publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(Constants.Auth.API_KEY_INSTANCE);
            publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyEncoded)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return publicKey;
    }
    /**
     * 원본 문자열을 BCrypt 해싱하여 반환함.
     * @param	orgStr	원본 문자열
     * @return	String	BCrypt 해싱 문자열
     */
    public static String hashByBCrypt(String orgStr) {
        return BCrypt.hashpw(orgStr, BCrypt.gensalt());
    }
    /**
     * 입력된 비밀번호와 저장된 해싱 비밀번호를 비교하여 동일여부를 반환함.
     * @param	inputPw		입력된 비밀번호
     * @param	hashedPw	저장된 비밀번호
     * @return	boolean		비밀번호 동일여부
     */
    public static boolean comparePwd(String inputPw, String hashedPw) {
        return BCrypt.checkpw(inputPw, hashedPw);
    }
}
