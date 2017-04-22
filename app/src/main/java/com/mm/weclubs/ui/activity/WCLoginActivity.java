package com.mm.weclubs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.mm.weclubs.R;
import com.mm.weclubs.app.login.WCLoginPresenter;
import com.mm.weclubs.app.login.WCLoginView;
import com.mm.weclubs.data.pojo.WCUserInfoInfo;
import com.mm.weclubs.ui.WCMainActivity;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/22 上午12:28
 * 描述:
 */

public class WCLoginActivity extends AppCompatActivity implements WCLoginView {

    private WCLoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mLoginPresenter = new WCLoginPresenter(getApplicationContext());
        mLoginPresenter.attachView(this);

        findViewById(R.id.email_sign_in_button).setOnClickListener(view ->
                mLoginPresenter.login(((AutoCompleteTextView) findViewById(R.id.email)).getText().toString(),
                ((EditText) findViewById(R.id.password)).getText().toString()));
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
