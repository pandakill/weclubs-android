package com.mm.weclubs.data.pojo;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/2/7 下午2:37
 * 描述:
 */

public class LoginBean {

    /**
     * user_id : 1
     * token : ZTkyZWYyYzUyZTI2NTc1NzIwNWI5MTI0MDM0NWQ5NjRfZGF0ZT0xNDg2NDMwOTUzMzUw
     */

    private int user_id;
    private String token;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "user_id=" + user_id +
                ", token='" + token + '\'' +
                '}';
    }
}
