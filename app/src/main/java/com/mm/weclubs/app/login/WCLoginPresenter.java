package com.mm.weclubs.app.login;

import android.content.Context;

import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.app.security.WCHttpParamsPresenter;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.data.pojo.WCUserInfoInfo;
import com.mm.weclubs.retrofit.WCServiceFactory;
import com.mm.weclubs.retrofit.service.WCUserService;
import com.mm.weclubs.util.MD5Util;
import com.mm.weclubs.util.WCLog;

import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/21 下午11:29
 * 描述: 用于登录相关的 presenter
 */

public class WCLoginPresenter extends BasePresenter<WCLoginView> {

    private WCUserService mUserService = null;
    private WCHttpParamsPresenter mHttpParamsPresenter = null;

    public WCLoginPresenter(Context context) {
        super(context);

        mUserService = WCServiceFactory.getUserService();
        mHttpParamsPresenter = new WCHttpParamsPresenter();
    }

    @Override
    public void initLog() {
        log = new WCLog(WCLoginPresenter.class);
    }

    public void login(String mobile, String password) {

        if (EmptyUtils.isEmpty(mobile)) {
            getMvpView().loginFail("手机号码不能为空");
            return;
        }

        if (EmptyUtils.isEmpty(password)) {
            getMvpView().loginFail("密码不能为空");
            return;
        }

        if (!RegexUtils.isMobileSimple(mobile)) {
            getMvpView().loginFail("请输入正确的手机号码");
            return;
        }

        log.d("mobile = " + mobile + "; password = " + password);
        password = MD5Util.md5(password);

        HashMap<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", password);

        mUserService.login(WCUserService.URL_LOGIN, mHttpParamsPresenter.initRequestParam(mContext, params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WCResponseParamBean<WCUserInfoInfo>>() {
                    @Override
                    public void onCompleted() {
                        log.d("login：onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        log.d("login：onError");
                    }

                    @Override
                    public void onNext(WCResponseParamBean<WCUserInfoInfo> userInfo) {
                        log.d("login：onNext = " + userInfo.toString());
                        if (userInfo.getResult_code() == 2000) {
                            getMvpView().loginSuccess(userInfo.getData());
                        } else {
                            getMvpView().loginFail(userInfo.getResult_msg());
                        }
                    }
                });
    }
}
