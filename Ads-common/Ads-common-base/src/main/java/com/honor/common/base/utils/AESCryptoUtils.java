package com.honor.common.base.utils;


import com.honor.common.base.constant.ErrorConstant;
import com.honor.common.base.constant.I18nConstant;
import com.honor.common.base.exception.PlugException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.Charset;


/**
 * 对称加密工具
 */
@Slf4j
public class AESCryptoUtils {

    /**
     * 加密
     *
     * @param plainText
     * @param password
     * @return
     */
    public static String encode(String plainText, String password) {
        try {
            byte[] bytes = plainText.getBytes(Charset.forName("UTF-8"));
            return Base64Utils.encodeToString(encode(bytes, AESKeyToolUtils.getDesKey(password)), Base64Utils.NO_WRAP);
        } catch (Exception e) {
            log.error("AESCryptoUtils.encode.params={}", plainText, e);
            throw PlugException.INSTANCE(ErrorConstant.PARSE_ERROR, I18nConstant.ENC_OR_DEC_EXCEPTION, "加密或者解密异常 !");
        }
    }

    /**
     * 加密
     *
     * @param plainBytes
     * @param desKey
     * @return
     */
    private static byte[] encode(byte[] plainBytes, SecretKey desKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            return cipher.doFinal(plainBytes);
        } catch (Exception e) {
            log.error("AESCryptoUtils.encode.params={}", plainBytes, e);
            throw PlugException.INSTANCE(ErrorConstant.PARSE_ERROR, I18nConstant.FAILURE, "加密或者解密异常 !");
        }
    }

    /**
     * 解密
     *
     * @param cipherText
     * @param password
     * @return
     */
    public static String decode(String cipherText, String password) {
        try {
            byte[] bytes = cipherText.getBytes(Charset.forName("UTF-8"));
            bytes = Base64Utils.decode(bytes, Base64Utils.NO_WRAP);
            return new String(decode(bytes, AESKeyToolUtils.getDesKey(password)), Charset.forName("UTF-8"));
        } catch (Exception e) {
            log.error("AESCryptoUtils.decode.params={}", cipherText, e);
            throw PlugException.INSTANCE(ErrorConstant.PARSE_ERROR, I18nConstant.ENC_OR_DEC_EXCEPTION, "加密或者解密异常 !");
        }
    }

    /**
     * 解密
     *
     * @param cipherBytes
     * @param desKey
     * @return
     */
    private static byte[] decode(byte[] cipherBytes, SecretKey desKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            return cipher.doFinal(cipherBytes);
        } catch (Exception e) {
            log.error("AESCryptoUtils.decode.params={}", cipherBytes, e);
            throw PlugException.INSTANCE(ErrorConstant.PARSE_ERROR, I18nConstant.ENC_OR_DEC_EXCEPTION, "加密或者解密异常 !");
        }
    }
}
