package com.mm.weclubs.data.network.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 上午12:25
 * 描述:
 */

public class WCNotifyListInfo extends WCDynamicBaseInfo implements Serializable {

    private static final long serialVersionUID = 1024L;

    private long notify_id;
    private long create_date;
    private String content;
    private int confirm_receive;

    public long getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(long notify_id) {
        this.notify_id = notify_id;
    }

    public long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getConfirm_receive() {
        return confirm_receive;
    }

    public void setConfirm_receive(int confirm_receive) {
        this.confirm_receive = confirm_receive;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
