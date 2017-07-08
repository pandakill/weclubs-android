package com.mm.weclubs.app.manage.meeting;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.bean.WCMeetingParticipationBean;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午6:52
 * 描述:
 */

public interface WCMeetingParticipationDetailContract{

    interface Presenter<V extends View> extends MVPPresenter<V>{
        /**
         * 读取会议参与详情
         *
         * @param meetingId 会议id
         */
        void getMeetingParticipation(long meetingId);
    }

    interface View extends MVPView {

        /**
         * 读取会议参与详情成功
         *
         * @param participationBean  参与详情实体
         */
        void getMeetingParticipationSuccess(WCMeetingParticipationBean participationBean);
    }
}
