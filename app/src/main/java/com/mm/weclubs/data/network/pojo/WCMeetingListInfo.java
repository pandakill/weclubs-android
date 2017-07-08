package com.mm.weclubs.data.network.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 上午12:12
 * 描述:
 */

public class WCMeetingListInfo extends WCDynamicBaseInfo implements Serializable {

    private static final long serialVersionUID = 1024L;

    private long meeting_id;
    private long deadline;
    private long create_date;
    private String content;
    private String address;
    private int confirm_join;
    private int has_sign;

    public long getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(long meeting_id) {
        this.meeting_id = meeting_id;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getConfirm_join() {
        return confirm_join;
    }

    public void setConfirm_join(int confirm_join) {
        this.confirm_join = confirm_join;
    }

    public int getHas_sign() {
        return has_sign;
    }

    public void setHas_sign(int has_sign) {
        this.has_sign = has_sign;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
