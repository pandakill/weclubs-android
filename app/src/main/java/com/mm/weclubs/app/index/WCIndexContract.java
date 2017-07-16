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

        /**
         * 点击 "扫一扫" 后跳转到 <二维码扫描>
         */
        void scan();

        /**
         * 点击 "搜索" 后, 跳转到 <首页搜索>
         */
        void search();

        /**
         * 点击某一个社团后, 跳转到 <社团详情>
         */
        void club(long club_id);

        /**
         * 点击社团成员头像区域，跳转到<社团成员>
         */
        void moreStudent(long club_id);
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

        void showAuthDialog();

        void openScanActivity();

        void openSearchActivity();

        void openClubDetailActivity(long club_id);

        void openStudentListActivity(long club_id);
    }
}
