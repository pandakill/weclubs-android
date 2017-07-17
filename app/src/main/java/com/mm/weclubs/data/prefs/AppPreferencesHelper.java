package com.mm.weclubs.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.mm.weclubs.di.ApplicationContext;
import com.mm.weclubs.di.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 文 件 名: AppPreferencesHelper
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/6 21:45
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {
    public static final int NULL_INDEX = -1;
    public static final int NULL_ID = -1;
    private static final String USER_CENTER_LAST_TIME_LOGIN = "PREFERENCE_KEY_LAST_LOGIN_ID";
    private static final String APP_UUID = "PREFERENCE_KEY_APP_UUID";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context, @PreferenceInfo String name) {
        mPrefs = context.getSharedPreferences(name,Context.MODE_PRIVATE);


    }

    @Override
    public void saveLastTimeLoginId(int id) {
        mPrefs.edit().putInt(USER_CENTER_LAST_TIME_LOGIN,id).apply();
    }

    @Override
    public int getLastTimeLoginId() {
        return mPrefs.getInt(USER_CENTER_LAST_TIME_LOGIN,NULL_INDEX);
    }

    @Override
    public void saveUUid(String uuid) {
        mPrefs.edit().putString(APP_UUID,uuid).apply();
    }

    @Override
    public String getUUid() {
        return mPrefs.getString(APP_UUID,"");
    }
}
