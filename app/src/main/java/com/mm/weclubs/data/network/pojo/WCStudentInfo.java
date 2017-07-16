package com.mm.weclubs.data.network.pojo;

/**
 * 文 件 名: WCStudentInfo
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/10 22:21
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public class WCStudentInfo {
    private String student_name;
    private int student_id;
    private String avatar_url;

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
