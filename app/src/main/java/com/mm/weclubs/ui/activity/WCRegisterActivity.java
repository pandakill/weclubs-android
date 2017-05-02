package com.mm.weclubs.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.mm.weclubs.R;
import com.mm.weclubs.app.login.WCLoginPresenter;
import com.mm.weclubs.app.login.WCLoginView;
import com.mm.weclubs.app.mob_sms.WCMobSmsPresenter;
import com.mm.weclubs.app.mob_sms.WCMobSmsView;
import com.mm.weclubs.data.pojo.WCUserInfoInfo;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/27 上午10:16
 * 描述:
 */

public class WCRegisterActivity extends BaseActivity implements WCLoginView, WCMobSmsView {

    private TextView mBtnGetCode;
    private EditText mInputMobile;
    private EditText mInputCode;
    private TextView mBtnNext;
    private RelativeLayout mRLInputPsw;
    private EditText mInputPsw;
    private View mLinePsw;

    private Observable<Void> verifyCodeObservable = null;

    private WCLoginPresenter mLoginPresenter;
    private WCMobSmsPresenter mMobSmsPresenter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mBtnGetCode = (TextView) findViewById(R.id.btn_get_code);
        mInputMobile = (EditText) findViewById(R.id.input_mobile);
        mInputCode = (EditText) findViewById(R.id.input_code);
        mBtnNext = (TextView) findViewById(R.id.btn_login);
        mRLInputPsw = (RelativeLayout) findViewById(R.id.rl_input_password);
        mInputPsw = (EditText) findViewById(R.id.input_password);
        mLinePsw = findViewById(R.id.line_password);
    }

    @Override
    protected void afterView() {

        mLoginPresenter = new WCLoginPresenter(this);
        mLoginPresenter.attachView(this);

        mMobSmsPresenter = new WCMobSmsPresenter(this);
        mMobSmsPresenter.attachView(this);

        mRLInputPsw.setVisibility(View.VISIBLE);
        mLinePsw.setVisibility(View.VISIBLE);

        mBtnGetCode.setOnClickListener(view -> {
            mMobSmsPresenter.getSmsCode(mInputMobile.getText().toString());
        });

        mBtnNext.setOnClickListener(view -> {
            log.d("点击了下一步按钮");
            mLoginPresenter.register(mInputMobile.getText().toString(),
                    mInputCode.getText().toString(), mInputPsw.getText().toString());
        });
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return true;
    }

    @Override
    protected void unSubscribeObservable() {
        if (verifyCodeObservable != null) {
            verifyCodeObservable.unsubscribeOn(AndroidSchedulers.mainThread());
        }

        mMobSmsPresenter.onDestroy();
    }

    @Override
    public void registerSuccess(WCUserInfoInfo userInfo) {
//        PreferencesHelper.getInstance(this).put("user" + userInfo.getUser_id(), userInfo, EncodeType.BASE_64);
        showIntent(WCMainActivity.class);
    }

    @Override
    public void loginSuccess(WCUserInfoInfo userInfo) {
    }

    @Override
    public void loginFail(String errorMsg) {
    }

    @Override
    public void getCodeSuccess() {

        verifyCodeObservable = RxView.clicks(mBtnGetCode)
                .throttleFirst(60, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread());

        verifyCodeObservable.subscribe(aVoid -> {
                    Observable.timer(0, 1000, TimeUnit.MILLISECONDS,
                            AndroidSchedulers.mainThread()).take(60).subscribe(aLong -> {
                                mBtnGetCode.setText(getString(R.string.action_repeat_get_code, 60 - aLong));
                                mBtnGetCode.setTextColor(getResources().getColor(R.color.colorCommonText_Tips));
                            }, (error) -> {
                            }, () -> {
                                mBtnGetCode.setText(getString(R.string.action_get_code));
                                mBtnGetCode.setTextColor(getResources().getColor(R.color.themeColor));
                            }
                    );
                }
        );
    }

    @Override
    public void getCodeFail(String errorMsg) {
        showToast(errorMsg);
    }
}
