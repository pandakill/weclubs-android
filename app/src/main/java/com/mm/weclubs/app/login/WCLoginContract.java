package com.mm.weclubs.app.login;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCUserInfoInfo;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/22 下午1:37
 * 描述:
 */

public interface WCLoginContract{

    interface Presenter<V extends View> extends MVPPresenter<V> {
        /**
         * 通过手机号码和密码登录
         *
         * @param mobile    手机号码
         * @param password  密码
         */
        void login(String mobile, String password);

        void checkLogin();
    }

    interface View extends MVPView{

        void setDefaultMobile(String mobile);

        void loginSuccess(WCUserInfoInfo userInfo);

        void loginFail(String errorMsg);
    }
}
