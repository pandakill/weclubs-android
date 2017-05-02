package com.mm.weclubs.app.mob_sms;

import android.content.Context;

import com.blankj.utilcode.utils.RegexUtils;
import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.util.ThreadUtil;
import com.mm.weclubs.util.WCLog;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/29 上午12:03
 * 描述:
 */

public class WCMobSmsPresenter extends BasePresenter<WCMobSmsView> {

    private EventHandler mMsgEventHandler = null;

    public WCMobSmsPresenter(Context context) {
        super(context);
        SMSSDK.initSDK(mContext, "1d66889b4e514", "be7b94528ec334b58c4e61f6780d104a");
    }

    @Override
    protected void initLog() {
        log = new WCLog(WCMobSmsPresenter.class);
    }

    public void initMobSMS() {

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

                    ThreadUtil.runInMainThread(mContext, () -> getMvpView().hideProgressDialog());

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

                    ThreadUtil.runInMainThread(mContext, () -> getMvpView().hideProgressDialog());
                }
            }
        };

        SMSSDK.registerEventHandler(mMsgEventHandler);
    }


    public void getSmsCode(String mobile) {

        if (!RegexUtils.isMobileSimple(mobile)) {
            getMvpView().showToast("请输入正确的手机号码");
            return;
        }

        getMvpView().showProgressDialog(null, false);

        initMobSMS();

        SMSSDK.getVerificationCode("+86", mobile);
    }

    public void onDestroy() {
        if (mMsgEventHandler != null) {
            SMSSDK.unregisterEventHandler(mMsgEventHandler);
            mMsgEventHandler = null;
        }
    }
}
