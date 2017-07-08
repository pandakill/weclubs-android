package com.mm.weclubs.app.register;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.network.pojo.WCUserInfoInfo;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/29 上午12:04
 * 描述:
 */

public interface WCRegisterContract{

    interface Presenter<V extends View> extends MVPPresenter<V> {

        void getSmsCode(String mobile);

        /**
         * 新用户通过手机验证码注册登录
         *
         * @param mobile    手机号码
         * @param code  手机验证码
         * @param password  用户设置的密码
         */
        void register(String mobile, String code, String password);
    }

    interface View extends MVPView{

        void registerSuccess(WCUserInfoInfo userInfo);

        void getCodeSuccess();

        void getCodeFail(String errorMsg);
    }
}
