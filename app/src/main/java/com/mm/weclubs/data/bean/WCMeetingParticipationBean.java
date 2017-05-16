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

    public ArrayList<WCMeetingParticipationInfo> getParticipation() {
        return participation;
    }

    public void setParticipation(ArrayList<WCMeetingParticipationInfo> participation) {
        this.participation = participation;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
