package com.mm.weclubs.app.manage.meeting;

import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.pojo.WCManageMeetingDetailInfo;
import com.mm.weclubs.data.pojo.WCManageMeetingInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午6:52
 * 描述:
 */

public interface WCManageMeetingView extends MVPView {

    void refreshMeetingList(ArrayList<WCManageMeetingInfo> list);

    void addMeetingList(ArrayList<WCManageMeetingInfo> list, boolean hasMore);

    void getMeetingDetailSuccess(WCManageMeetingDetailInfo meetingDetailInfo);
}
