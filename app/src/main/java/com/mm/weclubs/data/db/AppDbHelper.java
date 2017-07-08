package com.mm.weclubs.data.db;

import android.content.Context;

import com.mm.weclubs.data.db.database.AppDatabase;
import com.mm.weclubs.data.db.entity.User;
import com.mm.weclubs.di.ApplicationContext;
import com.socks.library.KLog;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 文 件 名: AppDbHelper
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/6 21:45
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */
@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;
    private final Context mContext;
    @Inject
    public AppDbHelper(@ApplicationContext Context context, AppDatabase appDatabase) {
        mAppDatabase = appDatabase;
        mContext = context;
    }

    private void insertUser(User user) {
        mAppDatabase.userDao().insertUser(user);
    }

    private void updateUser(User user) {
        mAppDatabase.userDao().updateUser(user);
    }

    private void deleteUser(User user) {
        mAppDatabase.userDao().deleteUser(user);
    }

    private User loadUserFromId(int id) {
        return mAppDatabase.userDao().loadUserFromId(id);
    }

    @Override
    public void saveUser(User user) {
        User u = loadUser();
        if (u == null){
            insertUser(user);
        }else{
            deleteUser(u);
            insertUser(user);
        }

        final String alias = "user_" + user.getUserId();
        JPushInterface.setAlias(mContext, alias, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                KLog.d("setAlias i = " + i + "; s = " + s + "; set = " + (set != null ? set.toString() : "null"));
            }
        });
    }

    @Override
    public void clearUser() {
        User user = loadUser();
        mAppDatabase.userDao().deleteUser(user);
        JPushInterface.setAlias(mContext, null, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
            }
        });
    }

    @Override
    public User loadUser() {
        return mAppDatabase.userDao().loadUser();
    }
}
