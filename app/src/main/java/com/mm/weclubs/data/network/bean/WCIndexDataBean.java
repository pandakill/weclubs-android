package com.mm.weclubs.data.network.bean;

import com.mm.weclubs.data.network.pojo.WCBannerInfo;
import com.mm.weclubs.data.network.pojo.WCHotClubListInfo;

import java.util.List;

/**
 * 创建人： zp
 * 创建时间：2017/7/12
 */

public class WCIndexDataBean {
    private List<WCHotClubListInfo> hot_club;
    private List<WCBannerInfo> banner;

    public List<WCHotClubListInfo> getHot_club() {
        return hot_club;
    }

    public void setHot_club(List<WCHotClubListInfo> hot_club) {
        this.hot_club = hot_club;
    }

    public List<WCBannerInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<WCBannerInfo> banner) {
        this.banner = banner;
    }
}
