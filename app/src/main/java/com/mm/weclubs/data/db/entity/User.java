package com.mm.weclubs.data.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * 文 件 名: User
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/6 23:12
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */
@Entity
public class User {
    public static final int AUTH_NO = 0;
    public static final int AUTH_ING = 2;
    public static final int AUTH_SUCCESS = 1;
    public static final int AUTH_FAILD = 3;
    /**
     * graduate_year : 0级
     * avatar_url : http://weclubs.net/avatar/qx2jkloew8.jpeg
     * gender : 1
     * school_id : 2
     * user_id : 21
     * nick_name : 得闲来坐
     * mobile : 13987965600
     * is_auth : 0
     * school_name : 深圳市闲正来坐大学
     * real_name : 闲正来坐
     * class_name : 技术1班
     * token : MzdkYmFiNzkwNWVjNWM4MDFkZjU0NWEwMDE4ODlkOWFfZGF0ZT0xNDkwMDg0NzMyMjgw
     */

    private String graduateYear;
    private String avatarUrl;
    private int gender;
    private int schoolId;
    @PrimaryKey
    private int userId;
    private String nickName;
    private String mobile;
    private int isAuth;
    private String schoolName;
    private String realName;
    private String className;
    private String token;

    public String getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(String graduateYear) {
        this.graduateYear = graduateYear;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(int isAuth) {
        this.isAuth = isAuth;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
