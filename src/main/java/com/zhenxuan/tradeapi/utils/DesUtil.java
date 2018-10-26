package com.zhenxuan.tradeapi.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;

/**
 * Created by silencehere on 16/10/29.
 */
public class DesUtil {

    private static final String DES = "DES";
    private static final String DEFAULT_KEY = "bAiDu6!0IP$@^20*&#";
    private static final Logger LOG = LoggerFactory.getLogger(DesUtil.class);

    private DesUtil() {
    }

    public static String encrypt(String data) {
        return encrypt(data, "bAiDu6!0IP$@^20*&#");
    }


    public static String encrypt(String data, String key) {
        if (data == null) {
            return null;
        } else {
            byte[] bt = encrypt(data.getBytes(), key.getBytes());
            String strs = Base64.encodeBase64String(bt);
            return strs;
        }
    }

    private static byte[] encrypt(byte[] data, byte[] key) {
        byte[] result = null;

        try {
            SecureRandom e = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, securekey, e);
            result = cipher.doFinal(data);
        } catch (InvalidKeyException var8) {
            LOG.error("InvalidKeyException", var8);
        } catch (NoSuchAlgorithmException var9) {
            LOG.error("NoSuchAlgorithmException", var9);
        } catch (InvalidKeySpecException var10) {
            LOG.error("InvalidKeySpecException", var10);
        } catch (NoSuchPaddingException var11) {
            LOG.error("NoSuchPaddingException", var11);
        } catch (IllegalBlockSizeException var12) {
            LOG.error("IllegalBlockSizeException", var12);
        } catch (BadPaddingException var13) {
            LOG.error("BadPaddingException", var13);
        }

        return result;
    }


    public static String decrypt(String data) {
        return decrypt(data, "bAiDu6!0IP$@^20*&#");
    }

    public static String decrypt(String data, String key) {
        if (data == null) {
            return null;
        } else {
            byte[] buf = Base64.decodeBase64(data);
            byte[] bt = decrypt(buf, key.getBytes());
            return new String(bt);
        }
    }



    private static byte[] decrypt(byte[] data, byte[] key) {
        byte[] result = null;

        try {
            SecureRandom e = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, securekey, e);
            result = cipher.doFinal(data);
        } catch (InvalidKeyException var8) {
            LOG.error("InvalidKeyException", var8);
        } catch (NoSuchAlgorithmException var9) {
            LOG.error("NoSuchAlgorithmException", var9);
        } catch (InvalidKeySpecException var10) {
            LOG.error("InvalidKeySpecException", var10);
        } catch (NoSuchPaddingException var11) {
            LOG.error("NoSuchPaddingException", var11);
        } catch (IllegalBlockSizeException var12) {
            LOG.error("IllegalBlockSizeException", var12);
        } catch (BadPaddingException var13) {
            LOG.error("BadPaddingException", var13);
        }

        return result;
    }
}
