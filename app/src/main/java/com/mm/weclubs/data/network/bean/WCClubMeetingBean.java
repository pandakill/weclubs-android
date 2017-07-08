package com.mm.weclubs.data.network.bean;

import com.mm.weclubs.data.network.pojo.WCManageMeetingInfo;
import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午5:49
 * 描述:
 */

public class WCClubMeetingBean implements Serializable {

    private int has_more;
    private ArrayList<WCManageMeetingInfo> meeting;

    public int getHas_more() {
        return has_more;
    }

    public void setHas_more(int has_more) {
        this.has_more = has_more;
    }

    public ArrayList<WCManageMeetingInfo> getMeeting() {
        return meeting;
    }

    public void setMeeting(ArrayList<WCManageMeetingInfo> meeting) {
        this.meeting = meeting;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
