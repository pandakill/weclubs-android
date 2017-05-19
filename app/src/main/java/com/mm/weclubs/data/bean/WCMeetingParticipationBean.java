package com.mm.weclubs.data.bean;

import com.mm.weclubs.data.pojo.WCMeetingParticipationInfo;
import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/17 上午12:05
 * 描述:
 */

public class WCMeetingParticipationBean implements Serializable {

    private ArrayList<WCMeetingParticipationInfo> participation;
    private int total_count;
    private int unsign_count;
    private int already_sign_count;
    private int unconfirm_count;
    private int already_confirm_count;

    public ArrayList<WCMeetingParticipationInfo> getParticipation() {
        return participation;
    }

    public void setParticipation(ArrayList<WCMeetingParticipationInfo> participation) {
        this.participation = participation;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getUnsign_count() {
        return unsign_count;
    }

    public void setUnsign_count(int unsign_count) {
        this.unsign_count = unsign_count;
    }

    public int getAlready_sign_count() {
        return already_sign_count;
    }

    public void setAlready_sign_count(int already_sign_count) {
        this.already_sign_count = already_sign_count;
    }

    public int getUnconfirm_count() {
        return unconfirm_count;
    }

    public void setUnconfirm_count(int unconfirm_count) {
        this.unconfirm_count = unconfirm_count;
    }

    public int getAlready_confirm_count() {
        return already_confirm_count;
    }

    public void setAlready_confirm_count(int already_confirm_count) {
        this.already_confirm_count = already_confirm_count;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
