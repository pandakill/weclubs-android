package com.mm.weclubs.util;

import android.util.Log;

import com.mm.weclubs.config.WCConfigConstants;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/21 下午10:31
 * 描述:
 */

public class WCLog {

    private String TAG = null;

    public WCLog(Class clazz) {
        TAG = clazz.getSimpleName();
    }

    public void e(String info) {
        Log.e(TAG, info);
    }

    public void i(String info) {
        Log.i(TAG, info);
    }

    public void d(String info) {
        if (WCConfigConstants.DEV) {
            Log.d(TAG, info);
        }
    }
}
