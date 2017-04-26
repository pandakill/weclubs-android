package com.mm.weclubs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.mm.weclubs.R;
import com.mm.weclubs.app.login.WCLoginPresenter;
import com.mm.weclubs.app.login.WCLoginView;
import com.mm.weclubs.data.pojo.WCUserInfoInfo;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/22 上午12:28
 * 描述:
 */

public class WCLoginActivity extends AppCompatActivity implements WCLoginView {

    private EditText mInputMobile;
    private EditText mInputPassword;

    private WCLoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        initView();

        mLoginPresenter = new WCLoginPresenter(getApplicationContext());
        mLoginPresenter.attachView(this);

        afterView();
    }

    private void initView() {
        mInputMobile = (EditText) findViewById(R.id.input_mobile);
        mInputPassword = (EditText) findViewById(R.id.input_password);
    }

    private void afterView() {
        findViewById(R.id.btn_login).setOnClickListener(view ->
                mLoginPresenter.login(mInputMobile.getText().toString(),
                        mInputPassword.getText().toString()));
    }

    @Override
    public void loginSuccess(WCUserInfoInfo userInfo) {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, WCMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFail(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }
}
