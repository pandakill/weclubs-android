package com.mm.weclubs.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.mm.weclubs.R;
import com.mm.weclubs.app.login.WCLoginPresenter;
import com.mm.weclubs.app.login.WCLoginView;
import com.mm.weclubs.data.pojo.WCUserInfoInfo;
import com.mm.weclubs.datacenter.WCUserDataCenter;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/22 上午12:28
 * 描述:
 */

public class WCLoginActivity extends BaseActivity implements WCLoginView {

    private EditText mInputMobile;
    private EditText mInputPassword;

    private WCLoginPresenter mLoginPresenter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mInputMobile = (EditText) findViewById(R.id.input_mobile);
        mInputPassword = (EditText) findViewById(R.id.input_password);

        mLoginPresenter = new WCLoginPresenter(getApplicationContext());
        mLoginPresenter.attachView(this);
    }

    @Override
    protected void afterView() {
//        findViewById(R.id.btn_login).setOnClickListener(view ->
//                mLoginPresenter.login(mInputMobile.getText().toString(),
//                        mInputPassword.getText().toString()));
        findViewById(R.id.btn_login).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginPresenter.login(mInputMobile.getText().toString(),
                        mInputPassword.getText().toString());
            }
        });

        mInputMobile.setText(WCUserDataCenter.getInstance(getApplicationContext()).getLastTimeLoginMobile());

        findViewById(R.id.btn_register).setOnClickListener(view -> showIntent(WCRegisterActivity.class));
        findViewById(R.id.btn_forget_password).setOnClickListener(view -> showToast("忘记密码"));

        mLoginPresenter.checkLogin();
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
    protected void unSubscribeObservable() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    public void registerSuccess(WCUserInfoInfo userInfo) {
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
