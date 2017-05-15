package com.mm.weclubs.data.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午5:50
 * 描述:  对应 /manage/club_meeting/get_my_meeting 接口
 */

public class WCManageMeetingInfo implements Serializable {

    /**
     * time_to_sign : 2
     * address : 广大华软 -- D101 教室
     * meeting_id : 6
     * total_count : 1
     * unconfirm_count : 1
     * already_sign_count : 0
     * club_avatar : http://p2.wmpic.me/article/2015/03/16/1426483393_DXGAJIiR.jpeg
     * club_name : 社团联合会
     * content : 今晚全体成员和各社团主席在 D101 教室召开新学期社团招新动员大会。
     * club_id : 1
     * create_date : 1491836724154
     * deadline : 1491836724154
     * sign_type : 1
     */

    private int time_to_sign;
    private String address;
    private long meeting_id;
    private int total_count;
    private int unconfirm_count;
    private int already_sign_count;
    private String club_avatar;
    private String club_name;
    private String content;
    private long club_id;
    private long create_date;
    private long deadline;
    private int sign_type;

    public int getTime_to_sign() {
        return time_to_sign;
    }

    public void setTime_to_sign(int time_to_sign) {
        this.time_to_sign = time_to_sign;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(long meeting_id) {
        this.meeting_id = meeting_id;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getUnconfirm_count() {
        return unconfirm_count;
    }

    public void setUnconfirm_count(int unconfirm_count) {
        this.unconfirm_count = unconfirm_count;
    }

    public int getAlready_sign_count() {
        return already_sign_count;
    }

    public void setAlready_sign_count(int already_sign_count) {
        this.already_sign_count = already_sign_count;
    }

    public String getClub_avatar() {
        return club_avatar;
    }

    public void setClub_avatar(String club_avatar) {
        this.club_avatar = club_avatar;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getClub_id() {
        return club_id;
    }

    public void setClub_id(long club_id) {
        this.club_id = club_id;
    }

    public long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public int getSign_type() {
        return sign_type;
    }

    public void setSign_type(int sign_type) {
        this.sign_type = sign_type;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
