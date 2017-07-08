package com.mm.weclubs.app.club;

import android.support.annotation.NonNull;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCMyClubListInfo;

import java.util.List;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 下午5:04
 * 描述:
 */

public interface WCMyClubListContract{

    interface Presenter<V extends View> extends MVPPresenter<V> {

        void refresh();

        void getMyClubsList(final int pageNo);
    }

    interface View extends MVPView{

        void setClubList(@NonNull List<WCMyClubListInfo> data);

        void loadFail();

        void addClubList(@NonNull List<WCMyClubListInfo> list, boolean hasMore);
    }


}
