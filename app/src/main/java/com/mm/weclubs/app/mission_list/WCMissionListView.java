package com.mm.weclubs.app.mission_list;

import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.pojo.WCMissionListInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/4 上午11:17
 * 描述:
 */

public interface WCMissionListView extends MVPView {

    void refreshMissionList(ArrayList<WCMissionListInfo> list);

    void addMissionList(ArrayList<WCMissionListInfo> list, boolean hasMore);
}
