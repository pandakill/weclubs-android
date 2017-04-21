package com.mm.weclubs.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mm.weclubs.R;
import com.mm.weclubs.ui.activity.LoginActivity;


/**
 * 创建人: fangzanpan
 * 创建时间: 2017/2/15 下午2:26
 * 描述:
 */

public class PDMyDailyFragment extends Fragment {

    private AppCompatActivity mAppCompatActivity;

    Button mLoginBtn;
    ContentLoadingProgressBar mLoadingProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mAppCompatActivity = (AppCompatActivity) getActivity();
        View fragmentView = inflater.inflate(R.layout.fragment_my_daily, null);
        Toolbar toolbar = (Toolbar) fragmentView.findViewById(R.id.toolbar);
        toolbar.setTitle("我的日记");
        mAppCompatActivity.setSupportActionBar(toolbar);

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLoadingProgressBar = (ContentLoadingProgressBar) view.findViewById(R.id.progress_bar);
        mLoginBtn = (Button) view.findViewById(R.id.login_btn);
        mLoadingProgressBar.show();
        mLoginBtn.setOnClickListener(view1 -> {
            Toast.makeText(mAppCompatActivity, "登录", Toast.LENGTH_SHORT).show();
            mAppCompatActivity.startActivity(new Intent(mAppCompatActivity, LoginActivity.class));
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_mine, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
