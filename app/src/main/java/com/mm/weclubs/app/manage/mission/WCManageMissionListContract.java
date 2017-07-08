package com.mm.weclubs.app.manage.mission;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCManageMissionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午6:52
 * 描述:
 */

public interface WCManageMissionListContract{

    interface Presenter<V extends View> extends MVPPresenter<V>{
        void getMissionListFromServer(final int pageNo);

        void refresh();
    }

    interface View extends MVPView{

        void loadFail();

        void refreshMissionList(List<WCManageMissionInfo> list);

        void addMissionList(ArrayList<WCManageMissionInfo> list, boolean hasMore);
    }
}
