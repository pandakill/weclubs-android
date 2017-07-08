package com.mm.weclubs.data.network;

/**
 * 文 件 名: NetError
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/7 01:01
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public class NetError extends Exception{
    private String errorBody;

    private int errorCode = 0;

    public NetError(String errorBody, int errorCode) {
        this.errorBody = errorBody;
        this.errorCode = errorCode;
    }

    public String getErrorBody() {
        return errorBody;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
