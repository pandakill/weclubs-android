package com.mm.weclubs.data.network.bean;

import com.mm.weclubs.data.network.pojo.WCMyClubListInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 下午4:12
 * 描述:
 */

public class WCMyClubsBean implements Serializable {

    private static final long serialVersionUID = 1024L;

    private int has_more;
    private ArrayList<WCMyClubListInfo> club;

    public int getHas_more() {
        return has_more;
    }

    public void setHas_more(int has_more) {
        this.has_more = has_more;
    }

    public ArrayList<WCMyClubListInfo> getClub() {
        return club;
    }

    public void setClub(ArrayList<WCMyClubListInfo> club) {
        this.club = club;
    }
}
