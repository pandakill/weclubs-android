package com.mm.weclubs.data.network.pojo;

/**
 * 创建人： zp
 * 创建时间：2017/7/12
 */

public class WCHotClubListInfo {

    /**
     * avatar_url : http://p2.wmpic.me/article/2015/03/16/1426483393_DXGAJIiR.jpeg
     * club_id : 1
     * club_name : 社团联合会
     */

    private String avatar_url;
    private int club_id;
    private String club_name;

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getClub_id() {
        return club_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }
}
