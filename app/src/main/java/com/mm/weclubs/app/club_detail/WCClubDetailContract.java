package com.mm.weclubs.app.club_detail;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;

public interface WCClubDetailContract {

    interface Presenter<V extends View> extends MVPPresenter<V> {

    }

    interface View extends MVPView{

        void setAvatar(String path);

        void setName(String name);

        /**
         *
         * @param info
         * @param gender 0 女生 1 男生 2 未知
         */
        void setInfo(String info, int gender);

        /**
         *
         * @param auth 认证信息
         */
        void setAuth(String auth);
    }
}
