package com.mm.weclubs.app.manage.notify;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.bean.WCNotifyCheckStatusBean;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午6:52
 * 描述:
 */

public interface WCNotifyReceiveStatusContract {

    interface Presenter<V extends View> extends MVPPresenter<V>{
        void getNotifyConfirmStatusFromServer(long notifyId);
    }

    interface View extends MVPView{

        /**
         * 读取通知确认详情成功
         *
         * @param notifyCheckStatus  通知确认详情列表
         */
        void getNotifyReceiveStatusSuccess(WCNotifyCheckStatusBean notifyCheckStatus);
    }
}
