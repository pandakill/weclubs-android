package com.mm.weclubs.data.network.bean;

import com.mm.weclubs.data.network.pojo.WCManageNotifyInfo;
import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午11:50
 * 描述:
 */

public class WCClubNotifyBean implements Serializable {

    private ArrayList<WCManageNotifyInfo> notify;
    private int has_more;

    public ArrayList<WCManageNotifyInfo> getNotify() {
        return notify;
    }

    public void setNotify(ArrayList<WCManageNotifyInfo> notify) {
        this.notify = notify;
    }

    public int getHas_more() {
        return has_more;
    }

    public void setHas_more(int has_more) {
        this.has_more = has_more;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
