package com.mm.weclubs.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mm.weclubs.data.db.entity.User;

/**
 * 文 件 名: UserDao
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/6 23:33
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM User WHERE userId = :id")
    User loadUserFromId(int id);

    @Query("SELECT * FROM User LIMIT 1")
    User loadUser();
}
