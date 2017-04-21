package com.mm.weclubs.data.pojo;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/2/7 下午2:33
 * 描述:
 */

public class UserBean {

    /**
     * user_id : 1
     * user_name : pandakill
     * icon_url : http://localhost:8080/pictures/1.jpeg
     * birthday : 1994-12-27
     * gender : 男
     * attribute : 1
     */

    private String user_id;
    private String user_name;
    private String icon_url;
    private String birthday;
    private String gender;
    private String attribute;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", icon_url='" + icon_url + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", attribute='" + attribute + '\'' +
                '}';
    }
}
