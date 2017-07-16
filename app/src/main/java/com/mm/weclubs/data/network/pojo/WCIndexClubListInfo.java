package com.mm.weclubs.data.network.pojo;

import java.util.List;

/**
 * 文 件 名: WCIndexClubListInfo
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/10 22:19
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public class WCIndexClubListInfo {
    /**
     * 社团id
     */
    private long club_id;
    /**
     * 社团名称
     */
    private String club_name;
    /**
     * 社团头像地址
     */
    private String avatar_url;
    /**
     * 社团等级，0：校级组织，1：院系组织，2：兴趣社团，3：班级组织，4：私下组织
     */
    private int level;
    /**
     * 社团口号
     */
    private String slogan;
    /**
     * 社团简介
     */
    private String attribution;
    /**
     * 社团的学生
     */
    private List<WCStudentInfo> student;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public List<WCStudentInfo> getStudent() {
        return student;
    }

    public void setStudent(List<WCStudentInfo> student) {
        this.student = student;
    }
}
