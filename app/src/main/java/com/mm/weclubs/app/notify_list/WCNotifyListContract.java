package com.mm.weclubs.app.notify_list;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCNotifyListInfo;

import java.util.List;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/12 下午5:33
 * 描述:
 */

public interface WCNotifyListContract extends MVPView {

    interface Presenter<V extends View> extends MVPPresenter<V> {

        void refresh(long clubId);

        void getNotifyFromServer(long clubId,final  int pageNo);

        void setNotifyConfirm(long notifyId,final  int position,final  WCNotifyListInfo notifyListInfo);
    }

    interface View extends MVPView{
        void refreshNotifyList(List<WCNotifyListInfo> list);

        void addNotifyList(List<WCNotifyListInfo> list, boolean hasMore);

        void loadFail();
        /**
         * 刷新任务列表
         *
         * @param notifyListInfo   通知实体
         * @param position  定位
         */
        void notifyChangeList(WCNotifyListInfo notifyListInfo, int position);
    }
}
