package com.mm.weclubs.app.notify_detail;

import android.support.annotation.NonNull;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCCommentListInfo;
import com.mm.weclubs.data.network.pojo.WCNotifyListInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/22 下午1:37
 * 描述:
 */

public interface WCNotifyDetailContract{

    interface Presenter<V extends View> extends MVPPresenter<V> {
        void getNotifyDetail(long notifyId);
        void setNotifyConfirm(long notifyId);

        void getCommentList(String type, long sourceId,final int pageNo);
    }

    interface View extends MVPView{

        void getNotifyDetailSuccess(@NonNull WCNotifyListInfo wcNotifyListInfo);

        void refreshCommentList(ArrayList<WCCommentListInfo> list);

        void addCommentList(ArrayList<WCCommentListInfo> list, boolean hasMore);

        /**
         * 刷新任务列表
         *
         * @param notifyListInfo   通知实体
         * @param position  定位
         */
        void notifyChangeList(WCNotifyListInfo notifyListInfo, int position);
    }
}
