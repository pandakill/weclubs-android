package com.mm.weclubs.app.mine;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/22 下午1:37
 * 描述:
 */

public interface WCMineContract {

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
