package com.mm.weclubs.app.meeting_list;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCMeetingListInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/4 上午11:17
 * 描述:
 */

public interface WCMeetingListContract extends MVPView {

    interface Presenter<V extends View> extends MVPPresenter<V> {

        void refresh(long clubId);

        void getMeetingListFromServer(long clubId, final int pageNo);

        void setMeetingConfirm(long meetingId,final int position,final WCMeetingListInfo meetingListInfo);
    }

    interface View extends MVPView{
        void refreshMeetingList(ArrayList<WCMeetingListInfo> list);

        void addMeetingList(ArrayList<WCMeetingListInfo> list, boolean hasMore);

        void loadFail();

        /**
         * 刷新会议列表
         *
         * @param meetingListInfo   会议实体
         * @param position  定位
         */
        void notifyChangeList(WCMeetingListInfo meetingListInfo, int position);
    }
}
