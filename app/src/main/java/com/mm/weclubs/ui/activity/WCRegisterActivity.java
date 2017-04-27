package com.mm.weclubs.ui.activity;

import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.mm.weclubs.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/27 上午10:16
 * 描述:
 */

public class WCRegisterActivity extends BaseActivity {

    private TextView mBtnGetCode;

    Observable<Void> verifyCodeObservable;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mBtnGetCode = (TextView) findViewById(R.id.btn_get_code);
    }

    @Override
    protected void afterView() {
        verifyCodeObservable = RxView.clicks(mBtnGetCode)
                                     .throttleFirst(60, TimeUnit.SECONDS)
                                     .subscribeOn(AndroidSchedulers.mainThread());

        verifyCodeObservable.subscribe(aVoid -> {
                    Observable.timer(0, 1000, TimeUnit.MILLISECONDS,
                            AndroidSchedulers.mainThread()).take(60).subscribe(aLong -> {
                                mBtnGetCode.setText(getString(R.string.action_repeat_get_code, 60 - aLong));
                            }, (error) -> {
                            }, () -> {
                                mBtnGetCode.setText(getString(R.string.action_get_code));
                            }
                    );
                }
        );
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
    }
}
