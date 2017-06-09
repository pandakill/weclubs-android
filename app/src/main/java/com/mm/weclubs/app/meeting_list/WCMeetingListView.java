package com.mm.weclubs.app.meeting_list;

import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.pojo.WCMeetingDetailInfo;
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

    /**
     * 获取会议详情接口请求成功
     *
     * @param meetingDetailInfo 请求成功得到的会议详情
     */
    void getMeetingDetailSuccess(WCMeetingDetailInfo meetingDetailInfo);

    /**
     * 刷新会议列表
     *
     * @param meetingListInfo   会议实体
     * @param position  定位
     */
    void notifyChangeList(WCMeetingListInfo meetingListInfo, int position);
}
