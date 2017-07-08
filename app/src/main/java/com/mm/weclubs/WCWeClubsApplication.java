package com.mm.weclubs;

import android.app.Application;

import com.blankj.utilcode.utils.ToastUtils;
import com.blankj.utilcode.utils.Utils;
import com.mm.weclubs.di.component.ApplicationComponent;
import com.mm.weclubs.di.component.DaggerApplicationComponent;
import com.mm.weclubs.di.module.ApplicationModule;
import com.socks.library.KLog;

import cn.jpush.android.api.JPushInterface;
import cn.smssdk.SMSSDK;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/21 下午9:11
 * 描述:  app 的应用入口
 */

public class WCWeClubsApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        if(BuildConfig.DEBUG){
            KLog.init(true,"WeClubs");
        }

        ToastUtils.init(false);
        SMSSDK.initSDK(this, "1d66889b4e514", "be7b94528ec334b58c4e61f6780d104a");

        JPushInterface.setDebugMode(true);  // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);          // 初始化 JPush
        KLog.d("JPush RegistrationId = " + JPushInterface.getRegistrationID(this));
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
