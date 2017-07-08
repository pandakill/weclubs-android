package com.mm.weclubs.app.meeting_detail;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCCommentListInfo;
import com.mm.weclubs.data.network.pojo.WCMeetingDetailInfo;
import com.mm.weclubs.data.network.pojo.WCMeetingListInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/4 上午11:17
 * 描述:
 */

public interface WCMeetingDetailContract{

    interface Presenter<V extends View> extends MVPPresenter<V> {
        void getMeetingDetail(long meetingId);
        void setMeetingConfirm(long meetingId);

        void getCommentList(String type, long sourceId,final int pageNo);
    }

    interface View extends MVPView{

        void refreshCommentList(ArrayList<WCCommentListInfo> list);

        void addCommentList(ArrayList<WCCommentListInfo> list, boolean hasMore);

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
}
