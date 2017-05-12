package com.mm.weclubs.app.meeting_list;

import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.pojo.WCMeetingListInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/4 上午11:17
 * 描述:
 */

public interface WCMeetingListView extends MVPView {

    void refreshMeetingList(ArrayList<WCMeetingListInfo> list);

    void addMeetingList(ArrayList<WCMeetingListInfo> list, boolean hasMore);
}
