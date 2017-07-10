package com.mm.weclubs.app.index;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCIndexClubListInfo;

import java.util.List;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/22 下午1:37
 * 描述:
 */

public interface WCIndexContract {

    interface Presenter<V extends View> extends MVPPresenter<V> {
        void refresh();

        void getClubListFromServer(final int pageNo);
    }

    interface View extends MVPView{

        void setData(List<WCIndexClubListInfo> list);

        void addData(List<WCIndexClubListInfo> list, boolean hasMore);

        void loadFail();
    }
}
