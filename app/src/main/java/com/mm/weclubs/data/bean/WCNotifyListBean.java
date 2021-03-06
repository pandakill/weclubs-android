package com.mm.weclubs.data.bean;

import com.mm.weclubs.data.pojo.WCNotifyListInfo;
import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 下午3:22
 * 描述:
 */

public class WCNotifyListBean implements Serializable {

    private static final long serialVersionUID = 1024L;

    private int has_more;
    private ArrayList<WCNotifyListInfo> notify;

    public ArrayList<WCNotifyListInfo> getNotify() {
        return notify;
    }

    public void setNotify(ArrayList<WCNotifyListInfo> notify) {
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
