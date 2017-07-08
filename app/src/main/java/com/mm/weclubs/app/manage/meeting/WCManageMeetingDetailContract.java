package com.mm.weclubs.app.manage.meeting;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCCommentListInfo;
import com.mm.weclubs.data.network.pojo.WCManageMeetingDetailInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午6:52
 * 描述:
 */

public interface WCManageMeetingDetailContract {

    interface Presenter<V extends View> extends MVPPresenter<V>{

        void getMeetingDetail(long meetingId);

        void getCommentList(String type, long sourceId, final int pageNo);
    }

    interface View extends MVPView{
        void refreshCommentList(ArrayList<WCCommentListInfo> list);

        void addCommentList(ArrayList<WCCommentListInfo> list, boolean hasMore);

        void getMeetingDetailSuccess(WCManageMeetingDetailInfo meetingDetailInfo);
    }
}
