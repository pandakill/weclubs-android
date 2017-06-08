package com.mm.weclubs.app.login;

import android.content.Context;

import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.data.pojo.WCUserInfoInfo;
import com.mm.weclubs.datacenter.WCUserDataCenter;
import com.mm.weclubs.retrofit.WCServiceFactory;
import com.mm.weclubs.retrofit.service.WCUserService;
import com.mm.weclubs.util.MD5Util;
import com.mm.weclubs.util.WCLog;

import java.util.HashMap;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
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
    private WCUserDataCenter mUserDataCenter;

    public WCLoginPresenter(Context context) {
        super(context);

        mUserService = WCServiceFactory.getUserService();
        mUserDataCenter = WCUserDataCenter.getInstance(mContext);
    }

    @Override
    public void initLog() {
        log = new WCLog(WCLoginPresenter.class);
    }

    /**
     * 通过手机号码和密码登录
     *
     * @param mobile    手机号码
     * @param password  密码
     */
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

        getMvpView().showProgressDialog("登录中...", false);

        log.d("mobile = " + mobile + "; password = " + password);
        password = MD5Util.md5(password);

        HashMap<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", password);

        mUserService.login(WCUserService.URL_LOGIN, mHttpParamsPresenter.initRequestParam(mContext.getApplicationContext(), params))
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

                        getMvpView().loginFail(e.getMessage());
                        getMvpView().hideProgressDialog();
                    }

                    @Override
                    public void onNext(WCResponseParamBean<WCUserInfoInfo> userInfo) {
                        log.d("login：onNext = " + userInfo.toString());
                        if (userInfo.getResult_code() == 2000) {
                            mUserDataCenter.saveUserInfo(userInfo.getData());
                            getMvpView().loginSuccess(userInfo.getData());

                            String alias = "user_" + userInfo.getData().getUser_id();
                            JPushInterface.setAlias(mContext, alias, new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {
                                    log.d("setAlias i = " + i + "; s = " + s + "; set = " + (set != null ? set.toString() : "null"));
                                }
                            });
                        } else {
                            getMvpView().loginFail(userInfo.getResult_msg());
                            mUserDataCenter.deleteUserInfo();
                        }

                        getMvpView().hideProgressDialog();
                    }
                });
    }

    /**
     * 新用户通过手机验证码注册登录
     *
     * @param mobile    手机号码
     * @param code  手机验证码
     * @param password  用户设置的密码
     */
    public void register(String mobile, String code, String password) {

        if (!RegexUtils.isMobileSimple(mobile)) {
            log.d("手机验证错误：" + mobile);
            getMvpView().showToast("请输入正确的手机号码");
            return;
        }

        if (EmptyUtils.isEmpty(code)) {
            log.d("验证码不能为空");
            getMvpView().showToast("验证码不能为空");
            return;
        }

        if (code.length() != 4) {
            log.d("输入验证码格式错误" + code);
            getMvpView().showToast("请输入正确的验证码");
            return;
        }

        try {
            int codeNumber = Integer.parseInt(code);
            log.d("手机验证码转为数字之后为：" + codeNumber);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            getMvpView().showToast("请输入正确的验证码");
            return;
        }

        if (EmptyUtils.isEmpty(password)) {
            log.d("密码输入为空");
            getMvpView().showToast("请输入密码");
            return;
        }

        getMvpView().showProgressDialog("注册中...", false);

        HashMap<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);

        password = MD5Util.md5(password);
        params.put("password", password);
        params.put("code", code);

        mUserService.register(WCUserService.URL_REGISTER, mHttpParamsPresenter.initRequestParam(mContext.getApplicationContext(), params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WCResponseParamBean<WCUserInfoInfo>>() {
                    @Override
                    public void onCompleted() {
                        log.d("register：onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        log.d("register：onError");

                        getMvpView().hideProgressDialog();
                    }

                    @Override
                    public void onNext(WCResponseParamBean<WCUserInfoInfo> userInfo) {
                        log.d("register：onNext = " + userInfo.toString());
                        if (userInfo.getResult_code() == 2000) {
                            mUserDataCenter.saveUserInfo(userInfo.getData());
                            getMvpView().registerSuccess(userInfo.getData());
                        } else {
                            getMvpView().showToast(userInfo.getResult_msg());

                            checkResult(userInfo);
                        }

                        getMvpView().hideProgressDialog();
                    }
                });

    }

    public void checkLogin() {

        WCUserInfoInfo userInfoInfo = mUserDataCenter.getCurrentUserInfo();

        if (userInfoInfo != null) {

            getMvpView().showProgressDialog("登录中...", false);

            HashMap<String, Object> params = new HashMap<>();
            params.put("mobile", userInfoInfo.getMobile());
            params.put("token", userInfoInfo.getToken());

            mUserService.getUserInfo(WCUserService.URL_GET_USER_INFO, mHttpParamsPresenter.initRequestParam(mContext, params))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WCResponseParamBean<WCUserInfoInfo>>() {
                        @Override
                        public void onCompleted() {
                            log.d("checkLogin：onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            log.d("checkLogin：onError - " + e.getMessage());
                            getMvpView().hideProgressDialog();
                        }

                        @Override
                        public void onNext(WCResponseParamBean<WCUserInfoInfo> userInfo) {
                            log.d("checkLogin：onNext = " + userInfo.toString());
                            if (userInfo.getResult_code() == 2000) {
                                userInfo.getData().setToken(mUserDataCenter.getCurrentUserInfo().getToken());
                                mUserDataCenter.saveUserInfo(userInfo.getData());
                                getMvpView().loginSuccess(userInfo.getData());
                            } else {
                                mUserDataCenter.deleteUserInfo();
                                log.d("checkLogin：获取用户信息失败 - " + userInfo.getResult_msg());
                            }

                            getMvpView().hideProgressDialog();
                        }
                    });
        }
    }
}
