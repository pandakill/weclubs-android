package com.mm.weclubs.app.index;

import android.support.annotation.NonNull;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCBannerInfo;
import com.mm.weclubs.data.network.pojo.WCHotClubListInfo;
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

        void startBanner();

        void stopBanner();
    }

    interface View extends MVPView{

        /**
         * 设置首页标题
         * @param schoolName 学校名字
         */
        void setSchoolName(@NonNull String schoolName);

        /**
         * 设置热门社团
         * @param hotClubs 热门社团集合，只有前五个
         */
        void setHotClubs(@NonNull List<WCHotClubListInfo> hotClubs);

        void setBanner(@NonNull List<WCBannerInfo> banners);

        void setData(List<WCIndexClubListInfo> list);

        void addData(List<WCIndexClubListInfo> list, boolean hasMore);

        void loadFail();

        void autoBanner();
    }
}
