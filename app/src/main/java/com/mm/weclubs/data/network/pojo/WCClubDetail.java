package com.mm.weclubs.data.network.pojo;

import java.util.List;

/**
 * 创建人： zp
 * 创建时间：2017/7/17
 */

public class WCClubDetail {
    /**
     * 组织id
     */
    private long club_id;
    /**
     * 组织名称
     */
    private String club_name;
    /**
     * 组织logo地址
     */
    private String avatar_url;
    /**
     * 组织等级，0：校级组织，1：院系组织，2：兴趣社团，3：班级组织，4：私下组织
     */
    private int club_level;
    /**
     * 社团口号
     */
    private String slogan;
    /**
     * 组织简介
     */
    private String attribution;
    /**
     * 组织人数
     */
    private int member_count;
    /**
     * 活动人数
     */
    private int activity_count;

    private List<WCStudentInfo> member;
    private List<WCHonorInfo> club_honor;

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

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public int getMember_count() {
        return member_count;
    }

    public void setMember_count(int member_count) {
        this.member_count = member_count;
    }

    public int getActivity_count() {
        return activity_count;
    }

    public void setActivity_count(int activity_count) {
        this.activity_count = activity_count;
    }

    public List<WCStudentInfo> getMember() {
        return member;
    }

    public void setMember(List<WCStudentInfo> member) {
        this.member = member;
    }

    public List<WCHonorInfo> getClub_honor() {
        return club_honor;
    }

    public void setClub_honor(List<WCHonorInfo> club_honor) {
        this.club_honor = club_honor;
    }
}
