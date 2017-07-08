package com.mm.weclubs.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.mm.weclubs.R;
import com.mm.weclubs.app.base.BaseActivity;
import com.mm.weclubs.app.register.WCRegisterContract;
import com.mm.weclubs.data.network.pojo.WCUserInfoInfo;
import com.socks.library.KLog;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/27 上午10:16
 * 描述:
 */

public class WCRegisterActivity extends BaseActivity implements WCRegisterContract.View {

    private TextView mBtnGetCode;
    private EditText mInputMobile;
    private EditText mInputCode;
    private TextView mBtnNext;
    private RelativeLayout mRLInputPsw;
    private EditText mInputPsw;
    private View mLinePsw;

    private Observable<Object> verifyCodeObservable = null;

    @Inject
    WCRegisterContract.Presenter<WCRegisterContract.View> mPresenter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        getActivityComponent().inject(this);
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

        //mLoginPresenter = new WCLoginPresenter(this);
        mPresenter.attachView(this);

        mRLInputPsw.setVisibility(View.VISIBLE);
        mLinePsw.setVisibility(View.VISIBLE);

        mBtnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getSmsCode(mInputMobile.getText().toString());
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            KLog.d("点击了下一步按钮");
                                            mPresenter.register(mInputMobile.getText().toString(),
                                                    mInputCode.getText().toString(), mInputPsw.getText().toString());
                                        }
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

        mPresenter.detachView();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    public void registerSuccess(WCUserInfoInfo userInfo) {
//        PreferencesHelper.getInstance(this).put("user" + userInfo.getUser_id(), userInfo, EncodeType.BASE_64);
        showIntent(WCMainActivity.class);
    }

    @Override
    public void getCodeSuccess() {

        verifyCodeObservable = RxView.clicks(mBtnGetCode)
                .throttleFirst(60, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread());

        verifyCodeObservable.subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                Observable.timer(1000,TimeUnit.MICROSECONDS,AndroidSchedulers.mainThread()).take(60)
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(@NonNull Long aLong) throws Exception {
                                mBtnGetCode.setText(getString(R.string.action_repeat_get_code, 60 - aLong));
                                mBtnGetCode.setTextColor(getResources().getColor(R.color.colorCommonText_Tips));
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                mBtnGetCode.setText(getString(R.string.action_get_code));
                                mBtnGetCode.setTextColor(getResources().getColor(R.color.themeColor));
                            }
                        });
            }
        });
    }

    @Override
    public void getCodeFail(String errorMsg) {
        showToast(errorMsg);
    }
}
