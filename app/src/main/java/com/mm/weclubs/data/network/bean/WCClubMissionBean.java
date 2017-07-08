package com.mm.weclubs.data.network.bean;

import com.mm.weclubs.data.network.pojo.WCManageMissionInfo;
import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/16 上午1:19
 * 描述:
 */

public class WCClubMissionBean implements Serializable {

    private int has_more;
    private ArrayList<WCManageMissionInfo> mission;

    public int getHas_more() {
        return has_more;
    }

    public void setHas_more(int has_more) {
        this.has_more = has_more;
    }

    public ArrayList<WCManageMissionInfo> getMission() {
        return mission;
    }

    public void setMission(ArrayList<WCManageMissionInfo> mission) {
        this.mission = mission;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
