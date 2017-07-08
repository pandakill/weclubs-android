package com.mm.weclubs.app.mission_list;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCMissionListInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/4 上午11:17
 * 描述:
 */

public interface WCMissionListContract{

    interface Presenter<V extends View> extends MVPPresenter<V> {

        void refresh(long clubId);

        void getMissionListFromServer(long clubId,final int pageNo);

        void setMissionConfirm(long missionId,final  int position,final  WCMissionListInfo missionListInfo);
    }

    interface View extends MVPView{
        void refreshMissionList(ArrayList<WCMissionListInfo> list);

        void addMissionList(ArrayList<WCMissionListInfo> list, boolean hasMore);

        void loadFail();

        /**
         * 刷新任务列表
         *
         * @param missionListInfo   任务实体
         * @param position  定位
         */
        void notifyChangeList(WCMissionListInfo missionListInfo, int position);
    }
}
