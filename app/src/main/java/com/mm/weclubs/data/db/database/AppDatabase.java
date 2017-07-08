package com.mm.weclubs.data.db.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mm.weclubs.data.db.dao.UserDao;
import com.mm.weclubs.data.db.entity.User;

/**
 * 文 件 名: AppDatabase
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/6 23:50
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract UserDao userDao();
}
