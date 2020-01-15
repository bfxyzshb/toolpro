package com.study.util.md5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 计算文件的MD5
 */
public class FileMD5 {
    private static Logger logger = LoggerFactory.getLogger(FileMD5.class);
    private static final String MD5 = "MD5";
    public static final int MD5_LENGTH = 16;
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 计算文件的MD5
     *
     * @param fileName 文件的绝对路径
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(String fileName) throws IOException {
        File f = new File(fileName);
        return getFileMD5String(f);
    }

    /**
     * 计算文件的MD5，重载方法
     *
     * @param file 文件对象
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(MD5);
        } catch (NoSuchAlgorithmException e) {
            logger.error("initialize messageDigest error");
        }
        messageDigest.update(byteBuffer);
        return bufferToHex(messageDigest.digest());
        // Common方法的encodeHex效率不如bufferToHex
        // return new String(Hex.encodeHex(messageDigest.digest()));
    }

    /**
     * 计算文件的MD5，重载方法
     *
     * @param byteOfFile 文件对象
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(byte[] byteOfFile) {
        if (byteOfFile == null || byteOfFile.length == 0) {
            return null;
        }
        ByteBuffer bb = ByteBuffer.wrap(byteOfFile);
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(MD5);
        } catch (NoSuchAlgorithmException e) {
            logger.error("initialize messageDigest error");
        }
        messageDigest.update(bb);
        return bufferToHex(messageDigest.digest());
        // Common方法的encodeHex效率不如bufferToHex
        // return new String(Hex.encodeHex(messageDigest.digest()));
    }

    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static boolean verifyFileByMD5(byte[] byteOfFile, String verifyStr) {
        String actualStr = getFileMD5String(byteOfFile);
        return verifyStr.equalsIgnoreCase(actualStr);
    }

    public static boolean verifyFileByMD5(byte[] byteOfFile, byte[] verifyMD5) {
        byte[] fileMD5 = getFileMD5ByteArray(byteOfFile);
        if (null == fileMD5 || null == verifyMD5 || fileMD5.length != verifyMD5.length) {
            return false;
        }
        for (int i = 0; i < fileMD5.length; i++) {
            if (fileMD5[i] != verifyMD5[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算文件的MD5，重载方法
     *
     * @param byteOfFile 文件对象
     * @return
     * @throws IOException
     */
    public static byte[] getFileMD5ByteArray(byte[] byteOfFile) {
        if (byteOfFile == null || byteOfFile.length == 0) {
            return null;
        }
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(MD5);
        } catch (NoSuchAlgorithmException e) {
            logger.error("initialize messageDigest error");
            return null;
        }
        ByteBuffer bb = ByteBuffer.wrap(byteOfFile);
        messageDigest.update(bb);
        return messageDigest.digest();
    }
}