package com.mm.weclubs.data.network.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/16 上午1:20
 * 描述:
 */

public class WCManageMissionInfo implements Serializable {

    /**
     * child_count : 0
     * club_id : 1
     * unconfirm_count : 0
     * mission_id : 2
     * club_avatar : http://p2.wmpic.me/article/2015/03/16/1426483393_DXGAJIiR.jpeg
     * has_child : 0
     * club_name : 社团联合会
     * create_date : 1491836724154
     * deadline : 1491836724154
     * unfinish_count : 0
     * content : 女生节摆摊活动
     */

    private int child_count;
    private long club_id;
    private int unconfirm_count;
    private long mission_id;
    private String club_avatar;
    private int has_child;
    private String club_name;
    private long create_date;
    private long deadline;
    private int unfinish_count;
    private String content;

    public int getChild_count() {
        return child_count;
    }

    public void setChild_count(int child_count) {
        this.child_count = child_count;
    }

    public long getClub_id() {
        return club_id;
    }

    public void setClub_id(long club_id) {
        this.club_id = club_id;
    }

    public int getUnconfirm_count() {
        return unconfirm_count;
    }

    public void setUnconfirm_count(int unconfirm_count) {
        this.unconfirm_count = unconfirm_count;
    }

    public long getMission_id() {
        return mission_id;
    }

    public void setMission_id(long mission_id) {
        this.mission_id = mission_id;
    }

    public String getClub_avatar() {
        return club_avatar;
    }

    public void setClub_avatar(String club_avatar) {
        this.club_avatar = club_avatar;
    }

    public int getHas_child() {
        return has_child;
    }

    public void setHas_child(int has_child) {
        this.has_child = has_child;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public int getUnfinish_count() {
        return unfinish_count;
    }

    public void setUnfinish_count(int unfinish_count) {
        this.unfinish_count = unfinish_count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
