package com.mm.weclubs.data.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/2/7 下午2:37
 * 描述:
 */

public class LoginBean implements Serializable {
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

    private String graduate_year;
    private String avatar_url;
    private int gender;
    private int school_id;
    private int user_id;
    private String nick_name;
    private String mobile;
    private int is_auth;
    private String school_name;
    private String real_name;
    private String class_name;
    private String token;

    public String getGraduate_year() {
        return graduate_year;
    }

    public void setGraduate_year(String graduate_year) {
        this.graduate_year = graduate_year;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIs_auth() {
        return is_auth;
    }

    public void setIs_auth(int is_auth) {
        this.is_auth = is_auth;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
