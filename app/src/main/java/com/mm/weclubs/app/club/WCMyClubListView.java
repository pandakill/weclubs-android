package com.mm.weclubs.app.club;

import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.pojo.WCMyClubListInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 下午5:04
 * 描述:
 */

public interface WCMyClubListView extends MVPView {

    void refreshClubList(ArrayList<WCMyClubListInfo> list);

    void addClubList(ArrayList<WCMyClubListInfo> list, boolean hasMore);
}
