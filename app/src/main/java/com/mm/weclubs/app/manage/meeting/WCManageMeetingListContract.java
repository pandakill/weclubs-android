package com.mm.weclubs.app.manage.meeting;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCManageMeetingInfo;

import java.util.List;

/**
 * 文 件 名: WCManageNotifyListContract
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/8 14:20
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public interface WCManageMeetingListContract {

    interface Presenter<V extends View> extends MVPPresenter<V> {
        void refresh();
        void getMeetingListFromServer(final int pageNo);
    }

    interface View extends MVPView {
        void refreshMeetingList(List<WCManageMeetingInfo> list);

        void addMeetingList(List<WCManageMeetingInfo> list, boolean hasMore);

        void loadFail();
    }
}
