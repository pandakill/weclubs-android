package com.mm.weclubs.data.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/13 下午6:24
 * 描述:
 */

public class WCManageMeetingDetailInfo extends WCMeetingDetailInfo implements Serializable {

    private int time_to_sign;
    private int total_count;
    private int unconfirm_count;
    private int already_sign_count;
    private int sign_type;
    private long club_id;
    private String club_name;
    private String avatar_url;

    public int getTime_to_sign() {
        return time_to_sign;
    }

    public void setTime_to_sign(int time_to_sign) {
        this.time_to_sign = time_to_sign;
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

    public int getSign_type() {
        return sign_type;
    }

    public void setSign_type(int sign_type) {
        this.sign_type = sign_type;
    }

    public long getClub_id() {
        return club_id;
    }

    public void setClub_id(long club_id) {
        this.club_id = club_id;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
