package com.mm.weclubs.data.network.pojo;

import com.mm.weclubs.util.JsonHelper;

import java.io.Serializable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 下午4:13
 * 描述:
 */

public class WCMyClubListInfo implements Serializable {

    private static final long serialVersionUID = 1024L;

    private long club_id;
    private String club_name;
    private String avatar_url;
    private int club_level;
    private long member_count;
    private long todo_count;
    private long activity_count;

    public long getClub_id() {
        return club_id;
    }

    public void setClub_id(long club_id) {
        this.club_id = club_id;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getClub_level() {
        return club_level;
    }

    public void setClub_level(int club_level) {
        this.club_level = club_level;
    }

    public long getMember_count() {
        return member_count;
    }

    public void setMember_count(long member_count) {
        this.member_count = member_count;
    }

    public long getTodo_count() {
        return todo_count;
    }

    public void setTodo_count(long todo_count) {
        this.todo_count = todo_count;
    }

    public long getActivity_count() {
        return activity_count;
    }

    public void setActivity_count(long activity_count) {
        this.activity_count = activity_count;
    }

    @Override
    public String toString() {
        return JsonHelper.getJsonStrFromObj(this);
    }
}
