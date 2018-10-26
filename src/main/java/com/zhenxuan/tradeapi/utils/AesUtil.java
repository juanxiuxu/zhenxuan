package com.zhenxuan.tradeapi.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * aes加密解密
 */
public class AesUtil {

    private static final String AES = "AES";
    private static final String BOUNCY_CASTLE = "BC";
    private static final String CHAR_ENCODING = "UTF-8";

    private static final String CIPHER_ALGORITHM_PKCS7 ="AES/CBC/PKCS7Padding";
    private static final String CIPHER_ALGORITHM_PKCS5 ="AES/CBC/PKCS5Padding";

    public enum Mode {
        PKCS5(1),
        PKCS7(2);

        Mode(int code) {
            this.code = code;
        }

        public final int code;
    }


    static {
        // 导入支持AES/CBC/PKCS7Padding的Provider
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * aes加密
     * @param plaintext  utf-8编码
     * @param aesKey
     * @param aesIv
     * @return
     */
    public static byte[] encrypt(Mode mode, byte[] plaintext, byte[] aesKey, byte[] aesIv) {
        try {
            Cipher cipher = selectCipher(mode);
            SecretKeySpec secretKey = new SecretKeySpec(aesKey, AES);
            IvParameterSpec ivSpec = new IvParameterSpec(aesIv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            return cipher.doFinal(plaintext);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * aes解密
     * @param ciphertext
     * @param aesKey
     * @param aesIv
     * @return
     */
    public static byte[] decrypt(Mode mode, byte[] ciphertext, byte[] aesKey, byte[] aesIv) {
        try {
            Cipher cipher = selectCipher(mode);
            SecretKeySpec secretKey = new SecretKeySpec(aesKey, AES);
            IvParameterSpec ivSpec = new IvParameterSpec(aesIv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            return cipher.doFinal(ciphertext);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Cipher selectCipher(Mode mode) throws Exception {
        Cipher cipher;
        switch (mode) {
            case PKCS5:
                cipher = Cipher.getInstance(CIPHER_ALGORITHM_PKCS5);
                break;
            case PKCS7:
                cipher = Cipher.getInstance(CIPHER_ALGORITHM_PKCS7, BOUNCY_CASTLE);
                break;
            default:
                throw new RuntimeException("Not Supported mode!");
        }
        return cipher;
    }

    public static void main(String[] args) throws Exception {

        String data = "i am data";
        String sessionKey = "i am strong key ";
        String iv = "i am iv i am iv ";

        // pkcs7
        String encryptedData7 = Base64.encodeBase64String(encrypt(Mode.PKCS7, data.getBytes(CHAR_ENCODING), sessionKey.getBytes(), iv.getBytes()));
        System.out.println(encryptedData7);

        String originalData7 = new String(decrypt(Mode.PKCS7, Base64.decodeBase64(encryptedData7), sessionKey.getBytes(), iv.getBytes()), CHAR_ENCODING);
        System.out.println(originalData7);

        // pkcs5
        String encryptedData5 = Base64.encodeBase64String(encrypt(Mode.PKCS5, data.getBytes(CHAR_ENCODING), sessionKey.getBytes(), iv.getBytes()));
        System.out.println(encryptedData5);

        String originalData5 = new String(decrypt(Mode.PKCS5, Base64.decodeBase64(encryptedData5), sessionKey.getBytes(), iv.getBytes()), CHAR_ENCODING);
        System.out.println(originalData5);
    }
}
