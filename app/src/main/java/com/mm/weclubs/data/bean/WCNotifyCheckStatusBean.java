package com.mm.weclubs.data.bean;

import com.mm.weclubs.data.pojo.WCNotifyRecieveStatusInfo;
import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/17 下午7:08
 * 描述:  通知查看详情列表接口
 */

public class WCNotifyCheckStatusBean implements Serializable {

    private int unread_count;
    private int total_count;
    private ArrayList<WCNotifyRecieveStatusInfo> confirm_status;

    public int getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(int unread_count) {
        this.unread_count = unread_count;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public ArrayList<WCNotifyRecieveStatusInfo> getConfirm_status() {
        return confirm_status;
    }

    public void setConfirm_status(ArrayList<WCNotifyRecieveStatusInfo> confirm_status) {
        this.confirm_status = confirm_status;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
