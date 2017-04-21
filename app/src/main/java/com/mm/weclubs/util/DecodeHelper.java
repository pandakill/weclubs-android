package com.mm.weclubs.util;

import android.support.annotation.NonNull;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/4 下午4:46
 * 描述: 加解密的方法
 */
public class DecodeHelper {

    private static DecodeHelper sInstance;

    public static DecodeHelper getInstance() {
        if (sInstance == null) {
            sInstance = new DecodeHelper();
        }

        return sInstance;
    }

    /**
     * 将一个字节流进行加密处理
     *
     * @param data  需要加密的字节流
     * @param type  加密算法类型
     * @return  返回加密后的字符串
     */
    public String encode(@NonNull byte[] data, @NonNull EncodeType type) {
        String encodeStr = "";

        switch (type) {
            case BASE_64:
                encodeStr = Base64.encode(data);
                break;
            case ASE:
                break;
        }

        return encodeStr;
    }

    /**
     * 将一个字符串进行解密
     *
     * @param data  需要解密的字符串
     * @param type  解密算法类型
     * @return  返回解密后的字节流
     */
    public byte[] decode(@NonNull String data, @NonNull EncodeType type) {
        byte[] decodeByte = new byte[0];
        switch (type) {
            case BASE_64:
                decodeByte = Base64.decode(data);
                break;
            case ASE:
                break;
            default:
                break;
        }

        return decodeByte;
    }

    public enum EncodeType {
        BASE_64,
        ASE;

        EncodeType() {}
    }
}
