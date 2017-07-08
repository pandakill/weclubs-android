package com.mm.weclubs.data.network.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/17 上午12:06
 * 描述:
 */

public class WCMeetingParticipationInfo implements Serializable {

    /**
     * avatar_url : null
     * job_name : 部长
     * is_confirm : 0
     * is_leave : 0
     * department_name : 生活部
     * name : 方赞潘
     * sign_date :
     * student_id : 1
     * is_sign : 0
     * dynamic_date :
     */

    private String avatar_url;
    private String job_name;
    private int is_confirm;
    private int is_leave;
    private String department_name;
    private String name;
    private String sign_date;
    private long student_id;
    private int is_sign;
    private int is_late;
    private long dynamic_date;
    private String comment;

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public int getIs_confirm() {
        return is_confirm;
    }

    public void setIs_confirm(int is_confirm) {
        this.is_confirm = is_confirm;
    }

    public int getIs_leave() {
        return is_leave;
    }

    public void setIs_leave(int is_leave) {
        this.is_leave = is_leave;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign_date() {
        return sign_date;
    }

    public void setSign_date(String sign_date) {
        this.sign_date = sign_date;
    }

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    public int getIs_sign() {
        return is_sign;
    }

    public void setIs_sign(int is_sign) {
        this.is_sign = is_sign;
    }

    public long getDynamic_date() {
        return dynamic_date;
    }

    public void setDynamic_date(long dynamic_date) {
        this.dynamic_date = dynamic_date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getIs_late() {
        return is_late;
    }

    public void setIs_late(int is_late) {
        this.is_late = is_late;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
