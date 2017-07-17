package com.mm.weclubs.data.db;

import com.mm.weclubs.data.db.entity.User;

import io.reactivex.Flowable;

/**
 * 文 件 名: DbHelper
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/6 21:22
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public interface DbHelper {

    void saveUser(User user);

    void clearUser();

    User loadUser();

    Flowable<User> loadUserAsync();
}
