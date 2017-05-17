package com.mm.weclubs.data.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/17 下午6:40
 * 描述:
 */

public class WCNotifyRecieveStatusInfo implements Serializable {

    /**
     * student_name : 黄鸡吧
     * is_confirm : 0
     * student_id : 18
     * create_date : 1492503046166
     * student_avatar : http://img32.mtime.cn/up/2012/12/23/030201.11164602_500.jpg
     */

    private String student_name;
    private int is_confirm;
    private long student_id;
    private long create_date;
    private String student_avatar;

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public int getIs_confirm() {
        return is_confirm;
    }

    public void setIs_confirm(int is_confirm) {
        this.is_confirm = is_confirm;
    }

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    public long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }

    public String getStudent_avatar() {
        return student_avatar;
    }

    public void setStudent_avatar(String student_avatar) {
        this.student_avatar = student_avatar;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
