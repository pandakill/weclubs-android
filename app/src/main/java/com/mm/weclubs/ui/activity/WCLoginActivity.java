package com.mm.weclubs.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.mm.weclubs.R;
import com.mm.weclubs.app.base.BaseActivity;
import com.mm.weclubs.app.login.WCLoginContract;
import com.mm.weclubs.data.network.pojo.WCUserInfoInfo;

import javax.inject.Inject;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/22 上午12:28
 * 描述:
 */

public class WCLoginActivity extends BaseActivity implements WCLoginContract.View {

    private EditText mInputMobile;
    private EditText mInputPassword;
    private boolean mNeedGetUserInfo = true;

    @Inject
    WCLoginContract.Presenter<WCLoginContract.View> mLoginPresenter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        getActivityComponent()
                .inject(this);

        mInputMobile = (EditText) findViewById(R.id.input_mobile);
        mInputPassword = (EditText) findViewById(R.id.input_password);

        mLoginPresenter.attachView(this);
    }

    @Override
    protected void afterView() {
        findViewById(R.id.btn_login).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginPresenter.login(mInputMobile.getText().toString(),
                        mInputPassword.getText().toString());
            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(WCRegisterActivity.class);
            }
        });
        findViewById(R.id.btn_forget_password).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("忘记密码");
            }
        });

        if (mNeedGetUserInfo) {
            mLoginPresenter.checkLogin();
        }

        mInputMobile.setText("13570578153");
        mInputPassword.setText("123456");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return false;
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {
        mNeedGetUserInfo = extras.getBoolean("getUserInfo", false);
    }

    @Override
    public void setDefaultMobile(String mobile) {
        mInputMobile.setText(mobile);
    }

    @Override
    public void loginSuccess(WCUserInfoInfo userInfo) {
        showToast("登录成功");

        showIntentThenKill(WCMainActivity.class);
    }

    @Override
    public void loginFail(String errorMsg) {
        showToast(errorMsg);
    }
}
