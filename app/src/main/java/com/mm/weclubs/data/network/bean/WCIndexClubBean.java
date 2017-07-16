package com.mm.weclubs.data.network.bean;

import com.mm.weclubs.data.network.pojo.WCIndexClubListInfo;

import java.util.List;

/**
 * 文 件 名: WCIndexClubBean
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/10 22:18
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public class WCIndexClubBean {
    private int is_more;
    private List<WCIndexClubListInfo> club_list;

    public int getIs_more() {
        return is_more;
    }

    public void setIs_more(int is_more) {
        this.is_more = is_more;
    }

    public List<WCIndexClubListInfo> getClub_list() {
        return club_list;
    }

    public void setClub_list(List<WCIndexClubListInfo> club_list) {
        this.club_list = club_list;
    }
}
