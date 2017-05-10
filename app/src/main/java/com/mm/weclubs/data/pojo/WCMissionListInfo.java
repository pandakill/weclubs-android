package com.mm.weclubs.data.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/4 下午11:05
 * 描述:
 */

public class WCMissionListInfo extends WCDynamicBaseInfo implements Serializable {

    private static final long serialVersionUID = 1024L;

    private long mission_id;
    private String content;
    private long create_date;
    private long confirm_date;
    private long deadline;
    private int finish;

    public long getMission_id() {
        return mission_id;
    }

    public void setMission_id(long mission_id) {
        this.mission_id = mission_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }

    public long getConfirm_date() {
        return confirm_date;
    }

    public void setConfirm_date(long confirm_date) {
        this.confirm_date = confirm_date;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
