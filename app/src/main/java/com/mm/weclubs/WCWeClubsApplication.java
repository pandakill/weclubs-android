package com.mm.weclubs;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.util.LibCommonUtils;
import com.mm.weclubs.util.MD5Util;
import com.mm.weclubs.util.PreferencesHelper;

import java.util.UUID;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/21 下午9:11
 * 描述:  app 的应用入口
 */

public class WCWeClubsApplication extends Application {

    private String TAG = WCWeClubsApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        initScreenWidth();
        initScreenHeight();
        initUUID();
    }

    /**
     * 获取手机屏幕的宽度分辨率
     */
    public void initScreenWidth() {
        int screenWidth =
                PreferencesHelper.getInstance(getApplicationContext()).getAsInteger("screen_width", 0);

        if (screenWidth <= 0 && getApplicationContext() != null) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            screenWidth = dm.widthPixels;

            if (WCConfigConstants.DEV) {
                Log.i(TAG, "initScreenWidth: 生成的 screenWidth = " + screenWidth);
            }

            PreferencesHelper.getInstance(getApplicationContext()).put("screen_width", screenWidth);
        } else {
            if (WCConfigConstants.DEV) {
                Log.i(TAG, "initScreenWidth: 从缓存中读取的 screenWidth = " + screenWidth);
            }
        }
    }

    /**
     * 获取手机屏幕的高度分辨率
     */
    public void initScreenHeight() {
        int screenHeight =
                PreferencesHelper.getInstance(getApplicationContext()).getAsInteger("screen_height", 0);
        if (screenHeight <= 0 && getApplicationContext() != null) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            screenHeight = dm.heightPixels;

            if (WCConfigConstants.DEV) {
                Log.i(TAG, "initScreenHeight: 生成的 screenHeight = " + screenHeight);
            }

            PreferencesHelper.getInstance(getApplicationContext()).put("screen_height", screenHeight);
        } else {
            if (WCConfigConstants.DEV) {
                Log.i(TAG, "initScreenHeight: 从缓存中读取的 screenHeight = " + screenHeight);
            }
        }
    }

    /**
     * 初始化 uuid
     */
    public void initUUID() {
        String uuid =
                PreferencesHelper.getInstance(getApplicationContext()).getAsString("uuid", null);

        if (LibCommonUtils.isEmpty(uuid)) {
            int sw = PreferencesHelper.getInstance(getApplicationContext()).getAsInteger("screen_width", 0);
            int sh = PreferencesHelper.getInstance(getApplicationContext()).getAsInteger("screen_height", 0);

            uuid = sw + "-" + UUID.randomUUID().toString() + "-" + sh;

            if (WCConfigConstants.DEV) {
                Log.i(TAG, "initUUID: 原始uuid = " + uuid);
            }

            uuid = MD5Util.md5(uuid).toLowerCase();

            if (WCConfigConstants.DEV) {
                Log.i(TAG, "initUUID: 加密后的uuid = " + uuid);
            }

            PreferencesHelper.getInstance(getApplicationContext()).put("uuid", uuid);
        } else {
            if (WCConfigConstants.DEV) {
                Log.i(TAG, "initUUID: 从缓存中读取的uuid = " + uuid);
            }
        }
    }
}
