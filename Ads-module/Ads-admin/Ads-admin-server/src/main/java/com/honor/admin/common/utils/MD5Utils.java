package com.honor.admin.common.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    private static final char[] HEX_DIGITS = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

    public static String hexdigest(String content) {
        if (content == null) {
            return null;
        }

        try {
            return hexdigest(content.getBytes(Charset.forName("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String hexdigest(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(input);
        byte[] buffer = messageDigest.digest();
        return bytesToStr(buffer);
    }

    private static String bytesToStr(byte[] buffer) {
        char[] resultBuffer = new char[32];
        int i = 0;
        int j = 0;
        while (true) {
            if (i >= 16)
                return new String(resultBuffer);
            int k = buffer[i];
            int m = j + 1;
            resultBuffer[j] = HEX_DIGITS[(0xF & k >>> 4)];
            j = m + 1;
            resultBuffer[m] = HEX_DIGITS[(k & 0xF)];
            i++;
        }
    }
}
