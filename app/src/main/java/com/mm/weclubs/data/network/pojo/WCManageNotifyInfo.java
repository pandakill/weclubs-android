package com.mm.weclubs.data.network.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午11:49
 * 描述:
 */

public class WCManageNotifyInfo implements Serializable {


    /**
     * unread_count : 1
     * dynamic_type : notify
     * total_count : 2
     * club_id : 2
     * club_avatar : http://www.sise.com.cn/sise/fengmian/fmgs_61/images/neirong/02/tuanwei.jpg
     * club_name : 学院团委
     * create_date : 1491836724154
     * notify_id : 5
     * content : 由于连夜大暴雨，特此通知今天需要继续上课，各位同学请准时上课，安排好时间，上课路上注意安全
     */

    private int unread_count;
    private String dynamic_type;
    private int total_count;
    private long club_id;
    private String club_avatar;
    private String club_name;
    private long create_date;
    private long notify_id;
    private String content;

    public int getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(int unread_count) {
        this.unread_count = unread_count;
    }

    public String getDynamic_type() {
        return dynamic_type;
    }

    public void setDynamic_type(String dynamic_type) {
        this.dynamic_type = dynamic_type;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public long getClub_id() {
        return club_id;
    }

    public void setClub_id(long club_id) {
        this.club_id = club_id;
    }

    public String getClub_avatar() {
        return club_avatar;
    }

    public void setClub_avatar(String club_avatar) {
        this.club_avatar = club_avatar;
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

    public long getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(long notify_id) {
        this.notify_id = notify_id;
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
