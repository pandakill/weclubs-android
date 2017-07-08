package com.mm.weclubs.app.mission_detail;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCCommentListInfo;
import com.mm.weclubs.data.network.pojo.WCMissionDetailInfo;
import com.mm.weclubs.data.network.pojo.WCMissionListInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/22 下午1:37
 * 描述:
 */

public interface WCMissionDetailContract {

    interface Presenter<V extends View> extends MVPPresenter<V> {
        void getMissionDetail(long missionId);
        void setMissionConfirm(long missionId);

        void getCommentList(String type, long sourceId,final int pageNo);
    }

    interface View extends MVPView{

        void refreshCommentList(ArrayList<WCCommentListInfo> list);

        void addCommentList(ArrayList<WCCommentListInfo> list, boolean hasMore);

        /**
         * 获取任务详情接口请求成功
         *
         * @param missionDetailInfo 请求成功得到的任务详情
         */
        void getMissionDetailSuccess(WCMissionDetailInfo missionDetailInfo);

        /**
         * 刷新任务列表
         *
         * @param missionListInfo   任务实体
         * @param position  定位
         */
        void notifyChangeList(WCMissionListInfo missionListInfo, int position);
    }
}
