package com.mm.weclubs.app.manage.notify;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCManageNotifyInfo;

import java.util.ArrayList;

/**
 * 文 件 名: WCManageNotifyListContract
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/8 14:20
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public interface WCManageNotifyListContract {

    interface Presenter<V extends View> extends MVPPresenter<V> {
        void refresh();
        void getNotifyListFromServer(final int pageNo);
    }

    interface View extends MVPView {
        void refreshNotifyList(ArrayList<WCManageNotifyInfo> list);

        void addNotifyList(ArrayList<WCManageNotifyInfo> list, boolean hasMore);

        void loadFail();
    }
}
