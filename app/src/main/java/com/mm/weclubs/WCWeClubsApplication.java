package com.mm.weclubs;

import android.app.Application;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.blankj.utilcode.utils.Utils;
import com.mm.weclubs.util.LibCommonUtils;
import com.mm.weclubs.util.MD5Util;
import com.mm.weclubs.util.PreferencesHelper;
import com.mm.weclubs.util.WCLog;

import java.util.UUID;

import cn.jpush.android.api.JPushInterface;
import cn.smssdk.SMSSDK;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/21 下午9:11
 * 描述:  app 的应用入口
 */

public class WCWeClubsApplication extends Application {

    private WCLog log = new WCLog(WCWeClubsApplication.class);

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
        ToastUtils.init(false);
        SMSSDK.initSDK(this, "1d66889b4e514", "be7b94528ec334b58c4e61f6780d104a");

        initScreenWidth();
        initScreenHeight();
        initUUID();

        JPushInterface.setDebugMode(true);  // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);          // 初始化 JPush
        log.d("JPush RegistrationId = " + JPushInterface.getRegistrationID(this));
    }

    /**
     * 获取手机屏幕的宽度分辨率
     */
    public void initScreenWidth() {
        int screenWidth =
                PreferencesHelper.getInstance(getApplicationContext()).getAsInteger("screen_width", 0);

        if (screenWidth <= 0) {
            screenWidth = ScreenUtils.getScreenWidth();

            log.d("initScreenWidth: 生成的 screenWidth = " + screenWidth);

            PreferencesHelper.getInstance(getApplicationContext()).put("screen_width", screenWidth);
        } else {
            log.d("initScreenWidth: 从缓存中读取的 screenWidth = " + screenWidth);
        }
    }

    /**
     * 获取手机屏幕的高度分辨率
     */
    public void initScreenHeight() {
        int screenHeight =
                PreferencesHelper.getInstance(getApplicationContext()).getAsInteger("screen_height", 0);
        if (screenHeight <= 0) {
            screenHeight = ScreenUtils.getScreenHeight();

            log.d("initScreenHeight: 生成的 screenHeight = " + screenHeight);

            PreferencesHelper.getInstance(getApplicationContext()).put("screen_height", screenHeight);
        } else {
            log.d("initScreenHeight: 从缓存中读取的 screenHeight = " + screenHeight);
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

            log.d("initUUID: 原始uuid = " + uuid);

            uuid = MD5Util.md5(uuid).toLowerCase();

            log.d("initUUID: 加密后的uuid = " + uuid);

            PreferencesHelper.getInstance(getApplicationContext()).put("uuid", uuid);
        } else {
            log.d("initUUID: 从缓存中读取的uuid = " + uuid);
        }
    }
}
