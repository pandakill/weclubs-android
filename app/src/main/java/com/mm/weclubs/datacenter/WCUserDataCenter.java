package com.mm.weclubs.datacenter;

import android.content.Context;

import com.mm.weclubs.data.pojo.WCUserInfoInfo;
import com.mm.weclubs.util.DecodeHelper.EncodeType;
import com.mm.weclubs.util.PreferencesHelper;
import com.mm.weclubs.util.WCLog;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/29 下午12:52
 * 描述:
 */

public class WCUserDataCenter {

    private static final String USER_CENTER_LAST_TIME_LOGIN = "PREFERENCE_KEY_LAST_LOGIN_MOBILE";
    private static final String USER_CENTER_LOGIN_USER = "PREFERENCE_KEY_USER";

    private WCLog log = null;

    private Context mContext = null;
    private WCUserInfoInfo mCurrentUserInfo = null;

    private static final Object monitor = new Object();
    private static WCUserDataCenter mUserDataCenter = null;

    private WCUserDataCenter(Context context) {
        mContext = context;
        log = new WCLog(WCUserDataCenter.class);
    }

    public static WCUserDataCenter getInstance(Context context) {
        synchronized (monitor) {
            if (mUserDataCenter == null) {
                mUserDataCenter = new WCUserDataCenter(context);
            }

            return mUserDataCenter;
        }
    }

    /**
     * 保存当前登录的用户状态
     *
     * @param userInfo  用户信息
     */
    public void saveUserInfo(WCUserInfoInfo userInfo) {
        if (userInfo == null || userInfo.getUser_id() <= 0) {
            log.i("saveUserInfo：保存用户信息失败");
            return;
        }

        mCurrentUserInfo = userInfo;

        String alias = "user_" + userInfo.getUser_id();
        JPushInterface.setAlias(mContext, alias, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                log.d("setAlias i = " + i + "; s = " + s + "; set = " + (set != null ? set.toString() : "null"));
            }
        });

        PreferencesHelper.getInstance(mContext).put(USER_CENTER_LAST_TIME_LOGIN, userInfo.getMobile());
        PreferencesHelper.getInstance(mContext).put(USER_CENTER_LOGIN_USER, userInfo, EncodeType.BASE_64);
    }

    public void deleteUserInfo() {
        mCurrentUserInfo = null;
        PreferencesHelper.getInstance(mContext).put(USER_CENTER_LOGIN_USER, null, EncodeType.BASE_64);
        JPushInterface.setAlias(mContext, null, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
            }
        });
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return  当前登录的用户
     */
    public WCUserInfoInfo getCurrentUserInfo() {
        if (mCurrentUserInfo == null) {
            mCurrentUserInfo = (WCUserInfoInfo) PreferencesHelper.getInstance(mContext)
                    .getAsObject(USER_CENTER_LOGIN_USER, EncodeType.BASE_64);
            log.d("getCurrentUserInfo：" + (mCurrentUserInfo != null ? mCurrentUserInfo.toString() : null));
        }

        return mCurrentUserInfo;
    }

    /**
     * 获取上次登录的手机号码
     *
     * @return  手机号码
     */
    public String getLastTimeLoginMobile() {
        return PreferencesHelper.getInstance(mContext).getAsString(USER_CENTER_LAST_TIME_LOGIN, null);
    }
}
