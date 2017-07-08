package com.mm.weclubs.app.register;

import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.RegexUtils;
import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.network.pojo.WCUserInfoInfo;
import com.mm.weclubs.util.DataHelper;
import com.mm.weclubs.util.MD5Util;
import com.mm.weclubs.util.rx.SchedulerProvider;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.inject.Inject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/29 上午12:03
 * 描述:
 */

public class WCRegisterPresenter<V extends WCRegisterContract.View> extends BasePresenter<V>
    implements WCRegisterContract.Presenter<V>{

    private EventHandler mMsgEventHandler = null;

    @Inject
    public WCRegisterPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    /**
     * 新用户通过手机验证码注册登录
     *
     * @param mobile    手机号码
     * @param code  手机验证码
     * @param password  用户设置的密码
     */
    @Override
    public void register(String mobile, String code, String password) {

        if (!RegexUtils.isMobileSimple(mobile)) {
            KLog.d("手机验证错误：" + mobile);
            getMvpView().showToast("请输入正确的手机号码");
            return;
        }

        if (EmptyUtils.isEmpty(code)) {
            KLog.d("验证码不能为空");
            getMvpView().showToast("验证码不能为空");
            return;
        }

        if (code.length() != 4) {
            KLog.d("输入验证码格式错误" + code);
            getMvpView().showToast("请输入正确的验证码");
            return;
        }

        try {
            int codeNumber = Integer.parseInt(code);
            KLog.d("手机验证码转为数字之后为：" + codeNumber);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            getMvpView().showToast("请输入正确的验证码");
            return;
        }

        if (EmptyUtils.isEmpty(password)) {
            KLog.d("密码输入为空");
            getMvpView().showToast("请输入密码");
            return;
        }

        getMvpView().showProgressDialog("注册中...", false);

        HashMap<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);

        password = MD5Util.md5(password);
        params.put("password", password);
        params.put("code", code);

        getCompositeDisposable().add(getDataManager().register(params)
                .doOnNext(new Consumer<WCUserInfoInfo>() {
                    @Override
                    public void accept(@NonNull WCUserInfoInfo wcUserInfoInfo) throws Exception {
                        getDataManager().saveUser(DataHelper.wcUserInfoInfo2User(wcUserInfoInfo));
                        getDataManager().saveLastTimeLoginId(wcUserInfoInfo.getUser_id());
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<WCUserInfoInfo>() {
                    @Override
                    public void accept(@NonNull WCUserInfoInfo wcUserInfoInfo) throws Exception {
                        getMvpView().hideProgressDialog();
                        getMvpView().registerSuccess(wcUserInfoInfo);
                    }
                }, this));
    }

    private void initMobSMS() {

        if (mMsgEventHandler != null) {
            return;
        }

        mMsgEventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        getMvpView().getCodeSuccess();
                        getMvpView().showToast("获取验证码成功");

                        onDestroy();
                    }
                    getMvpView().hideProgressDialog();
                } else {
                    ((Throwable)data).printStackTrace();
                    try {
                        String message = ((Throwable) data).getMessage();
                        JSONObject json = new JSONObject(message);
                        String msg = json.optString("detail");
                        getMvpView().getCodeFail(msg);
                    } catch (JSONException e) {
                        getMvpView().getCodeFail("获取短信验证码失败");
                        e.printStackTrace();
                    }

                    getMvpView().hideProgressDialog();
                }
            }
        };

        SMSSDK.registerEventHandler(mMsgEventHandler);
    }

    @Override
    public void getSmsCode(String mobile) {

        if (!RegexUtils.isMobileSimple(mobile)) {
            getMvpView().showToast("请输入正确的手机号码");
            return;
        }

        getMvpView().showProgressDialog(null, false);

        initMobSMS();

        SMSSDK.getVerificationCode("+86", mobile);
    }

    @Override
    public void detachView() {
        super.detachView();
        onDestroy();
    }

    private void onDestroy() {
        if (mMsgEventHandler != null) {
            SMSSDK.unregisterEventHandler(mMsgEventHandler);
            mMsgEventHandler = null;
        }
    }
}
